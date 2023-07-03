package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.callback.AdminCallback;
import com.github.CCweixiao.hbase.sdk.common.callback.MutatorCallback;
import com.github.CCweixiao.hbase.sdk.common.callback.TableCallback;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Table;
import org.apache.yetus.audience.InterfaceAudience;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author leo.jie (leojie1314@gmail.com)
 */
@InterfaceAudience.Private
public interface IHBaseBaseAdapter {
    Connection getConnection();
    Connection getHedgedReadClusterConnection();
    boolean hedgedReadIsOpen();
    long hedgedReadTimeout();
    int initHedgedReadPoolSize();

    default <T> T execute(AdminCallback<T, Admin> action) {
        try (Admin admin = this.getConnection().getAdmin()) {
            return action.doInAdmin(admin);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        }
    }

    default <T> T executeOnSource(String tableName, TableCallback<T, Table> action) throws IOException {
        try (Table table = this.getConnection().getTable(TableName.valueOf(tableName))) {
            return action.doInTable(table);
        } catch (Throwable throwable) {
            throw new IOException(throwable);
        }
    }

    default <T> T executeOnTarget(String tableName, TableCallback<T, Table> action) throws IOException {
        try (Table table = this.getHedgedReadClusterConnection().getTable(TableName.valueOf(tableName))) {
            return action.doInTable(table);
        } catch (Throwable throwable) {
            throw new IOException(throwable);
        }
    }

    default <T> Optional<T> execute(String tableName, TableCallback<T, Table> action) {
        if (hedgedReadIsOpen()) {
            ArrayList<Future<T>> futures = new ArrayList<>();
            CompletionService<T> hedgedService =
                    new ExecutorCompletionService<>(HBaseHedgedReadExecutor.getHBaseHedgedReadExecutor(initHedgedReadPoolSize()));

            Callable<T> executeInSource = () -> executeOnSource(tableName, action);
            Future<T> firstRequest = hedgedService.submit(executeInSource);
            futures.add(firstRequest);
            Future<T> future = null;
            try {
                future = hedgedService.poll(hedgedReadTimeout(), TimeUnit.MICROSECONDS);
                if (future != null) {
                    return Optional.ofNullable(future.get());
                }
            } catch (ExecutionException e) {
                futures.remove(future);
            } catch (InterruptedException e) {
                throw new HBaseSdkException("Interrupted while waiting for reading task.");
            }

            Callable<T> executeInTarget = () -> executeOnTarget(tableName, action);
            Future<T> oneMoreRequest = hedgedService.submit(executeInTarget);
            futures.add(oneMoreRequest);

            try {
                T result = getFirstToComplete(hedgedService, futures);
                cancelAll(futures);
                return Optional.ofNullable(result);
            } catch (InterruptedException e) {
                throw new HBaseSdkException(e);
            }

        } else {
            try {
                return Optional.ofNullable(executeOnSource(tableName, action));
            } catch (IOException e) {
                throw new HBaseSdkException(e);
            }
        }

    }

    default void execute(String tableName, MutatorCallback<BufferedMutator> action) {
        if (hedgedReadIsOpen()) {
            ArrayList<Future<Void>> futures = new ArrayList<>();
            CompletionService<Void> hedgedService =
                    new ExecutorCompletionService<>(HBaseHedgedReadExecutor.getHBaseHedgedReadExecutor(initHedgedReadPoolSize()));

            Callable<Void> executeInSource = () -> {
                executeOnSource(tableName, action);
                return null;
            };

            Future<Void> firstRequest = hedgedService.submit(executeInSource);
            futures.add(firstRequest);
            Future<Void> future = null;
            try {
                future = hedgedService.poll(hedgedReadTimeout(), TimeUnit.MICROSECONDS);
                if (future != null) {
                    future.get();
                    return;
                }
            } catch (ExecutionException e) {
                futures.remove(future);
            } catch (InterruptedException e) {
                throw new HBaseSdkException("Interrupted while waiting for reading task.");
            }

            Callable<Void> executeInTarget = () -> {
                executeOnTarget(tableName, action);
                return null;
            };

            Future<Void> oneMoreRequest = hedgedService.submit(executeInTarget);
            futures.add(oneMoreRequest);

            try {
                getFirstToComplete(hedgedService, futures);
                cancelAll(futures);
            } catch (InterruptedException e) {
                throw new HBaseSdkException(e);
            }

        } else {
            try {
                executeOnSource(tableName, action);
            } catch (IOException e) {
                throw new HBaseSdkException(e);
            }
        }
    }

    default void executeOnSource(String tableName, MutatorCallback<BufferedMutator> action) throws IOException {
        BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
        try (BufferedMutator mutator = this.getConnection()
                .getBufferedMutator(mutatorParams)) {
            action.doInMutator(mutator);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        }
    }

    default void executeOnTarget(String tableName, MutatorCallback<BufferedMutator> action) throws IOException {
        BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
        try (BufferedMutator mutator = this.getHedgedReadClusterConnection()
                .getBufferedMutator(mutatorParams)) {
            action.doInMutator(mutator);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        }
    }

    default <T> T getFirstToComplete(CompletionService<T> hedgedService,
                                     ArrayList<Future<T>> futures) throws InterruptedException {
        if (futures.isEmpty()) {
            throw new InterruptedException("let's retry.");
        }
        Future<T> future = null;
        try {
            future = hedgedService.take();
            futures.remove(future);
            return future.get();
        } catch (ExecutionException | CancellationException e) {
            futures.remove(future);
        }

        throw new InterruptedException("let's retry.");
    }

    default <T> void cancelAll(List<Future<T>> futures) {
        for (Future<T> future : futures) {
            future.cancel(false);
        }
    }

    default void executeSave(String tableName, Mutation mutation) {
        this.execute(tableName, mutator -> {
            mutator.mutate(mutation);
        });
    }

    default void executeSaveBatch(String tableName, List<Mutation> mutations) {
        this.execute(tableName, mutator -> {
            mutator.mutate(mutations);
        });
    }

    default void executeDelete(String tableName, Mutation mutation) {
        this.execute(tableName, mutator -> {
            mutator.mutate(mutation);
        });
    }

    default void executeDeleteBatch(String tableName, List<Mutation> mutations) {
        this.execute(tableName, mutator -> {
            mutator.mutate(mutations);
        });
    }
}

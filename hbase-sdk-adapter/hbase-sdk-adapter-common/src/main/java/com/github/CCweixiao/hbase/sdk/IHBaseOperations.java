package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.callback.AdminCallback;
import com.github.CCweixiao.hbase.sdk.common.callback.MutatorCallback;
import com.github.CCweixiao.hbase.sdk.common.callback.TableCallback;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.util.List;
import java.util.Optional;

/**
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface IHBaseOperations {
    Connection getConnection();

    default <T> T execute(AdminCallback<T, Admin> action) {
        try (Admin admin = this.getConnection().getAdmin()) {
            return action.doInAdmin(admin);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        }
    }

    default <T> Optional<T> execute(String tableName, TableCallback<T, Table> action) {
        try (Table table = this.getConnection().getTable(TableName.valueOf(tableName))) {
            return Optional.ofNullable(action.doInTable(table));
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        }
    }

    default void execute(String tableName, MutatorCallback<BufferedMutator> action) {
        BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
        try (BufferedMutator mutator = this.getConnection().getBufferedMutator(mutatorParams.writeBufferSize(4 * 1024 * 1024))) {
            action.doInMutator(mutator);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        }
    }

    default void save(String tableName, Mutation mutation) {
        this.execute(tableName, mutator -> {
            mutator.mutate(mutation);
        });
    }

    default void saveBatch(String tableName, List<Mutation> mutations) {
        this.execute(tableName, mutator -> {
            mutator.mutate(mutations);
        });
    }
}

package com.github.CCweixiao;

import com.github.CCweixiao.exception.HBaseOperationsException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <p>the interface is used to init some job，被{@link HBaseTemplate}实现。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface HBaseOperations {
    Logger LOGGER = LoggerFactory.getLogger(HBaseOperations.class);

    int REPLICATION_SCOPE_0 = 0;
    int REPLICATION_SCOPE_1 = 1;

    /**
     * 获取HBase的连接对象
     *
     * @return 获取HBase连接
     */
    Connection getConnection();

    /**
     * handle with admin operation.
     *
     * @param action admin action
     * @param <T>    return type class
     * @return return result
     */
    default <T> T execute(AdminCallback<T> action) {
        Admin admin = null;
        try {
            admin = this.getConnection().getAdmin();
            return action.doInAdmin(admin);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    LOGGER.error("the resource of admin released failed.");
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    /**
     * handle with table operation.
     *
     * @param tableName the name of table.
     * @param action    table action callback
     * @param <T>       return type class
     * @return return result
     */
    default <T> T execute(String tableName, TableCallback<T> action) {
        Table table = null;
        try {
            table = this.getConnection().getTable(TableName.valueOf(tableName));
            return action.doInTable(table);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        } finally {
            if (null != table) {
                try {
                    table.close();
                } catch (IOException e) {
                    LOGGER.error("the resource of table released failed.");
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    /**
     * handle with mutator operation.
     *
     * @param tableName the name of table.
     * @param action    mutator operation.
     */
    default void execute(String tableName, MutatorCallback action) {
        BufferedMutator mutator = null;
        try {
            BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
            mutator = this.getConnection().getBufferedMutator(mutatorParams.writeBufferSize(3 * 1024 * 1024));
            action.doInMutator(mutator);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        } finally {
            if (null != mutator) {
                try {
                    mutator.flush();
                    mutator.close();
                } catch (IOException e) {
                    LOGGER.error("the resource of mutator released failed.");
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }
}

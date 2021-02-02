package com.github.CCweixiao;

import com.github.CCweixiao.exception.HBaseOperationsException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * <p>此接口做一些默认初始化的工作，被{@link AbstractHBaseTemplate} 和 {@link AbstractHBaseAdminTemplate}实现。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface HBaseOperations {
    /**
     * 获取HBase的客户端连接对象
     *
     * @return 获取HBase连接对象
     */
    Connection getConnection();

    /**
     * 处理管理员类型的操作
     *
     * @param action 管理员类型的操作
     * @param <T>    泛型类型
     * @return 操作结果
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
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理HBase表数据读写相关的操作
     *
     * @param tableName 表名
     * @param action    表级别的读写操作回调
     * @param <T>       泛型类型
     * @return 结果
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
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理数据批量读写类型的操作
     *
     * @param tableName 表名
     * @param action    批量读写类型操作的回调
     */
    default void execute(String tableName, MutatorCallback action) {
        BufferedMutator mutator = null;
        try {
            BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
            mutator = this.getConnection().getBufferedMutator(mutatorParams.writeBufferSize(4 * 1024 * 1024));
            action.doInMutator(mutator);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        } finally {
            if (null != mutator) {
                try {
                    mutator.flush();
                    mutator.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

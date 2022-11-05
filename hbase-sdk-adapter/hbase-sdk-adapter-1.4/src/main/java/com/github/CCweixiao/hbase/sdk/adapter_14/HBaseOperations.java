package com.github.CCweixiao.hbase.sdk.adapter_14;

import com.github.CCweixiao.hbase.sdk.common.callback.AdminCallback;
import com.github.CCweixiao.hbase.sdk.common.callback.MutatorCallback;
import com.github.CCweixiao.hbase.sdk.common.callback.TableCallback;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;

import java.util.Optional;

/**
 * <p>初始化连接，并对admin 和 table的一些操作进行一层封装</p>
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
    default <T> T execute(AdminCallback<T, Admin> action) {
        try (Admin admin = this.getConnection().getAdmin()) {
            return action.doInAdmin(admin);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
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
    default <T> Optional<T> execute(String tableName, TableCallback<T, Table> action) {
        try (Table table = this.getConnection().getTable(TableName.valueOf(tableName))) {
            return Optional.ofNullable(action.doInTable(table));
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        }
    }

    /**
     * 处理数据批量读写类型的操作
     *
     * @param tableName 表名
     * @param action    批量读写类型操作的回调
     */
    default void execute(String tableName, MutatorCallback<BufferedMutator> action) {
        BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
        try (BufferedMutator mutator = this.getConnection().getBufferedMutator(mutatorParams.writeBufferSize(4 * 1024 * 1024))) {
            action.doInMutator(mutator);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        }
    }
}

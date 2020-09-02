package com.github.CCweixiao;

import org.apache.hadoop.hbase.client.Admin;

/**
 * <p>用于处理管理员级别的操作.</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface AdminCallback<T> {
    /**
     * admin operation call back
     *
     * @param admin HBase Admin Object
     * @return 返回结果
     * @throws Throwable 异常抛出
     */
    T doInAdmin(Admin admin) throws Throwable;
}

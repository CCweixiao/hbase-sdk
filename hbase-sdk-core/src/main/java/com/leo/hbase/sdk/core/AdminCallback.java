package com.leo.hbase.sdk.core;

import org.apache.hadoop.hbase.client.Admin;

/**
 * <p>用于处理管理员级别的操作.</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/5/13 5:23 下午
 * @since 1.0
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

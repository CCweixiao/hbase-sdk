package com.leo.hbase.sdk.core;

import org.apache.hadoop.hbase.client.Table;

/**
 * <p>用于表级别的操作。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/5/13 10:20 上午
 * @since 1.0
 */
public interface TableCallback<T> {
    /**
     * <p>表级别操作的回调</p>
     *
     * @param table HBase table object
     * @return 返回结果
     * @throws Throwable 异常抛出
     */
    T doInTable(Table table) throws Throwable;
}

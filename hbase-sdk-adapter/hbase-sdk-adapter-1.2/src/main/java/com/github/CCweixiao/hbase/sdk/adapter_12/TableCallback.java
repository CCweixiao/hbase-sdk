package com.github.CCweixiao.hbase.sdk.adapter_12;

import org.apache.hadoop.hbase.client.Table;

/**
 * <p>用于表级别的操作。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface TableCallback<T> {
    /**
     * <p>表级别操作的回调</p>
     *
     * @param table HTable的对象
     * @return 返回结果
     * @throws Throwable 异常抛出
     */
    T doInTable(Table table) throws Throwable;
}

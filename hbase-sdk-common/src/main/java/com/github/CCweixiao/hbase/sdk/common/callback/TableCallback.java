package com.github.CCweixiao.hbase.sdk.common.callback;

/**
 * <p>用于表级别的操作。</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public interface TableCallback<T, HT> {
    /**
     * <p>表级别操作的回调</p>
     *
     * @param table HTable的对象
     * @return 返回结果
     * @throws Throwable 异常抛出
     */
    T doInTable(HT table) throws Throwable;
}

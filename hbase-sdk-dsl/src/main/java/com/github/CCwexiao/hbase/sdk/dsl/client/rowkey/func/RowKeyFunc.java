package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;

/**
 * @author leojie 2020/11/28 11:09 上午
 */
public interface RowKeyFunc<T> {
    /**
     * convert text to row key
     *
     * @param text the value of row key
     * @return RowKey
     */
    RowKey<T> convert(String text);

    /**
     * reverse object value from RowKey
     *
     * @param rowKey RowKey obj
     * @return object value
     */
    T reverse(RowKey<T> rowKey);

    /**
     * convert function name
     *
     * @return function name
     */
    String funcName();

    /**
     * the desc of function
     *
     * @return func desc
     */
    String desc();

}

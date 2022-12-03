package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.BaseRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;

/**
 * @author leojie 2020/11/28 11:09 上午
 */
public interface RowKeyFunc<T> {
    /**
     * convert text to row key
     *
     * @param rowKey the value of row key
     * @return RowKey
     */
    T evalFuncReturnRowValue(BaseRowKey<T> rowKey);

    T evalFuncReturnRowValue(HBaseColumn row, String value);

    /**
     * convert function name
     *
     * @return function name
     */
    String showFuncName();

    /**
     * the desc of function
     *
     * @return func desc
     */
    String showDesc();

}

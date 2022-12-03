package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunc;

/**
 * @author leojie 2020/11/28 10:58 上午
 */
public interface RowKey<T> {
    /**
     * byte arr of row key
     * @return row value byte arr
     */
    byte[] toBytes();

    /**
     * extract value
     *
     * @return object value
     */
    T computeRowValue();

    String getOriValue();

    void setValueBytes(byte[] valueBytes);

    /**
     * column type
     *
     * @return type
     */
    ColumnType columnType();

    /**
     * set function by name
     *
     * @param rowKeyFunc function
     */
    void setRowKeyFunc(RowKeyFunc<T> rowKeyFunc);
}

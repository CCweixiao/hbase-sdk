package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;

/**
 * @author leojie 2020/11/28 10:58 上午
 */
public interface RowKey<T> {
    /**
     * byte arr of row key
     *
     * @return byte arr
     */
    byte[] toBytes();

    /**
     * extract value
     *
     * @return object value
     */
    T extractValue();

    /**
     * column type
     *
     * @return type
     */
    ColumnType columnType();

    default RowKey<byte[]> startRow () {
        return new BytesRowKey(new byte[0]);
    }

    default RowKey<byte[]> endRow () {
        return new BytesRowKey(new byte[0]);
    }
}

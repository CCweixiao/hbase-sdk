package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;

/**
 * @author leojie 2022/12/3 12:51
 */
public class StringRowKey implements RowKey<String> {
    protected String value;

    public StringRowKey(String value) {
        this.value = value;
    }

    @Override
    public byte[] toBytes() {
        return this.columnType().getTypeHandler().toBytes(String.class, value);
    }

    @Override
    public String extractValue() {
        return this.value;
    }

    @Override
    public ColumnType columnType() {
        return ColumnType.StringType;
    }
}

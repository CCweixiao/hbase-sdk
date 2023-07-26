package com.github.CCweixiao.hbase.sdk.common.model.row;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;

/**
 * @author leojie 2022/12/5 23:03
 */
public class HBaseDataColumn {
    private final String family;
    private final String qualifier;

    private final ColumnType columnType;
    private final Object value;
    private final long timestamp;

    public HBaseDataColumn(String family, String qualifier, ColumnType columnType, Object value, long timestamp) {
        this.family = family;
        this.qualifier = qualifier;
        this.columnType = columnType;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getFamily() {
        return family;
    }
    public String getQualifier() {
        return qualifier;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public Object getValue() {
        return value;
    }
    public long getTimestamp() {
        return timestamp;
    }
}

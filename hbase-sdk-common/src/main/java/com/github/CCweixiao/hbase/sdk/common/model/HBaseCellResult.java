package com.github.CCweixiao.hbase.sdk.common.model;

/**
 * @author leojie 2022/11/27 18:10
 */
public class HBaseCellResult {
    private Object rowKey;
    private String familyName;
    private String columnName;
    private Object value;
    private long timestamp;

    public Object getRowKey() {
        return rowKey;
    }

    public void setRowKey(Object rowKey) {
        this.rowKey = rowKey;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

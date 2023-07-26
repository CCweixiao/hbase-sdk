package com.github.CCweixiao.hbase.sdk.common.model.row;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2022/12/5 23:04
 */
public class HBaseDataRow {
    private final String rowKeyFieldName;
    private final Object rowKeyVal;
    private List<HBaseDataColumn> columns;

    private HBaseDataRow(String rowKeyFieldName, Object rowKeyVal) {
        this.rowKeyFieldName = rowKeyFieldName;
        this.rowKeyVal = rowKeyVal;
    }

    public String getRowKeyFieldName() {
        return rowKeyFieldName;
    }

    public Object getRowKeyVal() {
        return rowKeyVal;
    }

    public List<HBaseDataColumn> getColumns() {
        return columns;
    }

    public HBaseDataRow appendColumn(String family, String qualifier, ColumnType columnType, Object value, long timestamp) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(new HBaseDataColumn(family, qualifier, columnType, value, timestamp));
        return this;
    }

    public HBaseDataRow appendColumn(String family, String qualifier, ColumnType columnType, Object value) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(new HBaseDataColumn(family, qualifier, columnType, value, 0L));
        return this;
    }

    public HBaseDataRow appendColumn(String family, String qualifier, Object value) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(new HBaseDataColumn(family, qualifier, ColumnType.StringType, value, 0L));
        return this;
    }

    public static HBaseDataRow of(String rowKeyFieldName, Object rowKeyVal) {
        return new HBaseDataRow(rowKeyFieldName, rowKeyVal);
    }

    public static HBaseDataRow of(Object rowKey) {
        return new HBaseDataRow("row_key", rowKey);
    }
}

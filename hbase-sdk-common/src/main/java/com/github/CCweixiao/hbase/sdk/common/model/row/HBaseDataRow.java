package com.github.CCweixiao.hbase.sdk.common.model.row;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2022/12/5 23:04
 */
public class HBaseDataRow {
    private final String rowKeyName;
    private final Object rowKey;
    private List<HBaseDataColumn> columns;

    private HBaseDataRow(String rowKeyName, Object rowKey) {
        this.rowKeyName = rowKeyName;
        this.rowKey = rowKey;
    }

    public String getRowKeyName() {
        return rowKeyName;
    }

    public Object getRowKey() {
        return rowKey;
    }

    public List<HBaseDataColumn> getColumns() {
        return columns;
    }

    public HBaseDataRow appendColumn(String family, String qualifier, Object value, Long timestamp) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(new HBaseDataColumn(family, qualifier, value, timestamp));
        return this;
    }

    public HBaseDataRow appendColumn(String family, String qualifier, Object value) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(new HBaseDataColumn(family, qualifier, value, null));
        return this;
    }

    public static HBaseDataRow of(String rowKeyName, Object rowKey) {
        return new HBaseDataRow(rowKeyName, rowKey);
    }

    public static HBaseDataRow of(Object rowKey) {
        return new HBaseDataRow("row_key", rowKey);
    }
}

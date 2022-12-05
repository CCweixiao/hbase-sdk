package com.github.CCwexiao.hbase.sdk.dsl.model.row;

import java.util.Collection;

/**
 * @author leojie 2022/12/5 23:04
 */
public class Row {
    private Object rowKey;
    private Collection<Column> columns;

    public Row() {
    }

    public Object getRowKey() {
        return rowKey;
    }

    public void setRowKey(Object rowKey) {
        this.rowKey = rowKey;
    }

    public Collection<Column> getColumns() {
        return columns;
    }

    public void setColumns(Collection<Column> columns) {
        this.columns = columns;
    }
}

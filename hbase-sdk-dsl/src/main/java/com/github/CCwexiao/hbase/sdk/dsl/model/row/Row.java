package com.github.CCwexiao.hbase.sdk.dsl.model.row;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author leojie 2022/12/5 23:04
 */
public class Row {
    private final Object rowKey;
    private Collection<Column> columns;

    private Row(Object rowKey) {
        this.rowKey = rowKey;
    }

    public Object getRowKey() {
        return rowKey;
    }

    public Collection<Column> getColumns() {
        return columns;
    }

    public Row appendColumn(String family, String qualifier, Object value, Long timestamp) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(new Column(family, qualifier, value, timestamp));
        return this;
    }

    public Row appendColumn(String family, String qualifier, Object value) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(new Column(family, qualifier, value, null));
        return this;
    }

    public static Row of(Object rowKey) {
        return new Row(rowKey);
    }
}

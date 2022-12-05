package com.github.CCwexiao.hbase.sdk.dsl.model.row;

import java.util.Collection;

/**
 * @author leojie 2022/12/5 23:07
 */
public class DataSet {
    private String tableName;
    private Collection<Row> rowSet;

    public DataSet() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Collection<Row> getRowSet() {
        return rowSet;
    }

    public void setRowSet(Collection<Row> rowSet) {
        this.rowSet = rowSet;
    }
}

package com.github.CCwexiao.hbase.sdk.dsl.model.row;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author leojie 2022/12/5 23:07
 */
public class DataSet {
    private final String tableName;
    private Collection<Row> rowSet;

    public DataSet(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public Collection<Row> getRowSet() {
        return rowSet;
    }

    public static DataSet of(String tableName) {
        return new DataSet(tableName);
    }

    public DataSet appendRow(Row row) {
        if (this.rowSet == null) {
            this.rowSet = new ArrayList<>();
        }
        this.rowSet.add(row);
        return this;
    }

    public void show() {

    }
}

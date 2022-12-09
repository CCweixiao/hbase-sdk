package com.github.CCweixiao.hbase.sdk.common.model.row;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leojie 2022/12/5 23:07
 */
public class HBaseDataSet {
    private final String tableName;
    private List<HBaseDataRow> rowSet;
    public HBaseDataSet(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
    public List<HBaseDataRow> getRowSet() {
        return rowSet;
    }

    public static HBaseDataSet of(String tableName) {
        return new HBaseDataSet(tableName);
    }
    public HBaseDataSet appendRow(HBaseDataRow row) {
        if (this.rowSet == null) {
            this.rowSet = new ArrayList<>();
        }
        this.rowSet.add(row);
        return this;
    }

    public HBaseDataSet appendRows(List<HBaseDataRow> rowList) {
        if (this.rowSet == null) {
            this.rowSet = new ArrayList<>();
        }
        this.rowSet.addAll(rowList);
        return this;
    }

    public void show() {
        if (this.getRowSet() == null || this.getRowSet().isEmpty()) {
            return;
        }
        List<String> colNams = new ArrayList<>();
        HBaseDataRow row = this.getRowSet().get(0);
        colNams.add(row.getRowKeyName());
        for (HBaseDataColumn column : row.getColumns()) {
            colNams.add(column.getFamily() + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR + column.getQualifier());
        }
        List<List<String>> valueList = new ArrayList<>();
        for (HBaseDataRow r : this.getRowSet()) {
            List<String> tmpValueList = new ArrayList<>(r.getColumns().size() + 1);
            tmpValueList.add(r.getRowKey().toString());
            tmpValueList.addAll(r.getColumns().stream().map(c -> {
                if (c.getValue() == null) {
                    return "NULL";
                } else {
                    return c.getValue().toString();
                }
            }).collect(Collectors.toList()));
            valueList.add(tmpValueList);
        }
        DataSetFormatter dataSetFormatter = new DataSetFormatter(colNams, valueList);
        System.out.println(dataSetFormatter.printTable());
    }
}

package com.github.CCwexiao.hbase.sdk.dsl.model;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseColumnNotFoundException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

import java.util.*;

/**
 * @author leojie 2020/11/27 10:53 下午
 */
public class HBaseTableSchema {
    private final String tableName;

    private final String defaultFamily;
    /**
     * qualifier -> family -> HBaseColumnSchema
     */
    private final Map<String, HBaseColumn> columnSchemaMap;

    private final TableQuerySetting tableQuerySetting;

    public HBaseTableSchema(Builder builder) {
        this.tableName = builder.tableName;
        this.defaultFamily = builder.defaultFamily;
        this.columnSchemaMap = builder.columnSchemaMap;
        this.tableQuerySetting = builder.runtimeSetting;
    }

    public static class Builder {
        private final String tableName;

        public Builder(String tableName) {
            this.tableName = tableName;
        }

        private String defaultFamily;
        /**
         * family:qualifier -> HBaseColumnSchema
         */
        private Map<String, HBaseColumn> columnSchemaMap;

        private TableQuerySetting runtimeSetting = new TableQuerySetting();

        public Builder defaultFamily(String defaultFamily) {
            this.defaultFamily = defaultFamily;
            return this;
        }

        public Builder addColumnSchemas(Map<String, HBaseColumn> columnSchemaMap) {
            this.columnSchemaMap = columnSchemaMap;
            return this;
        }

        public Builder addColumn(HBaseColumn column) {
            if (this.columnSchemaMap == null) {
                this.columnSchemaMap = new LinkedHashMap<>(4);
            }
            if (column.columnIsRow()) {
                this.columnSchemaMap.put(column.getColumnName(), column);
                return this;
            }
            String familyName = column.getFamilyName();
            this.columnSchemaMap.put(familyName + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR
                    + column.getColumnName(), column);
            return this;
        }

        public Builder addColumn(String familyName, String columnName, ColumnType columnType,
                                 boolean isRow, boolean nullable) {
            addColumn(new HBaseColumn.Builder(columnName)
                    .defaultFamilyName(defaultFamily)
                    .familyName(familyName)
                    .columnIsRow(isRow)
                    .columnType(columnType)
                    .nullable(nullable)
                    .build());
            return this;
        }

        public Builder addColumn(String columnName, ColumnType columnType, boolean isRow, boolean nullable) {
            return addColumn("", columnName, columnType, isRow, nullable);
        }

        public Builder addColumn(String columnName, ColumnType columnType, boolean isRow) {
            return addColumn("", columnName, columnType, isRow, true);
        }

        public Builder addColumn(String columnName, ColumnType columnType) {
            return addColumn("", columnName, columnType, false, true);
        }

        public Builder addColumn(String familyName, String columnName, ColumnType columnType, boolean isRow) {
            return addColumn(familyName, columnName, columnType, isRow, true);
        }

        public Builder addColumn(String familyName, String columnName, ColumnType columnType) {
            return addColumn(familyName, columnName, columnType, false, true);
        }

        public Builder addColumn(String familyName, String columnName) {
            return addColumn(familyName, columnName, ColumnType.StringType, false, true);
        }

        public Builder addColumn(String columnName) {
            return addColumn("", columnName, ColumnType.StringType, false, true);
        }

        public Builder addRow(String columnName, ColumnType columnType) {
            return addColumn("", columnName, columnType, true, false);
        }

        public Builder addRow(String columnName) {
            return addColumn("", columnName, ColumnType.StringType, true, false);
        }

        public Builder scanCaching(int scanCaching) {
            this.runtimeSetting.setScanCaching(scanCaching);
            return this;
        }

        public Builder scanBatch(int scanBatch) {
            this.runtimeSetting.setScanBatch(scanBatch);
            return this;
        }

        public Builder deleteBatch(int deleteBatch) {
            this.runtimeSetting.setDeleteBatch(deleteBatch);
            return this;
        }

        public Builder scanCacheBlocks(boolean scanCacheBlocks) {
            this.runtimeSetting.setScanCacheBlocks(scanCacheBlocks);
            return this;
        }

        public Builder runtimeSetting(TableQuerySetting runtimeSetting) {
            this.runtimeSetting = runtimeSetting;
            return this;
        }

        public HBaseTableSchema build() {
            return new HBaseTableSchema(this);
        }
    }

    public HBaseColumn findColumn(String familyName, String columnName) {
        MyAssert.checkArgument(StringUtil.isNotBlank(columnName), "The column name must not be empty.");
        if (this.columnSchemaMap == null || this.columnSchemaMap.isEmpty()) {
            return null;
        }
        if (StringUtil.isBlank(familyName) && this.columnSchemaMap.containsKey(columnName)) {
            return this.columnSchemaMap.get(columnName);
        }
        if (StringUtil.isBlank(familyName)) {
            familyName = this.defaultFamily;
        }
        HBaseColumn column = this.columnSchemaMap.get(familyName + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR + columnName);
        if (column == null) {
            throw new HBaseColumnNotFoundException(String.format("The col of %s:%s is undefined.", familyName, columnName));
        }
        return column;
    }

    public HBaseColumn findColumn(String columnName) {
        return findColumn(this.defaultFamily, columnName);
    }

    public HBaseColumn findRow() {
        if (this.columnSchemaMap == null || this.columnSchemaMap.isEmpty()) {
            throw new HBaseColumnNotFoundException("Please set column schema for table: " + this.getTableName());
        }
        for (HBaseColumn column : this.columnSchemaMap.values()) {
            if (column.columnIsRow()) {
                return column;
            }
        }
        throw new HBaseColumnNotFoundException(String.format("Row key is undefined in the column schema of table: %s.", this.getTableName()));
    }

    /**
     * 获取所有的 HBaseColumnSchema
     *
     * @return All HBaseColumnSchema List
     */
    public Set<HBaseColumn> findAllColumns() {
        return new HashSet<>(columnSchemaMap.values());
    }

    public String getTableName() {
        return tableName;
    }

    public String getDefaultFamily() {
        return defaultFamily;
    }

    public Map<String, HBaseColumn> getColumnSchemaMap() {
        return columnSchemaMap;
    }

    public TableQuerySetting getTableQuerySetting() {
        return tableQuerySetting;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------table--------------------------\n");
        sb.append("tableName = ");
        sb.append(this.getTableName());
        sb.append(", ");
        sb.append("defaultFamily = ");
        sb.append(this.getDefaultFamily());
        sb.append("\n");
        HBaseColumn row = null;
        for (HBaseColumn column : findAllColumns()) {
            if (column.columnIsRow()) {
                row = column;
            }
        }
        if (row != null) {
            sb.append(row.generateSchema(this.getDefaultFamily()));
            sb.append("\n");
        }
        for (HBaseColumn column : findAllColumns()) {
            if (column.columnIsRow()) {
                continue;
            }
            sb.append(column.generateSchema(this.getDefaultFamily()));
            sb.append("\n");
        }
        sb.append("---------------table--------------------------\n");
        return sb.toString();
    }

    public void printSchema() {
        System.out.println(this.toString());
    }
}

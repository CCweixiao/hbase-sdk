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

    private TableQueryProperties tableQueryProperties;

    public HBaseTableSchema(Builder builder) {
        this.tableName = builder.tableName;
        this.defaultFamily = builder.defaultFamily;
        this.columnSchemaMap = builder.columnSchemaMap;
        this.tableQueryProperties = builder.tableQueryProperties;
    }

    public static class Builder {
        private final String tableName;

        private String defaultFamily;
        /**
         * family:qualifier -> HBaseColumnSchema
         */
        private Map<String, HBaseColumn> columnSchemaMap;

        private TableQueryProperties tableQueryProperties;

        private Builder(String tableName) {
            this.tableName = tableName;
            this.tableQueryProperties = new TableQueryProperties();
        }

        public Builder defaultFamily(String defaultFamily) {
            this.defaultFamily = defaultFamily;
            return this;
        }

        public Builder addColumn(HBaseColumn column) {
            if (this.columnSchemaMap == null) {
                this.columnSchemaMap = new LinkedHashMap<>(4);
            }
            String columnName = column.getColumnName();
            if (this.columnSchemaMap.containsKey(columnName)) {
                throw new IllegalArgumentException("Added duplicate field " + columnName);
            }
            if (column.columnIsRow()) {
                Map<String, HBaseColumn> newColumnMap = new LinkedHashMap<>();
                newColumnMap.put(column.getColumnName(), column);
                newColumnMap.putAll(this.columnSchemaMap);
                this.columnSchemaMap = newColumnMap;
                return this;
            }
            String familyName = column.getFamily();
            this.columnSchemaMap.put(familyName + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR
                    + column.getColumnName(), column);
            return this;
        }

        public Builder addColumn(String family, String columnName, ColumnType columnType,
                                 boolean isRow, boolean nullable) {
            if (StringUtil.isBlank(columnName)) {
                throw new HBaseColumnNotFoundException("The name of column must not be empty.");
            }
            if (StringUtil.isBlank(family)) {
                if (!isRow) {
                    MyAssert.checkArgument(StringUtil.isBlank(this.getDefaultFamily()),
                            String.format("The family and default family of column:[%s] are both empty.", columnName));
                    family = this.getDefaultFamily();
                }
            }
            addColumn(HBaseColumn.of(family, columnName)
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
            this.tableQueryProperties.setScanCaching(scanCaching);
            return this;
        }

        public Builder scanBatch(int scanBatch) {
            this.tableQueryProperties.setScanBatch(scanBatch);
            return this;
        }

        public Builder deleteBatch(int deleteBatch) {
            this.tableQueryProperties.setDeleteBatch(deleteBatch);
            return this;
        }

        public Builder scanCacheBlocks(boolean scanCacheBlocks) {
            this.tableQueryProperties.setScanCacheBlocks(scanCacheBlocks);
            return this;
        }

        public Builder tableQueryProperties(TableQueryProperties tableQueryProperties) {
            this.tableQueryProperties = tableQueryProperties;
            return this;
        }

        public HBaseTableSchema build() {
            return new HBaseTableSchema(this);
        }

        public String getDefaultFamily() {
            return defaultFamily;
        }
    }

    public HBaseColumn findColumn(String family, String columnName) {
        MyAssert.checkArgument(StringUtil.isNotBlank(columnName), "The column name must not be empty.");
        if (this.columnSchemaMap == null || this.columnSchemaMap.isEmpty()) {
            return null;
        }
        HBaseColumn column;
        if (StringUtil.isBlank(family)) {
            column = this.columnSchemaMap.get(columnName);
            if (column != null) {
                return column;
            }
            family = this.getDefaultFamily();
        }
        column = this.columnSchemaMap.get(family + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR + columnName);
        if (column == null) {
            throw new HBaseColumnNotFoundException(String.format("The column of %s:%s is undefined.", family, columnName));
        }
        return column;
    }

    public HBaseColumn findColumn(String columnName) {
        return findColumn(this.getDefaultFamily(), columnName);
    }

    public HBaseColumn findRow() {
        if (this.columnSchemaMap == null || this.columnSchemaMap.isEmpty()) {
            throw new HBaseColumnNotFoundException("Please add column for the table schema: " + this.getTableName());
        }
        int num = 0;
        HBaseColumn row = null;
        for (HBaseColumn column : this.columnSchemaMap.values()) {
            if (column.columnIsRow()) {
                row = column;
                num += 1;
            }
        }
        if (num == 0) {
            throw new HBaseColumnNotFoundException(String.format("Row key is undefined in the column schema of table: %s", this.getTableName()));
        }
        if (num > 1) {
            throw new HBaseColumnNotFoundException(String.format("Row key is defined multiple times in the column schema of table: %s", this.getTableName()));
        }
        return row;
    }

    /**
     * 获取所有的 HBaseColumnSchema
     *
     * @return All HBaseColumnSchema List
     */
    public List<HBaseColumn> findAllColumns() {
        if (this.columnSchemaMap == null || this.columnSchemaMap.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<HBaseColumn> columns = new ArrayList<>(this.columnSchemaMap.size());
        this.columnSchemaMap.forEach((columnName, column) -> columns.add(column));
        return columns;
    }

    public Map<KeyValue, HBaseColumn> createColumnsMap() {
        if (this.findAllColumns().isEmpty()) {
            return new HashMap<>(0);
        }
        Map<KeyValue, HBaseColumn> columnMap = new HashMap<>(this.findAllColumns().size());
        this.findAllColumns().forEach(column ->
                columnMap.put(new KeyValue(column.getFamilyNameBytes(), column.getColumnNameBytes()), column));
        return columnMap;
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

    public void setTableQuerySetting(TableQueryProperties tableQueryProperties) {
        this.tableQueryProperties = tableQueryProperties;
    }

    public TableQueryProperties getTableQuerySetting() {
        return tableQueryProperties;
    }

    public static Builder of(String tableName) {
        if (StringUtil.isBlank(tableName)) {
            throw new IllegalArgumentException("The table name is not allowed to be empty.");
        }
        String fullTableName = HMHBaseConstants.getFullTableName(tableName);
        return new Builder(fullTableName);
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
            sb.append(row.generateSchema());
            sb.append("\n");
        }
        for (HBaseColumn column : findAllColumns()) {
            if (column.columnIsRow()) {
                continue;
            }
            sb.append(column.generateSchema());
            sb.append("\n");
        }
        sb.append("---------------table--------------------------\n");
        return sb.toString();
    }

    public void printSchema() {
        System.out.println(this);
    }
}

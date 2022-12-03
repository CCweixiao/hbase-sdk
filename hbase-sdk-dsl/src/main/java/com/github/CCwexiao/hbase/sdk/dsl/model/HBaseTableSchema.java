package com.github.CCwexiao.hbase.sdk.dsl.model;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseColumnNotFoundException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseFamilyNotFoundException;
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

    public HBaseTableSchema(Builder builder) {
        this.tableName = builder.tableName;
        this.defaultFamily = builder.defaultFamily;
        this.columnSchemaMap = builder.columnSchemaMap;
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
                this.columnSchemaMap = new HashMap<>(4);
            }
            if (column.columnIsRow()) {
                this.columnSchemaMap.put(column.getColumnName(), column);
                return this;
            }
            String familyName = column.getFamilyName();
            if (StringUtil.isBlank(familyName) && StringUtil.isBlank(this.defaultFamily)) {
                throw new HBaseFamilyNotFoundException("Please set family name in col or set default family in table schema.");
            }
            if (StringUtil.isBlank(familyName) && StringUtil.isNotBlank(this.defaultFamily)) {
                familyName = this.defaultFamily;
            }
            this.columnSchemaMap.put(familyName + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR
                    + column.getColumnName(), column);
            return this;
        }

        public Builder addColumn(String familyName, String columnName,
                                 ColumnType columnType, boolean isRow,
                                 boolean nullable) {
            if (isRow) {
                familyName = "";
            } else {
                if (StringUtil.isBlank(familyName)) {
                    familyName = defaultFamily;
                }
            }
            addColumn(new HBaseColumn.Builder(familyName, columnName)
                    .columnIsRow(isRow)
                    .columnType(columnType)
                    .nullable(nullable)
                    .build());
            return this;
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

        public HBaseTableSchema build() {
            return new HBaseTableSchema(this);
        }
    }

    public HBaseColumn findColumn(String familyName, String columnName) {
        MyAssert.checkArgument(StringUtil.isNotBlank(columnName), "The column name must not be empty.");
        if (this.columnSchemaMap == null || this.columnSchemaMap.isEmpty()) {
            return null;
        }
        if (StringUtil.isBlank(familyName) && columnSchemaMap.containsKey(columnName)) {
            return columnSchemaMap.get(columnName);
        }
        if (StringUtil.isBlank(familyName)) {
            familyName = defaultFamily;
        }
        HBaseColumn column = columnSchemaMap.get(familyName + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR + columnName);
        if (column == null) {
            throw new HBaseColumnNotFoundException(String.format("The col of %s:%s is undefined.", familyName, columnName));
        }
        return column;
    }

    public HBaseColumn findColumn(String columnName) {
        return findColumn(defaultFamily, columnName);
    }

    public HBaseColumn findRow(String columnName) {
        MyAssert.checkArgument(StringUtil.isNotBlank(columnName), "The column name must not be empty.");
        if (this.columnSchemaMap == null || this.columnSchemaMap.isEmpty()) {
            return null;
        }
        HBaseColumn row = columnSchemaMap.get(columnName);
        if (row == null) {
            throw new HBaseColumnNotFoundException(String.format("The row key column id undefined for table [%s].", tableName));
        }
        return row;
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
}

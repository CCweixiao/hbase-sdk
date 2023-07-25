package com.github.CCwexiao.hbase.sdk.dsl.model;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

import java.util.Objects;

/**
 * @author leojie 2020/11/27 10:45 下午
 */
public class HBaseColumn {
    /**
     * family name
     */
    private final String family;
    /**
     * qualifier name
     */
    private final String columnName;
    /**
     * qualifier value type
     */
    private final ColumnType columnType;
    /**
     * is null or not
     */
    private final boolean nullable;
    /**
     * set one column is row
     */
    private final boolean columnIsRow;

    public HBaseColumn(Builder builder) {
        this.family = builder.family;
        this.columnName = builder.columnName;
        this.columnType = builder.columnType;
        this.nullable = builder.nullable;
        this.columnIsRow = builder.columnIsRow;
    }

    public static class Builder {
        private final String family;
        private final String columnName;
        private ColumnType columnType = ColumnType.StringType;
        private boolean nullable = true;
        private boolean columnIsRow = false;

        public Builder(String family, String columnName) {
            MyAssert.checkArgument(StringUtil.isNotBlank(columnName), "The mame of column must not be empty.");
            this.family = family;
            this.columnName = columnName;
        }

        public Builder columnType(ColumnType columnType) {
            this.columnType = columnType;
            return this;
        }

        public Builder columnIsRow(boolean columnIsRow) {
            this.columnIsRow = columnIsRow;
            return this;
        }

        public Builder nullable(boolean nullable) {
            this.nullable = nullable;
            return this;
        }

        public HBaseColumn build() {
            return new HBaseColumn(this);
        }
    }

    public static HBaseColumn.Builder of(String family, String columnName) {
        return new HBaseColumn.Builder(family, columnName);
    }

    public String getFamily() {
        return family;
    }

    public byte[] getFamilyNameBytes() {
        return BytesUtil.toBytes(this.getFamily());
    }

    public String getColumnName() {
        return columnName;
    }

    public byte[] getColumnNameBytes() {
        return BytesUtil.toBytes(this.getColumnName());
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean columnIsRow() {
        return columnIsRow;
    }

    public byte[] convertValToBytes(Object value) {
        return this.getColumnType().getTypeHandler().toBytes(this.getColumnType().getTypeClass(), value);
    }

    public Object convertBytesToVal(byte[] bytes) {
        return this.getColumnType().getTypeHandler().toObject(this.getColumnType().getTypeClass(), bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HBaseColumn)) {
            return false;
        }
        HBaseColumn column = (HBaseColumn) o;
        return this.getFamily().equals(column.getFamily())
                && columnName.equals(column.columnName) && columnIsRow == column.columnIsRow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(family, columnName, columnIsRow);
    }

    public String generateSchema() {
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        sb.append("—— ");
        if (!this.columnIsRow()) {
            sb.append(this.getFamily());
            sb.append(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR);
        }
        sb.append(this.getColumnName());
        sb.append(": ");
        sb.append("(");
        sb.append("isRow");
        sb.append(" = ");
        sb.append(this.columnIsRow());
        sb.append(", ");
        sb.append("nullable");
        sb.append(" = ");
        sb.append(this.isNullable());
        sb.append(")");
        return sb.toString();
    }
}

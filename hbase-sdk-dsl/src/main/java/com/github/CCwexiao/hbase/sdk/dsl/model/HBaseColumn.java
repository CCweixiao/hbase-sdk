package com.github.CCwexiao.hbase.sdk.dsl.model;


import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlFamilyMissingException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

import java.util.Objects;

/**
 * @author leojie 2020/11/27 10:45 下午
 */
public class HBaseColumn {
    private final String defaultFamilyName;
    /**
     * family name
     */
    private final String familyName;
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
        this.familyName = builder.familyName;
        this.columnName = builder.columnName;
        this.columnType = builder.columnType;
        this.nullable = builder.nullable;
        this.columnIsRow = builder.columnIsRow;
        this.defaultFamilyName = builder.defaultFamilyName;
    }

    public static class Builder {
        private final String columnName;
        private String defaultFamilyName;
        private String familyName;
        private ColumnType columnType = ColumnType.StringType;
        private boolean nullable = true;
        private boolean columnIsRow = false;

        public Builder(String columnName) {
            MyAssert.checkArgument(StringUtil.isNotBlank(columnName), "The mame of col must not be empty.");
            this.columnName = columnName;
        }

        public Builder defaultFamilyName(String defaultFamilyName) {
            this.defaultFamilyName = defaultFamilyName;
            return this;
        }

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
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

    public String getFamilyName() {
        String f = familyName;
        if (StringUtil.isBlank(f)) {
            f = defaultFamilyName;
        }
        if (StringUtil.isBlank(f) && !this.columnIsRow()) {
            throw new HBaseSqlFamilyMissingException(String.format("The family of col [%s] must not be empty.", columnName));
        }
        return familyName;
    }

    public byte[] getFamilyNameBytes() {
        return BytesUtil.toBytes(this.getFamilyName());
    }

    public byte[] getColumnNameBytes() {
        return BytesUtil.toBytes(this.getColumnName());
    }

    public String getColumnName() {
        return columnName;
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

    public byte[] convertBytesByValue(Object value) {
        return this.getColumnType().getTypeHandler().toBytes(this.getColumnType().getTypeClass(), value);
    }

    public Object toObject(byte[] row) {
        return this.getColumnType().getTypeHandler().toObject(this.getColumnType().getTypeClass(), row);
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
        return this.getFamilyName().equals(column.getFamilyName())
                && columnName.equals(column.columnName) && columnIsRow == column.columnIsRow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(familyName, columnName, columnIsRow);
    }

    public String generateSchema(String defaultFamily) {
        String familyName = defaultFamily;
        if (StringUtil.isNotBlank(this.getFamilyName())) {
            familyName = this.getFamilyName();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        sb.append("—— ");
        if (!this.columnIsRow()) {
            sb.append(familyName);
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

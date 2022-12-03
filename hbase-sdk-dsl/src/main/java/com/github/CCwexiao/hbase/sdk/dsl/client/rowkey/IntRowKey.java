package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlColValueAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;

import java.util.Objects;

/**
 * @author leojie 2022/12/3 12:43
 */
public class IntRowKey implements RowKey<Integer> {
    private final int value;

    public IntRowKey(String value) {
        try {
            this.value = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new HBaseSqlColValueAnalysisException("The value of " + value + " can not be converted int number.");
        }
    }

    @Override
    public byte[] toBytes() {
        return this.columnType().getTypeHandler().toBytes(Integer.class, value);
    }

    @Override
    public Integer extractValue() {
        return this.value;
    }

    @Override
    public ColumnType columnType() {
        return ColumnType.IntegerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntRowKey)) {
            return false;
        }
        IntRowKey intRowKey = (IntRowKey) o;
        return value == intRowKey.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

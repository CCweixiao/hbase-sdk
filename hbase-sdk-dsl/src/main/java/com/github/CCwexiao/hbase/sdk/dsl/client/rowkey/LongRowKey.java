package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlColValueAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;

/**
 * @author leojie 2022/12/3 12:49
 */
public class LongRowKey implements RowKey<Long> {

    private final long value;

    public LongRowKey(String value) {
        try {
            this.value = Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new HBaseSqlColValueAnalysisException("The value of " + value + " can not be converted long number.");
        }
    }

    @Override
    public byte[] toBytes() {
        return this.columnType().getTypeHandler().toBytes(Long.class, value);
    }

    @Override
    public Long extractValue() {
        return this.value;
    }

    @Override
    public ColumnType columnType() {
        return ColumnType.LongType;
    }
}

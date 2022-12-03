package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.ConvertIntRowKeyFunc;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunction;

import java.util.Objects;

/**
 * @author leojie 2022/12/3 12:43
 */
public class IntRowKey extends BaseRowKey<Integer> {
    public IntRowKey(String value) {
        super(value, new ConvertIntRowKeyFunc());
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
        return this.computeRowValue().intValue() == intRowKey.computeRowValue().intValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

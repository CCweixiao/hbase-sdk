package com.github.CCweixiao.hbase.sdk.hql.rowkey;

import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Objects;

/**
 * @author leojie 2020/11/28 12:40 下午
 */
public class IntRowKey implements RowKey {
    private final int value;

    public IntRowKey(int value) {
        this.value = value;
    }

    @Override
    public byte[] toBytes() {
        return Bytes.toBytes(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
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

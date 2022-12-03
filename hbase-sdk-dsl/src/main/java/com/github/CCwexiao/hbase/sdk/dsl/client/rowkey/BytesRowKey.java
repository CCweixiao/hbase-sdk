package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.common.util.EncodingUtil;

import java.util.Arrays;

/**
 * @author leojie 2020/11/28 11:58 上午
 */
public class BytesRowKey implements RowKey<byte[]> {
    private final byte[] value;

    public BytesRowKey(byte[] value) {
        MyAssert.checkNotNull(value);
        this.value = value.clone();
    }

    @Override
    public byte[] toBytes() {
        return this.value.clone();
    }

    @Override
    public byte[] extractValue() {
        return this.value;
    }

    @Override
    public ColumnType columnType() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BytesRowKey)) {
            return false;
        }
        BytesRowKey that = (BytesRowKey) o;
        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }


    @Override
    public String toString() {
        return "BytesRowKey [key=" + EncodingUtil.toHexString(value) + "]";
    }

}

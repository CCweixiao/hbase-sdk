package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.EncodingUtil;
import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;

import java.util.Arrays;

/**
 * @author leojie 2020/11/28 11:58 上午
 */
public class BytesRowKey implements RowKey {
    private final byte[] key;

    public BytesRowKey(byte[] key) {
        MyAssert.checkNotNull(key);
        this.key = key.clone();
    }

    @Override
    public byte[] toBytes() {
        return key.clone();
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
        return Arrays.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(key);
    }


    @Override
    public String toString() {
        return "BytesRowKey [key=" + EncodingUtil.toHexString(key) + "]";
    }

}

package com.github.CCweixiao.hbase.sdk.hql.rowkey;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Objects;

/**
 * @author leojie 2020/11/28 12:42 下午
 */
public class StringRowKey implements RowKey {
    private final String key;

    public StringRowKey(String key) {
        MyAssert.notNull(key);
        this.key = key;
    }

    @Override
    public byte[] toBytes() {
        return Bytes.toBytes(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StringRowKey)) {
            return false;
        }
        StringRowKey that = (StringRowKey) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}

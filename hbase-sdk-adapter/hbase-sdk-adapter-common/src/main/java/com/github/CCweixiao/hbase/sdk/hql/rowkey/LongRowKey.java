package com.github.CCweixiao.hbase.sdk.hql.rowkey;

import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Objects;

/**
 * @author leojie 2020/11/28 12:44 下午
 */
public class LongRowKey implements RowKey {
    private long key;

    public LongRowKey(long key){
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LongRowKey that = (LongRowKey) o;
        return key == that.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}

package com.github.CCweixiao.hql.rowkey;

import com.github.CCwexiao.dsl.client.RowKey;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.hadoop.hbase.util.Bytes;

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
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

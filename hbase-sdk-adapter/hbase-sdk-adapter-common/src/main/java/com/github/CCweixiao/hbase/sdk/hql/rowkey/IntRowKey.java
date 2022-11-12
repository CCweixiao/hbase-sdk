package com.github.CCweixiao.hbase.sdk.hql.rowkey;

import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntRowKey intRowKey = (IntRowKey) o;

        return new EqualsBuilder().append(value, intRowKey.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

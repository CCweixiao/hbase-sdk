package com.github.CCweixiao.hbase.sdk.hql.rowkey;

import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.util.Util;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 12:42 下午
 */
public class StringRowKey implements RowKey {
    private final String key;

    public StringRowKey(String key) {
        Util.checkNull(key);
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
        StringRowKey that = (StringRowKey) o;
        return new EqualsBuilder().append(key, that.key).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(key).toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

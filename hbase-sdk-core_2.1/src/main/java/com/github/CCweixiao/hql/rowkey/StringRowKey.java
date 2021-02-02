package com.github.CCweixiao.hql.rowkey;

import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.util.Util;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
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

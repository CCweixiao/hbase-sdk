package com.github.CCwexiao.dsl.client.rowkey;

import com.github.CCwexiao.dsl.util.EncodingUtil;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.util.Util;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author leojie 2020/11/28 11:58 上午
 */
public class BytesRowKey implements RowKey {
    private final byte[] key;

    public BytesRowKey(byte[] key){
        Util.checkNull(key);

        this.key = key;
    }

    @Override
    public byte[] toBytes() {
        return key;
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
        return "BytesRowKey [key=" + EncodingUtil.toHexString(key) + "]";
    }

}

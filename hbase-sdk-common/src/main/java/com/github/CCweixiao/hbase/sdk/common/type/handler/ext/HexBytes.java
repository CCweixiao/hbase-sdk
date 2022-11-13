package com.github.CCweixiao.hbase.sdk.common.type.handler.ext;

import com.github.CCweixiao.hbase.sdk.common.util.EncodingUtil;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author leojie 2020/11/28 6:09 下午
 */
public class HexBytes {
    private final byte[] data;

    public HexBytes(byte[] data) {
        ObjUtil.checkIsNull(data);
        this.data = data;
    }

    public HexBytes(String hexStr) {
        ObjUtil.checkIsNull(hexStr);
        this.data = EncodingUtil.parseBytesFromHexString(hexStr);
    }

    public byte[] getData() {
        return data;
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
        return "HexBytes [bytes=" + EncodingUtil.toHexString(data) + "]";
    }

}

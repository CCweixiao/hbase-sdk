package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.Assert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

/**
 * @author leojie 2020/11/28 7:55 下午
 */
public class ByteHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == byte.class || type == Byte.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return new byte[] {(Byte) value};
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return bytes[0];
    }

    @Override
    public String convertToString(Object val) {
        Assert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Byte or byte.");

        byte b = (byte) val;
        return String.valueOf(b);
    }
}

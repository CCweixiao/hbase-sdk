package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.Assert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

/**
 * @author leojie 2020/11/28 7:54 下午
 */
public class CharHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == char.class || type == Character.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        char c = (Character) value;
        byte[] result = new byte[2];
        result[1] = (byte) c;
        result[0] = (byte) (c >>> 8);
        return result;
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        int c = 0;
        c ^= (bytes[0] & 0xFF);
        c = c << 8;
        c ^= (bytes[1] & 0xFF);
        return (char) c;
    }

    @Override
    public String convertToString(Object val) {
        Assert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Character or char.");

        char c = (char) val;
        return String.valueOf(c);
    }
}

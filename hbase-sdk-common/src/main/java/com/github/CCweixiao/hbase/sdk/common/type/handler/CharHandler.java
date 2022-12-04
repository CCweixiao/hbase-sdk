package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

/**
 * @author leojie 2020/11/28 7:54 下午
 */
public class CharHandler extends AbstractTypeHandler<Character> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return type == char.class || type == Character.class;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        char c = value.toString().charAt(0);
        byte[] result = new byte[2];
        result[1] = (byte) c;
        result[0] = (byte) (c >>> 8);
        return result;
    }

    @Override
    protected Object convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int c = 0;
        c ^= (bytes[0] & 0xFF);
        c = c << 8;
        c ^= (bytes[1] & 0xFF);
        return (char) c;
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, v -> v.toCharArray()[0]);
    }
}

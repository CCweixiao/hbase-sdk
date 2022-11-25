package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

/**
 * @author leojie 2020/11/28 7:50 下午
 */
public class LongHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == long.class || type == Long.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return long2Bytes((Long) value);
    }

    protected byte[] long2Bytes(long longValue) {
        byte[] result = new byte[Long.BYTES];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (longValue & 0xFF);
            longValue >>= Byte.SIZE;
        }
        return result;
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return bytes2Long(bytes);
    }

    protected long bytes2Long(byte[] bytes) {
        long values = 0;
        for (int i = (Long.BYTES - 1); i >= 0; i--) {
            values <<= Byte.SIZE;
            values |= (bytes[i] & 0xff);
        }
        return values;
    }


    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Long or long.");
        return val.toString();
    }
}

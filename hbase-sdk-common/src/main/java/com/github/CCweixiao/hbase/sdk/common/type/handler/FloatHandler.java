package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;

/**
 * @author leojie 2020/11/28 7:52 下午
 */
public class FloatHandler extends IntegerHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == float.class || type == Float.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return int2Bytes(Float.floatToIntBits((Float) value));
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return Float.intBitsToFloat(bytes2Int(bytes));
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Float or float.");
        return val.toString();
    }

    public Object convertObjectFromStr(String value) {
        return toObjectFromStr(value, Float::parseFloat);
    }
}

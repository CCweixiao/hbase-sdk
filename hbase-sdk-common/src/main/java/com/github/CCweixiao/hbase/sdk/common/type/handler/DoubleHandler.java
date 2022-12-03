package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.ByteUtil;

/**
 * @author leojie 2020/11/28 7:53 下午
 */
public class DoubleHandler extends AbstractTypeHandler<Double> {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == double.class || type == Double.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return double2Bytes((Double) value);
    }

    protected byte[] double2Bytes(double longValue) {
        return ByteUtil.long2Bytes(Double.doubleToLongBits(longValue));
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return bytes2Double(bytes);
    }

    protected double bytes2Double(byte[] bytes) {
        return Double.longBitsToDouble(ByteUtil.bytes2Long(bytes));
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Double or double.");
        return val.toString();
    }
    @Override
    public String extractTargetTypeStrValue(String value) {
        return toObjectFromStr(value, Double::parseDouble);
    }
}

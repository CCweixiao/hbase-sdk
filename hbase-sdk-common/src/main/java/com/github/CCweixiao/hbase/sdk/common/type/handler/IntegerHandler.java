package com.github.CCweixiao.hbase.sdk.common.type.handler;


import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

/**
 * @author leojie 2020/11/28 7:51 下午
 */
public class IntegerHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == int.class || type == Integer.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return int2Bytes((Integer) value);
    }

    protected byte[] int2Bytes(int intValue) {
        return new byte[]{
                (byte) (intValue & 0xFF),
                (byte) ((intValue >> 8) & 0xFF),
                (byte) ((intValue >> 16) & 0xFF),
                (byte) ((intValue >> 24) & 0xFF)
        };
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return bytes2Int(bytes);
    }

    protected int bytes2Int(byte[] bytes) {
        return bytes[0] & 0xFF |
                (bytes[1] & 0xFF) << 8 |
                (bytes[2] & 0xFF) << 16 |
                (bytes[3] & 0xFF) << 24;
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Integer or int.");
        return val.toString();
    }
}

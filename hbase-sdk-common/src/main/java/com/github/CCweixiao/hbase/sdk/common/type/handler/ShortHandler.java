package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.type.TypeConverter;

/**
 * @author leojie 2020/11/28 7:49 下午
 */
public class ShortHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == short.class || type == Short.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        short shortValue = (Short) value;
        byte[] b = new byte[Short.BYTES];
        b[0] = (byte) (shortValue & 0xff);
        b[1] = (byte) ((shortValue >> Byte.SIZE) & 0xff);
        return b;
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return (short) (bytes[0] & 0xff | (bytes[1] & 0xff) << Byte.SIZE);
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Short or short.");
        return val.toString();
    }

    public Object convertObjectFromStr(String value) {
        return toObjectFromStr(value, Short::parseShort);
    }
}

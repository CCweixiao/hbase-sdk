package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.Assert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

/**
 * @author leojie 2020/11/28 7:56 下午
 */
public class BooleanHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == boolean.class || type == Boolean.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return new byte[]{(byte) ((Boolean) value ? -1 : 0)};
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return bytes[0] != 0;
    }

    @Override
    public String convertToString(Object val) {
        Assert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Boolean or boolean.");
        return val.toString();
    }
}

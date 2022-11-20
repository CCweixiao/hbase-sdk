package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.Assert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.charset.Charset;

/**
 * @author leojie 2020/11/28 7:40 下午
 */
public class EnumHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type.isEnum();
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        String name = ((Enum<?>) value).name();
        return (name).getBytes(Charset.defaultCharset());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object convertToObject(Class type, byte[] bytes) {
        String name = new String(bytes, Charset.defaultCharset());
        return Enum.valueOf(type, name);
    }

    @Override
    public String convertToString(Object val) {
        Assert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Enum.");
        return val.toString();
    }
}

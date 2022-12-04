package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.HBaseColumnTypeCastException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.charset.Charset;

/**
 * @author leojie 2020/11/28 7:40 下午
 */
public class EnumHandler extends AbstractTypeHandler<Enum<?>> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return type.isEnum();
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        String name = ((Enum<?>) value).name();
        return (name).getBytes(Charset.defaultCharset());
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        throw new HBaseColumnTypeCastException("The string value cast to enum is unsupported");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object convertByteArrToObjVal(Class type, byte[] bytes) {
        String name = new String(bytes, Charset.defaultCharset());
        return Enum.valueOf(type, name);
    }

    @Override
    public String toString(Object val) {
        MyAssert.checkArgument(this.matchConverterType(val.getClass()), "The type of value " + val + " is not Enum.");
        return val.toString();
    }
}

package com.github.CCweixiao.hbase.sdk.common.type.handler;

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
        byte[] bytes;
        if (value != null) {
            String name = ((Enum<?>) value).name();
            bytes = (name).getBytes(Charset.defaultCharset());
        } else {
            bytes = new byte[0];
        }
        return bytes;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Object convertToObject(Class type, byte[] bytes) {
        String name = new String(bytes, Charset.defaultCharset());
        return Enum.valueOf(type, name);
    }
}

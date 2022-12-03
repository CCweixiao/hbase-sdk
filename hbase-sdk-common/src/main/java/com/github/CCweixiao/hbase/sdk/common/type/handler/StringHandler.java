package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.charset.Charset;

/**
 * @author leojie 2020/11/28 7:48 下午
 */
public class StringHandler extends AbstractTypeHandler<String> {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == String.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return ((String) value).getBytes(Charset.defaultCharset());
    }

    @Override
    protected String convertToObject(Class<?> type, byte[] bytes) {
        return new String(bytes, Charset.defaultCharset());
    }

    @Override
    public String convertToString(Object val) {
        return val.toString();
    }

    @Override
    public String extractTargetTypeStrValue(String value) {
        return value;
    }
}

package com.github.CCweixiao.hbase.sdk.common.type.handler;


import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.charset.Charset;

/**
 * @author leojie 2020/11/28 7:48 下午
 */
public class StringHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == String.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        byte[] bytes;
        if (value != null) {
            bytes = ((String) value).getBytes(Charset.defaultCharset());
        } else {
            bytes = new byte[0];
        }
        return bytes;
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return new String(bytes, Charset.defaultCharset());
    }
}

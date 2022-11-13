package com.github.CCweixiao.hbase.sdk.common.type.handler.string;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.charset.Charset;

/**
 * @author leojie 2020/11/28 7:57 下午
 */
public class StringIntegerHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == int.class || type == Integer.class;
    }


    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        byte[] bytes;
        if (value != null) {
            bytes = (String.valueOf(value)).getBytes(Charset.defaultCharset());
        } else {
            bytes = new byte[0];
        }
        return bytes;
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        String str = new String(bytes, Charset.defaultCharset());
        return Integer.parseInt(str);
    }
}

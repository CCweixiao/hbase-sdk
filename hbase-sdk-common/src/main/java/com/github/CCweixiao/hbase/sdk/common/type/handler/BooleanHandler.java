package com.github.CCweixiao.hbase.sdk.common.type.handler;

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
        return new byte[]{(byte)((Boolean) value ? -1 : 0)};
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        if (bytes.length != 1) {
            throw new IllegalArgumentException("Array has wrong size: " + bytes.length);
        } else {
            return bytes[0] != 0;
        }
    }
}

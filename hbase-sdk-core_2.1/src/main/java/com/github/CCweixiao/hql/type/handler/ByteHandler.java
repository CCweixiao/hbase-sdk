package com.github.CCweixiao.hql.type.handler;

import com.github.CCweixiao.hql.type.AbstractTypeHandler;
import com.github.CCwexiao.dsl.util.Util;

/**
 * @author leojie 2020/11/28 7:55 下午
 */
public class ByteHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == byte.class || type == Byte.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return new byte[] { ((Byte) value).byteValue() };
    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        Util.checkLength(bytes, 1);
        return bytes[0];
    }
}

package com.github.CCweixiao.hql.type.handler;

import com.github.CCweixiao.hql.type.AbstractTypeHandler;
import com.github.CCwexiao.dsl.util.Util;

/**
 * @author leojie 2020/11/28 7:54 下午
 */
public class CharacterHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == char.class || type == Character.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        char c = ((Character) value).charValue();
        byte[] result = new byte[2];
        result[1] = (byte) c;
        result[0] = (byte) (c >>> 8);
        return result;
    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        Util.checkLength(bytes, 2);

        int c = 0;
        c ^= (bytes[0] & 0xFF);
        c = c << 8;
        c ^= (bytes[1] & 0xFF);
        return (char) c;
    }
}

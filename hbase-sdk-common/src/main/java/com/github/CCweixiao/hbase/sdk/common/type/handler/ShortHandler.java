package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author leojie 2020/11/28 7:49 下午
 */
public class ShortHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == short.class || type == Short.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        byte[] bytes;
        if (value != null) {
            ByteBuffer buffer = ByteBuffer.allocate(2);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.putShort((Short) value);
            bytes = buffer.array();
        } else {
            bytes = new byte[0];
        }
        return bytes;
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put(bytes);
        return buffer.getShort(0);
    }
}

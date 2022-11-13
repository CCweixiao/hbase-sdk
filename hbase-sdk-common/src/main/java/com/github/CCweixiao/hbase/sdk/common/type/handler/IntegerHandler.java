package com.github.CCweixiao.hbase.sdk.common.type.handler;


import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author leojie 2020/11/28 7:51 下午
 */
public class IntegerHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == int.class || type == Integer.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        byte[] bytes;
        if (value != null) {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.putInt((Integer) value);
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
        return buffer.getInt(0);
    }
}

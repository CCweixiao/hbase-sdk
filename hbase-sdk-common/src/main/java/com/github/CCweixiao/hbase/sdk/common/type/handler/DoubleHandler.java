package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author leojie 2020/11/28 7:53 下午
 */
public class DoubleHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == double.class || type == Double.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        byte[] bytes;
        if (value != null) {
            ByteBuffer buffer = ByteBuffer.allocate(8);
            buffer.order(ByteOrder.BIG_ENDIAN);
            buffer.putDouble((Double) value);
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
        return buffer.getDouble(0);
    }
}

package com.github.CCweixiao.hbase.sdk.common.type;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseTypeHandlerException;
import com.github.CCweixiao.hbase.sdk.common.lang.Assert;

import java.nio.ByteBuffer;

/**
 * @author leojie 2022/11/13 13:03
 */
public abstract class AbstractTypeHandler implements TypeHandler {
    /**
     * Determine whether the type meets the conditions for processing.
     *
     * @param type variable type
     * @return true or false
     */
    protected abstract boolean matchTypeHandler(Class<?> type);

    protected abstract byte[] convertToBytes(Class<?> type, Object value);

    protected abstract Object convertToObject(Class<?> type, byte[] bytes);

    public byte[] toBytes(Class<?> type, Object value) {
        Assert.notNull(type, "The type of value must be not null.");
        if (value == null) {
            return null;
        }
        if (!matchTypeHandler(type)) {
            throw new HBaseTypeHandlerException(String.format("Wrong type %s to handle.", type.getName()));
        }
        return convertToBytes(type, value);
    }

    @Override
    public byte[] convertToBytes(Object val) {
        return toBytes(val.getClass(), val);
    }

    @Override
    public ByteBuffer toByteBuffer(Class<?> type, Object val) {
        byte[] bytes = toBytes(type, val);
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    @Override
    public ByteBuffer convertToByteBuffer(Object val) {
        return toByteBuffer(val.getClass(), val);
    }

    public Object toObject(Class<?> type, byte[] bytes) {
        Assert.notNull(type, "The type of value must be not null.");
        if (!matchTypeHandler(type)) {
            throw new HBaseTypeHandlerException(String.format("Wrong type %s to handle.", type.getName()));
        }
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return convertToObject(type, bytes);
    }

    @Override
    public Object toObject(Class<?> type, ByteBuffer buffer) {
        if (buffer == null) {
            return null;
        }
        buffer.flip();
        int len = buffer.limit() - buffer.position();
        byte[] bytes = new byte[len];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get();
        }
        return toObject(type, bytes);
    }
}

package com.github.CCweixiao.hbase.sdk.common.type;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseColumnTypeCastException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseTypeHandlerException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;

import java.nio.ByteBuffer;
/**
 * @author leojie 2022/11/13 13:03
 */
public abstract class AbstractTypeHandler<T> implements TypeHandler<T> {

    /**
     * Determine whether the type meets the conditions for processing.
     *
     * @param type variable type
     * @return true or false
     */
    protected abstract boolean matchConverterType(Class<?> type);

    protected abstract byte[] convertObjValToByteArr(Class<?> type, Object obj);

    protected abstract Object convertByteArrToObjVal(Class<?> type, byte[] bytes);

    @Override
    public byte[] toBytes(Class<?> type, Object value) {
        MyAssert.notNull(type, "The class type of value must be not null.");
        if (value == null) {
            return null;
        }
        if (!matchConverterType(type)) {
            throw new HBaseTypeHandlerException(String.format("Wrong type %s to handle.", type.getName()));
        }
        return convertObjValToByteArr(type, value);
    }

    @Override
    public byte[] toBytes(Object value) {
        return toBytes(value.getClass(), value);
    }

    @Override
    public ByteBuffer toByteBuffer(Class<?> type, Object value) {
        byte[] bytes = toBytes(type, value);
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        return buffer;
    }

    @Override
    public ByteBuffer toByteBuffer(Object value) {
        return toByteBuffer(value.getClass(), value);
    }

    @Override
    public Object toObject(Class<?> type, byte[] bytes) {
        MyAssert.notNull(type, "The type of value must be not null.");
        if (!matchConverterType(type)) {
            throw new HBaseTypeHandlerException(String.format("Wrong type %s to handle.", type.getName()));
        }
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return convertByteArrToObjVal(type, bytes);
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

    @Override
    public String toString(String value, TypeConverter<T> typeConverter) {
        try {
            return String.valueOf(typeConverter.convertTo(value));
        } catch (Exception e) {
            throw new HBaseColumnTypeCastException(String.format("The value [%s] cast another type error.", value), e);
        }
    }

    protected T convertByteArrToObjVal(String value, TypeConverter<T> typeConverter) {
        try {
            return typeConverter.convertTo(value);
        } catch (Exception e) {
            throw new HBaseColumnTypeCastException(String.format("The value [%s] cast another type error.", value), e);
        }
    }

    @Override
    public String toString(Object val) {
        if (val == null) {
            return null;
        }
        return val.toString();
    }
}

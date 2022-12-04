package com.github.CCweixiao.hbase.sdk.common.type;

import java.nio.ByteBuffer;

/**
 * @author leojie 2022/11/13 12:57
 */
public interface TypeHandler<T> {

    byte[] toBytes(Class<?> type, Object value);

    byte[] toBytes(Object value);

    String toString(Object value);

    ByteBuffer toByteBuffer(Class<?> type, Object value);

    ByteBuffer toByteBuffer(Object value);

    Object toObject(Class<?> type, byte[] bytes);

    Object toObject(Class<?> type, ByteBuffer buffer);

    String toString(String value, TypeConverter<T> typeConverter);

    String extractMatchTtypeValue(String value);
}

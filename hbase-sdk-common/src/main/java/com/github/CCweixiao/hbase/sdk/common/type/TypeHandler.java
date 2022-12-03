package com.github.CCweixiao.hbase.sdk.common.type;

import java.nio.ByteBuffer;

/**
 * @author leojie 2022/11/13 12:57
 */
public interface TypeHandler<T> {

    byte[] toBytes(Class<?> type, Object value);

    byte[] convertToBytes(Object value);

    String convertToString(Object value);

    ByteBuffer toByteBuffer(Class<?> type, Object value);

    ByteBuffer convertToByteBuffer(Object value);

    Object toObject(Class<?> type, byte[] bytes);

    Object toObject(Class<?> type, ByteBuffer buffer);

    String toObjectFromStr(String value, TypeConverter<T> typeConverter);
}

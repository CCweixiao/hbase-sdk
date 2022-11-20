package com.github.CCweixiao.hbase.sdk.common.type;

import java.nio.ByteBuffer;

/**
 * @author leojie 2022/11/13 12:57
 */
public interface TypeHandler {

    byte[] toBytes(Class<?> type, Object val);

    byte[] convertToBytes(Object val);

    String convertToString(Object val);

    ByteBuffer toByteBuffer(Class<?> type, Object val);

    ByteBuffer convertToByteBuffer(Object val);

    Object toObject(Class<?> type, byte[] bytes);

    Object toObject(Class<?> type, ByteBuffer buffer);
}

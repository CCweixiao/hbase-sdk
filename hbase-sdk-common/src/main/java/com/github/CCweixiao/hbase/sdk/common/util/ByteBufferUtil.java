package com.github.CCweixiao.hbase.sdk.common.util;

import com.github.CCweixiao.hbase.sdk.common.type.TypeHandlerFactory;

import java.nio.ByteBuffer;

/**
 * @author leojie 2020/12/27 2:36 下午
 */
public class ByteBufferUtil {

    public static String byteBufferToString(ByteBuffer buffer) {
        return TypeHandlerFactory.toStrFromBuffer(buffer);
    }

    public static Object byteBufferToObj( Class<?> clazz, ByteBuffer buffer) {
        return TypeHandlerFactory.toObject(clazz, buffer);
    }

    public static ByteBuffer toByteBuffer(Object val) {
        return TypeHandlerFactory.toByteBuffer(val);
    }

    public static ByteBuffer toStrByteBuffer(Object val) {
        return TypeHandlerFactory.toStrByteBuffer(val);
    }

    public static ByteBuffer toByterBufferFromStr(String val) {
        return TypeHandlerFactory.toByteBufferFromStr(val);
    }

    public static String bytesIncrement(String str) {
        if (StrUtil.isBlank(str)) {
            return "";
        }
        final char[] chars = str.toCharArray();
        for (int i = chars.length - 1; i < chars.length; i++) {
            if (chars[i] != 0xff) {
                chars[i] += 1;
                return String.valueOf(chars);
            }
        }
        return "";
    }

}

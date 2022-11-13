package com.github.CCweixiao.hbase.sdk.common.util;

import com.github.CCweixiao.hbase.sdk.common.type.TypeHandlerFactory;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author leojie 2020/12/27 2:36 下午
 */
public class ByteBufferUtil {

    public static String byteBufferToString(ByteBuffer buffer) {
        CharBuffer charBuffer;
        try {
            Charset charset = Charset.forName(HBaseThriftProtocol.CHAR_SET);
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer);
            buffer.flip();
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Object byteBufferToObj(Class<?> type, ByteBuffer buffer) {
        return TypeHandlerFactory.toObject(type, buffer);
    }

    public static ByteBuffer toByteBuffer(Object val) {
        return TypeHandlerFactory.toByteBuffer(val);
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

package com.github.CCweixiao.hbase.sdk.common.util;

/**
 * @author leojie 2022/12/3 20:03
 */
public final class ByteUtil {
    private ByteUtil() {

    }

    public static byte[] int2Bytes(int intValue) {
        return new byte[]{
                (byte) (intValue & 0xFF),
                (byte) ((intValue >> 8) & 0xFF),
                (byte) ((intValue >> 16) & 0xFF),
                (byte) ((intValue >> 24) & 0xFF)
        };
    }

    public static int bytes2Int(byte[] bytes) {
        return bytes[0] & 0xFF |
                (bytes[1] & 0xFF) << 8 |
                (bytes[2] & 0xFF) << 16 |
                (bytes[3] & 0xFF) << 24;
    }

    public static byte[] long2Bytes(long longValue) {
        byte[] result = new byte[Long.BYTES];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (longValue & 0xFF);
            longValue >>= Byte.SIZE;
        }
        return result;
    }

    public static long bytes2Long(byte[] bytes) {
        long values = 0;
        for (int i = (Long.BYTES - 1); i >= 0; i--) {
            values <<= Byte.SIZE;
            values |= (bytes[i] & 0xff);
        }
        return values;
    }
}

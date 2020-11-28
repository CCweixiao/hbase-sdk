package com.github.CCwexiao.dsl.util;


import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2020/11/28 12:05 下午
 */
public class BytesUtil {
    /** Empty bytes. */
    public static final byte[] EMPTY = {};

    /** Bytes [ZERO]. */
    public static final byte[] ZERO  = { (byte) 0 };

    /** Bytes [ONE]. */
    public static final byte[] ONE   = { (byte) 1 };

    /**
     * Sub bytes.
     * */
    public static byte[] subBytes(byte[] bytes, int index, int length) {
        Util.checkNull(bytes);
        Util.check(index >= 0);
        Util.check(length >= 0);
        Util.check(index + length <= bytes.length);

        byte[] result = new byte[length];
        System.arraycopy(bytes, index, result, 0, length);
        return result;
    }

    /**
     * p's index of bytes.
     * */
    public static int index(byte[] bytes, byte[] p) {
        Util.checkNull(bytes);
        Util.checkNull(p);
        Util.check(bytes.length > 0);
        Util.check(p.length > 0);

        for (int i = 0; i + p.length <= bytes.length; i++) {

            boolean match = true;

            for (int j = 0; j < p.length; j++) {
                if (bytes[i + j] != p[j]) {
                    match = false;
                    break;
                }
            }

            if (match) {
                return i;
            }

        }

        return -1;
    }

    /**
     * Split bytes on pattern p.
     * */
    public static List<byte[]> split(byte[] bytes, byte[] p) {
        Util.checkNull(bytes);
        Util.checkNull(p);
        Util.check(bytes.length > 0);
        Util.check(p.length > 0);

        List<byte[]> result = new ArrayList<byte[]>();
        byte[] tem = bytes;
        for (int index = index(tem, p); index != -1; index = index(tem, p)) {
            result.add(subBytes(tem, 0, index));
            tem = subBytes(tem, index + p.length, tem.length - index - p.length);
            if (tem.length == 0) {
                break;
            }
        }

        if (tem.length > 0) {
            result.add(tem);
        }

        return result;
    }

    private BytesUtil() {
    }
}

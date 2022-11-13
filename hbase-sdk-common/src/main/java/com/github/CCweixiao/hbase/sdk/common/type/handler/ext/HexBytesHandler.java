package com.github.CCweixiao.hbase.sdk.common.type.handler.ext;


import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;

/**
 * @author leojie 2020/11/28 7:58 下午
 */
public class HexBytesHandler extends AbstractTypeHandler {
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type.getClass().getSimpleName().equalsIgnoreCase("HexBytes");
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        String hexString = (String) value;
        return fromHex(hexString);
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return toHex(bytes, 0, bytes.length);
    }

    private int hexCharToNibble(char ch) {
        if (ch <= '9' && ch >= '0') {
            return ch - 48;
        } else if (ch >= 'a' && ch <= 'f') {
            return ch - 97 + 10;
        } else if (ch >= 'A' && ch <= 'F') {
            return ch - 65 + 10;
        } else {
            throw new IllegalArgumentException("Invalid hex char: " + ch);
        }
    }

    private byte hexCharsToByte(char c1, char c2) {
        return (byte)(hexCharToNibble(c1) << 4 | hexCharToNibble(c2));
    }

    public byte[] fromHex(String hex) {
        ObjUtil.checkArgument(hex.length() % 2 == 0, "length must be a multiple of 2");
        int len = hex.length();
        byte[] b = new byte[len / 2];
        for(int i = 0; i < len; i += 2) {
            b[i / 2] = hexCharsToByte(hex.charAt(i), hex.charAt(i + 1));
        }
        return b;
    }

    public static String toHex(byte[] b, int offset, int length) {
        ObjUtil.checkArgument(length <= 1073741823);
        int numChars = length * 2;
        char[] ch = new char[numChars];

        for(int i = 0; i < numChars; i += 2) {
            byte d = b[offset + i / 2];
            ch[i] = HEX_CHARS[d >> 4 & 15];
            ch[i + 1] = HEX_CHARS[d & 15];
        }

        return new String(ch);
    }
}

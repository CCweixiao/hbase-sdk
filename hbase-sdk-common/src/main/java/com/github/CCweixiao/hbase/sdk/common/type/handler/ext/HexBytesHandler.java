package com.github.CCweixiao.hbase.sdk.common.type.handler.ext;


import com.github.CCweixiao.hbase.sdk.common.exception.HBaseTypeHandlerException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

/**
 * @author leojie 2020/11/28 7:58 下午
 */
public class HexBytesHandler extends AbstractTypeHandler {
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return "HexBytes".equalsIgnoreCase(type.getSimpleName());
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return decode((String) value);
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return new String(encode(bytes));
    }

    public char[] encode(byte[] data) {
        final int len = data.length;
        final char[] out = new char[len << 1];
        // two characters from the hex value.
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = HEX_CHARS[(0xF0 & data[i]) >>> 4];
            out[j++] = HEX_CHARS[0x0F & data[i]];
        }
        return out;
    }


    public byte[] decode(CharSequence encoded) {
        if (StringUtil.isEmpty(encoded)) {
            return null;
        }

        encoded = StringUtil.cleanBlank(encoded);
        int len = encoded.length();

        if ((len & 0x01) != 0) {
            // 如果提供的数据是奇数长度，则前面补0凑偶数
            encoded = "0" + encoded;
            len = encoded.length();
        }

        final byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(encoded.charAt(j), j) << 4;
            j++;
            f = f | toDigit(encoded.charAt(j), j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    private int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit < 0) {
            throw new HBaseTypeHandlerException(String.format("Illegal hexadecimal character %s at index %s", ch, index));
        }
        return digit;
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not HexBytes.");
        return val.toString();
    }
}

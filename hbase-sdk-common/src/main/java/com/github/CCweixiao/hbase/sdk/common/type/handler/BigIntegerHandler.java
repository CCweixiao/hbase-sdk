package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * @author leojie 2022/11/19 22:24
 */
public class BigIntegerHandler extends BigDecimalHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return BigInteger.class == type;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        BigInteger bi = (BigInteger) value;
        return bi.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return toBigInteger(new String(bytes, StandardCharsets.UTF_8));
    }

    protected BigInteger toBigInteger(String numberStr) {
        if (StringUtil.isBlank(numberStr)) {
            return BigInteger.ZERO;
        }

        try {
            final Number number = parseNumber(numberStr);
            if (number instanceof BigDecimal) {
                return ((BigDecimal) number).toBigInteger();
            } else {
                return new BigInteger(number.toString());
            }
        } catch (Exception ignore) {
        }

        return new BigInteger(numberStr);
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not BigInteger.");
        return val.toString();
    }

    public Object convertObjectFromStr(String value) {
        return toObjectFromStr(value, Boolean::new);
    }
}

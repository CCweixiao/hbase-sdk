package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author leojie 2022/11/19 22:17
 */
public class BigDecimalHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return BigDecimal.class == type;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        BigDecimal bd = (BigDecimal) value;
        return bd.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return toBigDecimal(new String(bytes, StandardCharsets.UTF_8));
    }

    protected BigDecimal toBigDecimal(String numberStr) {
        if (StringUtil.isBlank(numberStr)) {
            return BigDecimal.ZERO;
        }

        try {
            final Number number = parseNumber(numberStr);
            if (number instanceof BigDecimal) {
                return (BigDecimal) number;
            } else {
                return new BigDecimal(number.toString());
            }
        } catch (Exception ignore) {}

        return new BigDecimal(numberStr);
    }

    protected Number parseNumber(String numberStr) throws NumberFormatException {
        if (StringUtil.startWithIgnoreCase(numberStr, "0x")) {
            // 0x04表示16进制数
            return Long.parseLong(numberStr.substring(2), 16);
        }

        try {
            final NumberFormat format = NumberFormat.getInstance();
            if (format instanceof DecimalFormat) {
                // issue#1818@Github
                // 当字符串数字超出double的长度时，会导致截断，此处使用BigDecimal接收
                ((DecimalFormat) format).setParseBigDecimal(true);
            }
            return format.parse(numberStr);
        } catch (ParseException e) {
            final NumberFormatException nfe = new NumberFormatException(e.getMessage());
            nfe.initCause(e);
            throw nfe;
        }
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not BigDecimal.");
        return val.toString();
    }
}

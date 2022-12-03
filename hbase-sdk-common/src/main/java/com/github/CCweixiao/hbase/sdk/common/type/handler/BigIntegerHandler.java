package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.NumberUtil;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * @author leojie 2022/11/19 22:24
 */
public class BigIntegerHandler extends AbstractTypeHandler<BigInteger> {
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
        return NumberUtil.toBigInteger(new String(bytes, StandardCharsets.UTF_8));
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not BigInteger.");
        return val.toString();
    }

    @Override
    public String extractTargetTypeStrValue(String value) {
        return toObjectFromStr(value, BigInteger::new);
    }
}

package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.NumberUtil;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * @author leojie 2022/11/19 22:24
 */
public class BigIntegerHandler extends AbstractTypeHandler<BigInteger> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return BigInteger.class == type;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        BigInteger bi = (BigInteger) value;
        return bi.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected Object convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        return NumberUtil.toBigInteger(new String(bytes, StandardCharsets.UTF_8));
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, BigInteger::new);
    }
}

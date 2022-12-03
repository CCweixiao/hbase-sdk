package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.NumberUtil;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * @author leojie 2022/11/19 22:17
 */
public class BigDecimalHandler extends AbstractTypeHandler<BigDecimal> {
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
    protected BigDecimal convertToObject(Class<?> type, byte[] bytes) {
        return NumberUtil.toBigDecimal(new String(bytes, StandardCharsets.UTF_8));
    }

    @Override
    public String extractTargetTypeStrValue(String value) {
        return toObjectFromStr(value, BigDecimal::new);
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not BigDecimal.");
        return val.toString();
    }
}

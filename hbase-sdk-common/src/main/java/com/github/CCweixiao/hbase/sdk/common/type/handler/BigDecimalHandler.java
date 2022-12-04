package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;
import java.math.BigDecimal;

/**
 * @author leojie 2022/11/19 22:17
 */
public class BigDecimalHandler extends AbstractTypeHandler<BigDecimal> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return BigDecimal.class == type;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        BigDecimal val = convertByteArrToObjVal(value.toString(), BigDecimal::new);
        return BytesUtil.toBytes(val);
    }

    @Override
    protected BigDecimal convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return BytesUtil.toBigDecimal(bytes);
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, BigDecimal::new);
    }
}

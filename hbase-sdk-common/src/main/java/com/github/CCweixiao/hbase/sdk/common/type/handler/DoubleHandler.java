package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;

/**
 * @author leojie 2020/11/28 7:53 下午
 */
public class DoubleHandler extends AbstractTypeHandler<Double> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return type == double.class || type == Double.class;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        double val = convertByteArrToObjVal(value.toString(), Double::parseDouble);
        return BytesUtil.toBytes(val);
    }

    @Override
    protected Object convertByteArrToObjVal(Class<?> type, byte[] bytes) {
       if (bytes == null) {
           return null;
       }
       return BytesUtil.toDouble(bytes);
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, Double::parseDouble);
    }
}

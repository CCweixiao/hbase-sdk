package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;

/**
 * @author leojie 2020/11/28 7:52 下午
 */
public class FloatHandler extends AbstractTypeHandler<Float> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return type == float.class || type == Float.class;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        float val = convertByteArrToObjVal(value.toString(), Float::parseFloat);
        return BytesUtil.toBytes(val);
    }

    @Override
    protected Object convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return BytesUtil.toFloat(bytes);
    }
    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, Float::parseFloat);
    }
}

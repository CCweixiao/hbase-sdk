package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;

/**
 * @author leojie 2020/11/28 7:56 下午
 */
public class BooleanHandler extends AbstractTypeHandler<Boolean> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return type == boolean.class || type == Boolean.class;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        boolean val = convertByteArrToObjVal(value.toString(), Boolean::parseBoolean);
        return BytesUtil.toBytes(val);
    }

    @Override
    protected Object convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return BytesUtil.toBoolean(bytes);
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, Boolean::new);
    }
}

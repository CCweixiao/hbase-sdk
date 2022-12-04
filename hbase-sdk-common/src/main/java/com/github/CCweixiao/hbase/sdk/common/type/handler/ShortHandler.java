package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;

/**
 * @author leojie 2020/11/28 7:49 下午
 */
public class ShortHandler extends AbstractTypeHandler<Short> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return type == short.class || type == Short.class;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        short val = convertByteArrToObjVal(value.toString(), Short::parseShort);
        return BytesUtil.toBytes(val);
    }

    @Override
    protected Short convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        return BytesUtil.toShort(bytes);
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, Short::parseShort);
    }
}

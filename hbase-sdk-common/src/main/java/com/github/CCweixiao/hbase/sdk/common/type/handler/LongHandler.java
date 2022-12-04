package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;

/**
 * @author leojie 2020/11/28 7:50 下午
 */
public class LongHandler extends AbstractTypeHandler<Long> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return type == long.class || type == Long.class;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        long val = convertByteArrToObjVal(value.toString(), Long::parseLong);
        return BytesUtil.toBytes(val);
    }

    @Override
    protected Long convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return BytesUtil.toLong(bytes);
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, Long::parseLong);
    }
}

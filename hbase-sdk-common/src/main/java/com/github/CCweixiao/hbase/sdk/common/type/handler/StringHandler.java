package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;

/**
 * @author leojie 2020/11/28 7:48 下午
 */
public class StringHandler extends AbstractTypeHandler<String> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return type == String.class;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        return BytesUtil.toBytes(value.toString());
    }

    @Override
    protected String convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return BytesUtil.toString(bytes);
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return value;
    }
}

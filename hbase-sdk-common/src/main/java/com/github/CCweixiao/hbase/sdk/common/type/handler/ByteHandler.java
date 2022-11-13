package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;

/**
 * @author leojie 2020/11/28 7:55 下午
 */
public class ByteHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == byte.class || type == Byte.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return new byte[] {(Byte) value};
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        ObjUtil.checkLength(bytes, 1);
        return bytes[0];
    }
}

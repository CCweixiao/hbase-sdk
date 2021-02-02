package com.github.CCweixiao.hql.type.handler.ext;

import com.github.CCweixiao.hql.type.AbstractTypeHandler;
import com.github.CCwexiao.dsl.type.HexBytes;

/**
 * @author leojie 2020/11/28 7:58 下午
 */
public class HexBytesHandler  extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == HexBytes.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return ((HexBytes) value).getData();
    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        return new HexBytes(bytes);
    }
}

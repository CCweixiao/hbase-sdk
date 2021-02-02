package com.github.CCweixiao.hql.type.handler;

import com.github.CCweixiao.hql.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 7:50 下午
 */
public class LongHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == long.class || type == Long.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return Bytes.toBytes((Long) value);
    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        return Bytes.toLong(bytes);
    }
}

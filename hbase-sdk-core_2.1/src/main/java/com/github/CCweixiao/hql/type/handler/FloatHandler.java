package com.github.CCweixiao.hql.type.handler;

import com.github.CCweixiao.hql.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 7:52 下午
 */
public class FloatHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == float.class || type == Float.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return Bytes.toBytes((Float) value);
    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        return Bytes.toFloat(bytes);
    }
}

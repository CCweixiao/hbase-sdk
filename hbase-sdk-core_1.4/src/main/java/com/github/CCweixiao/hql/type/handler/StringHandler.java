package com.github.CCweixiao.hql.type.handler;

import com.github.CCweixiao.hql.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 7:48 下午
 */
public class StringHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == String.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        return  Bytes.toBytes((String) value);
    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        return Bytes.toString(bytes);
    }
}

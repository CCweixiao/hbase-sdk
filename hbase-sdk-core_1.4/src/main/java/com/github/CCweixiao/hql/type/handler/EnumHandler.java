package com.github.CCweixiao.hql.type.handler;

import com.github.CCweixiao.hql.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 7:40 下午
 */
public class EnumHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type.isEnum();
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        String name = ((Enum<?>) value).name();
        return Bytes.toBytes(name);
    }

    @Override
    protected Object innerToObject(Class type, byte[] bytes) {
        String name = Bytes.toString(bytes);
        return Enum.valueOf(type, name);
    }
}

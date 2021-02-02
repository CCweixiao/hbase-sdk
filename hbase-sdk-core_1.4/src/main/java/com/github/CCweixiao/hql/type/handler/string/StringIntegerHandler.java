package com.github.CCweixiao.hql.type.handler.string;

import com.github.CCweixiao.hql.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 7:57 下午
 */
public class StringIntegerHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == int.class || type == Integer.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        String str = String.valueOf(((Integer) value));
        return Bytes.toBytes(str);
    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        String str = Bytes.toString(bytes);
        return Integer.parseInt(str);
    }
}

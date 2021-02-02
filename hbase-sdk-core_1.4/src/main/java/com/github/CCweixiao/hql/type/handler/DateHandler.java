package com.github.CCweixiao.hql.type.handler;

import com.github.CCweixiao.hql.type.AbstractTypeHandler;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Date;

/**
 * @author leojie 2020/11/28 7:53 下午
 */
public class DateHandler extends AbstractTypeHandler {
    @Override
    protected boolean aboutToHandle(Class<?> type) {
        return type == Date.class;
    }

    @Override
    protected byte[] innerToBytes(Class<?> type, Object value) {
        long time = ((Date) value).getTime();
        return Bytes.toBytes(time);
    }

    @Override
    protected Object innerToObject(Class<?> type, byte[] bytes) {
        long time = Bytes.toLong(bytes);
        return new Date(time);
    }
}

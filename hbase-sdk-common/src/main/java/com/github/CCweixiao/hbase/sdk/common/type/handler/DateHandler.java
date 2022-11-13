package com.github.CCweixiao.hbase.sdk.common.type.handler;


import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

/**
 * @author leojie 2020/11/28 7:53 下午
 */
public class DateHandler extends AbstractTypeHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == Date.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        byte[] bytes;
        if (value != null) {
            ByteBuffer buffer = ByteBuffer.allocate(8);
            buffer.order(ByteOrder.BIG_ENDIAN);
            long time = ((Date) value).getTime();
            buffer.putLong(time);
            bytes = buffer.array();
        } else {
            bytes = new byte[0];
        }
        return bytes;
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.put(bytes);
        long time = buffer.getLong(0);
        return new Date(time);
    }
}

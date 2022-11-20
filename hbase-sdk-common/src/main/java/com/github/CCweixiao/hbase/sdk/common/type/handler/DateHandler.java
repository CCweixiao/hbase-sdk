package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.Assert;

import java.util.Date;

/**
 * @author leojie 2020/11/28 7:53 下午
 */
public class DateHandler extends LongHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == Date.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        long time = ((Date) value).getTime();
        return long2Bytes(time);
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        long time = bytes2Long(bytes);
        return new Date(time);
    }

    @Override
    public String convertToString(Object val) {
        Assert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Date.");
        Date d = (Date) val;
        return super.convertToString(d.getTime());
    }
}

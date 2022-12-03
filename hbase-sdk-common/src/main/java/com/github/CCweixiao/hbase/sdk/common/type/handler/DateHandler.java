package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.ByteUtil;

import java.util.Date;

/**
 * @author leojie 2020/11/28 7:53 下午
 */
public class DateHandler extends AbstractTypeHandler<Date> {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == Date.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        long time = ((Date) value).getTime();
        return ByteUtil.long2Bytes(time);
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        long time = ByteUtil.bytes2Long(bytes);
        return new Date(time);
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Date.");
        Date d = (Date) val;
        return String.valueOf(d.getTime());
    }
    @Override
    public String extractTargetTypeStrValue(String value) {
        return toObjectFromStr(value, v -> new Date(Long.parseLong(v)));
    }
}

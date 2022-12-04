package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.BytesUtil;

import java.util.Date;

/**
 * @author leojie 2020/11/28 7:53 下午
 */
public class DateHandler extends AbstractTypeHandler<Date> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return type == Date.class;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }
        long time = ((Date) value).getTime();
        return BytesUtil.toBytes(time);
    }

    @Override
    protected Object convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        long time = BytesUtil.toLong(bytes);
        return new Date(time);
    }

    @Override
    public String toString(Object val) {
        MyAssert.checkArgument(this.matchConverterType(val.getClass()), "The type of value " + val + " is not Date.");
        Date d = (Date) val;
        return String.valueOf(d.getTime());
    }
    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, v -> new Date(Long.parseLong(v)));
    }
}

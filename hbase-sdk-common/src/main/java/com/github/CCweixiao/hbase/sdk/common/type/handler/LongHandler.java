package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.ByteUtil;

/**
 * @author leojie 2020/11/28 7:50 下午
 */
public class LongHandler extends AbstractTypeHandler<Long> {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == long.class || type == Long.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return ByteUtil.long2Bytes((Long) value);
    }

    @Override
    protected Long convertToObject(Class<?> type, byte[] bytes) {
        return ByteUtil.bytes2Long(bytes);
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Long or long.");
        return val.toString();
    }
    @Override
    public String extractTargetTypeStrValue(String value) {
        return toObjectFromStr(value, Long::parseLong);
    }
}

package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.ByteUtil;

/**
 * @author leojie 2020/11/28 7:51 下午
 */
public class IntegerHandler extends AbstractTypeHandler<Integer> {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return type == int.class || type == Integer.class;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        return ByteUtil.int2Bytes((Integer) value);
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        return ByteUtil.bytes2Int(bytes);
    }

    @Override
    public String convertToString(Object val) {
        MyAssert.checkArgument(this.matchTypeHandler(val.getClass()), "The type of value " + val + " is not Integer or int.");
        return val.toString();
    }

    @Override
    public String extractTargetTypeStrValue(String value) {
        return toObjectFromStr(value, Integer::parseInt);
    }
}

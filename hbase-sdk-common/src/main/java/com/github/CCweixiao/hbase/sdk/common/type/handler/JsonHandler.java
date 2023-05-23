package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.alibaba.fastjson2.JSON;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseColumnTypeCastException;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;

import java.nio.charset.Charset;


/**
 * @author leojie 2022/11/20 18:50
 */
public class JsonHandler extends AbstractTypeHandler<Object> {
    @Override
    protected boolean matchConverterType(Class<?> type) {
        return true;
    }

    @Override
    protected byte[] convertObjValToByteArr(Class<?> type, Object value) {
        try {
            String jsonVal = JSON.toJSONString(value);
            return jsonVal.getBytes(Charset.defaultCharset());
        } catch (Exception e) {
            throw new HBaseColumnTypeCastException(e);
        }
    }

    @Override
    protected Object convertByteArrToObjVal(Class<?> type, byte[] bytes) {
        String jsonVal = new String(bytes, Charset.defaultCharset());
        return JSON.parseObject(jsonVal, type);
    }

    @Override
    public String toString(Object val) {
        return JSON.toJSONString(val);
    }

    @Override
    public String extractMatchTtypeValue(String value) {
        return toString(value, JSON::toJSONString);
    }
}

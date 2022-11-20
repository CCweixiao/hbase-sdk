package com.github.CCweixiao.hbase.sdk.common.type.handler;

import com.alibaba.fastjson2.JSON;


/**
 * @author leojie 2022/11/20 18:50
 */
public class JsonHandler extends StringHandler {
    @Override
    protected boolean matchTypeHandler(Class<?> type) {
        return true;
    }

    @Override
    protected byte[] convertToBytes(Class<?> type, Object value) {
        String jsonVal = JSON.toJSONString(value);
        return super.convertToBytes(String.class, jsonVal);
    }

    @Override
    protected Object convertToObject(Class<?> type, byte[] bytes) {
        String jsonVal = super.convertToObject(String.class, bytes).toString();
        return JSON.parseObject(jsonVal, type);
    }

    @Override
    public String convertToString(Object val) {
        return JSON.toJSONString(val);
    }
}

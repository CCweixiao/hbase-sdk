package com.github.CCweixiao.hbase.sdk.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>序列化工具类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class JsonUtil {
    public static String toJson(Object data) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(data);
    }

    public static <T> T fromJson(String val, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(val, clazz);
    }

    public static Map<String, Object> fromJsonToMap(String val) {
        Map<String, Object> mapType = new HashMap<>();
        return fromJson(val, mapType.getClass());
    }

}

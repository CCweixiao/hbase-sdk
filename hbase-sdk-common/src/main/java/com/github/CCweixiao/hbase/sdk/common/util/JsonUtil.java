package com.github.CCweixiao.hbase.sdk.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * <p>JSON serialization tool class.</p>
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
}

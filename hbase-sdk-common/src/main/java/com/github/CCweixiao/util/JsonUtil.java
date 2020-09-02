package com.github.CCweixiao.util;

import com.google.gson.Gson;


/**
 * <p>序列化工具类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class JsonUtil {
    public static String toJson(Object data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public static <T> T fromJson(String val, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(val, clazz);
    }

}

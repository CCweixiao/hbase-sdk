package com.leo.hbase.sdk.core.util;

import com.google.gson.Gson;


/**
 * <p>序列化工具类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/9 3:45 下午
 * @since 1.0
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

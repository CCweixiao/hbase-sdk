package com.github.CCweixiao.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * <p>序列化工具类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class JsonUtil {
    private static ObjectMapper objectMapper;

    public static String toJson(Object data) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T fromJson(String val, Class<T> clazz) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.readValue(val, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Object v = fromJson("233", Object.class);

        System.out.println(v);
    }

}

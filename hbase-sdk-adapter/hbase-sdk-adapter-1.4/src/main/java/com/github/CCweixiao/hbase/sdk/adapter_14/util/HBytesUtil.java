package com.github.CCweixiao.hbase.sdk.adapter_14.util;

import com.github.CCweixiao.hbase.sdk.common.util.JsonUtil;
import org.apache.hadoop.hbase.util.Bytes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>HBase bytes 类型转换工具类</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class HBytesUtil {

    public static byte[] toBytes(Object val) {
        if (val == null) {
            return null;
        }
        String valClassName = val.getClass().getName();
        if (valClassName.equals(String.class.getName())) {
            return Bytes.toBytes(val.toString());
        }
        return Bytes.toBytes(JsonUtil.toJson(val));
    }

    public static Object toObject(byte[] val, Class<?> classType) {
        final String className = classType.getName();
        String value = Bytes.toString(val);

        switch (className) {
            case "java.lang.String":
                return value;
            case "boolean":
            case "java.lang.Boolean":
                return Boolean.parseBoolean(value);
            case "long":
            case "java.lang.Long":
                return Long.parseLong(value);
            case "float":
            case "java.lang.Float":
                return Float.parseFloat(value);
            case "double":
            case "java.lang.Double":
                return Double.parseDouble(value);
            case "int":
            case "java.lang.Integer":
                return Integer.parseInt(value);
            case "short":
            case "java.lang.Short":
                return Short.parseShort(value);
            case "java.math.BigDecimal":
                return BigDecimal.valueOf(Long.parseLong(value));
            default:
                return JsonUtil.fromJson(Bytes.toString(val), classType);
        }
    }

    public static <T> List<T> toList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(String.class.getName());
        System.out.println(Boolean.class.getName());
        System.out.println(Long.class.getName());
        System.out.println(Float.class.getName());
        System.out.println(Double.class.getName());
        System.out.println(Integer.class.getName());
        System.out.println(Short.class.getName());
        System.out.println(BigDecimal.class.getName());
        System.out.println(int.class.getName());
    }


}

package com.github.CCweixiao.hbase.sdk.adapter_12.util;

import com.github.CCweixiao.hbase.sdk.common.util.JsonUtil;
import org.apache.hadoop.hbase.util.Bytes;

import java.math.BigDecimal;

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
        switch (valClassName) {
            case "java.lang.String":
                return Bytes.toBytes(val.toString());
            case "boolean":
            case "java.lang.Boolean":
                return Bytes.toBytes(Boolean.parseBoolean(val.toString()));
            case "long":
            case "java.lang.Long":
                return Bytes.toBytes(Long.parseLong(val.toString()));
            case "float":
            case "java.lang.Float":
                return Bytes.toBytes(Float.parseFloat(val.toString()));
            case "double":
            case "java.lang.Double":
                return Bytes.toBytes(Double.parseDouble(val.toString()));
            case "int":
            case "java.lang.Integer":
                return Bytes.toBytes(Integer.parseInt(val.toString()));
            case "short":
            case "java.lang.Short":
                return Bytes.toBytes(Short.parseShort(val.toString()));
            case "java.math.BigDecimal":
                return Bytes.toBytes(BigDecimal.valueOf(Long.parseLong(val.toString())));
            default:
                return Bytes.toBytes(JsonUtil.toJson(val));
        }
    }

    public static Object toObject(byte[] val, Class<?> classType) {
        if (val == null) {
            return null;
        }
        final String className = classType.getName();
        switch (className) {
            case "java.lang.String":
                return Bytes.toString(val);
            case "boolean":
            case "java.lang.Boolean":
                return Bytes.toBoolean(val);
            case "long":
            case "java.lang.Long":
                return Bytes.toLong(val);
            case "float":
            case "java.lang.Float":
                return Bytes.toFloat(val);
            case "double":
            case "java.lang.Double":
                return Bytes.toDouble(val);
            case "int":
            case "java.lang.Integer":
                return Bytes.toInt(val);
            case "short":
            case "java.lang.Short":
                return Bytes.toShort(val);
            case "java.math.BigDecimal":
                return Bytes.toBigDecimal(val);
            default:
                return JsonUtil.fromJson(Bytes.toString(val), classType);
        }
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
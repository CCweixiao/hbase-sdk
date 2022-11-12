package com.github.CCwexiao.hbase.sdk.dsl.util;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2020/11/28 3:45 下午
 */
public class ClassUtil {
    private static final Map<String, Class<?>> nativeClassMap = new HashMap<>();
    private static final Map<String, Class<?>> simpleClassMap = new HashMap<>();
    private static final Map<Class<?>, Class<?>> boxTypeMap = new HashMap<>();

    static {
        nativeClassMap.put("byte", byte.class);
        nativeClassMap.put("short", short.class);
        nativeClassMap.put("int", int.class);
        nativeClassMap.put("long", long.class);
        nativeClassMap.put("char", char.class);
        nativeClassMap.put("float", float.class);
        nativeClassMap.put("double", double.class);
        nativeClassMap.put("boolean", boolean.class);

        simpleClassMap.put("Byte", Byte.class);
        simpleClassMap.put("Short", Short.class);
        simpleClassMap.put("Int", Integer.class);
        simpleClassMap.put("Long", Long.class);
        simpleClassMap.put("Char", Character.class);
        simpleClassMap.put("Float", Float.class);
        simpleClassMap.put("Double", Double.class);
        simpleClassMap.put("Boolean", Boolean.class);

        simpleClassMap.put("string", String.class);
        simpleClassMap.put("String", String.class);
        simpleClassMap.put("date", Date.class);
        simpleClassMap.put("Date", Date.class);

        boxTypeMap.put(byte.class, Byte.class);
        boxTypeMap.put(short.class, Short.class);
        boxTypeMap.put(int.class, Integer.class);
        boxTypeMap.put(long.class, Long.class);
        boxTypeMap.put(char.class, Character.class);
        boxTypeMap.put(float.class, Float.class);
        boxTypeMap.put(double.class, Double.class);
        boxTypeMap.put(boolean.class, Boolean.class);
    }

    /**
     * 根据名称获取类型
     *
     * @param className 类型名称
     * @return 类型
     */
    public static Class<?> forName(String className) {
        Util.checkEmptyString(className);

        if (nativeClassMap.containsKey(className)) {
            return nativeClassMap.get(className);
        }

        if (simpleClassMap.containsKey(className)) {
            return simpleClassMap.get(className);
        }

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new HBaseOperationsException(e);
        }

    }

    /**
     * 判断c1和c2是否有相同的类型
     *
     * @param c1 类型1
     * @param c2 类型2
     * @return 类型是否相同
     */
    public static boolean withSameType(Class<?> c1, Class<?> c2) {
        Util.checkNull(c1);
        Util.checkNull(c2);

        if (c1 == c2) {
            return true;
        }
        return tryConvertToBoxClass(c1) == tryConvertToBoxClass(c2);
    }


    public static Class<?> tryConvertToBoxClass(Class<?> c) {
        Util.checkNull(c);

        if (boxTypeMap.containsKey(c)) {
            return boxTypeMap.get(c);
        }
        return c;
    }

    private ClassUtil() {
    }
}

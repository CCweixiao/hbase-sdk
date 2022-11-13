package com.github.CCweixiao.hbase.sdk.common.util;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2022/11/13 14:27
 */
public class ClassUtil {
    private static final Map<String, Class<?>> NATIVE_CLASS_MAP = new HashMap<>();
    private static final Map<String, Class<?>> STRING_CLASS_MAP = new HashMap<>();
    private static final Map<Class<?>, Class<?>> BOX_TYPE_MAP = new HashMap<>();

    static {
        NATIVE_CLASS_MAP.put("byte", byte.class);
        NATIVE_CLASS_MAP.put("short", short.class);
        NATIVE_CLASS_MAP.put("int", int.class);
        NATIVE_CLASS_MAP.put("long", long.class);
        NATIVE_CLASS_MAP.put("char", char.class);
        NATIVE_CLASS_MAP.put("float", float.class);
        NATIVE_CLASS_MAP.put("double", double.class);
        NATIVE_CLASS_MAP.put("boolean", boolean.class);

        STRING_CLASS_MAP.put("Byte", Byte.class);
        STRING_CLASS_MAP.put("Short", Short.class);
        STRING_CLASS_MAP.put("Int", Integer.class);
        STRING_CLASS_MAP.put("Long", Long.class);
        STRING_CLASS_MAP.put("Char", Character.class);
        STRING_CLASS_MAP.put("Float", Float.class);
        STRING_CLASS_MAP.put("Double", Double.class);
        STRING_CLASS_MAP.put("Boolean", Boolean.class);

        STRING_CLASS_MAP.put("string", String.class);
        STRING_CLASS_MAP.put("String", String.class);
        STRING_CLASS_MAP.put("date", Date.class);
        STRING_CLASS_MAP.put("Date", Date.class);

        BOX_TYPE_MAP.put(byte.class, Byte.class);
        BOX_TYPE_MAP.put(short.class, Short.class);
        BOX_TYPE_MAP.put(int.class, Integer.class);
        BOX_TYPE_MAP.put(long.class, Long.class);
        BOX_TYPE_MAP.put(char.class, Character.class);
        BOX_TYPE_MAP.put(float.class, Float.class);
        BOX_TYPE_MAP.put(double.class, Double.class);
        BOX_TYPE_MAP.put(boolean.class, Boolean.class);
    }

    /**
     * 根据名称获取类型
     *
     * @param className 类型名称
     * @return 类型
     */
    public static Class<?> forName(String className) {
        ObjUtil.checkEmptyString(className);

        if (NATIVE_CLASS_MAP.containsKey(className)) {
            return NATIVE_CLASS_MAP.get(className);
        }

        if (STRING_CLASS_MAP.containsKey(className)) {
            return STRING_CLASS_MAP.get(className);
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
        ObjUtil.checkIsNull(c1);
        ObjUtil.checkIsNull(c2);

        if (c1 == c2) {
            return true;
        }
        return tryConvertToBoxClass(c1) == tryConvertToBoxClass(c2);
    }


    public static Class<?> tryConvertToBoxClass(Class<?> c) {
        ObjUtil.checkIsNull(c);

        if (BOX_TYPE_MAP.containsKey(c)) {
            return BOX_TYPE_MAP.get(c);
        }
        return c;
    }

    private ClassUtil() {
    }
}

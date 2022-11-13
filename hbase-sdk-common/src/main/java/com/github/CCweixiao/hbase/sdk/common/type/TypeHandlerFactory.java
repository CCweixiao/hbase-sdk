package com.github.CCweixiao.hbase.sdk.common.type;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.type.handler.*;
import com.github.CCweixiao.hbase.sdk.common.util.ClassUtil;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2020/11/28 7:39 下午
 */
public final class TypeHandlerFactory {
    private static final EnumHandler ENUM_HANDLER = new EnumHandler();
    private static final Map<Class<?>, AbstractTypeHandler> DEFAULT_HANDLERS = new HashMap<>(10);
    private static volatile Map<String, AbstractTypeHandler> typeHandlerCache;

    private TypeHandlerFactory() {

    }

    static {
        DEFAULT_HANDLERS.put(String.class, new StringHandler());
        DEFAULT_HANDLERS.put(Date.class, new DateHandler());
        DEFAULT_HANDLERS.put(Boolean.class, new BooleanHandler());
        DEFAULT_HANDLERS.put(Character.class, new CharHandler());
        DEFAULT_HANDLERS.put(Byte.class, new ByteHandler());
        DEFAULT_HANDLERS.put(Short.class, new ShortHandler());
        DEFAULT_HANDLERS.put(Integer.class, new IntegerHandler());
        DEFAULT_HANDLERS.put(Long.class, new LongHandler());
        DEFAULT_HANDLERS.put(Float.class, new FloatHandler());
        DEFAULT_HANDLERS.put(Double.class, new DoubleHandler());
    }

    public static AbstractTypeHandler findTypeHandler(Class<?> type) {
        ObjUtil.checkIsNull(type);
        type = ClassUtil.tryConvertToBoxClass(type);
        if (type.isEnum()) {
            return ENUM_HANDLER;
        }
        return DEFAULT_HANDLERS.get(type);
    }

    public static byte[] toBytes(Class<?> type, Object val) {
        AbstractTypeHandler typeHandler = findTypeHandler(type);
        return typeHandler.toBytes(type, val);
    }

    public static Object toObject(Class<?> type, byte[] bytes) {
        AbstractTypeHandler typeHandler = findTypeHandler(type);
        return typeHandler.toObject(type, bytes);
    }

    public static byte[] toBytes(Object val) {
        AbstractTypeHandler typeHandler = findTypeHandler(val.getClass());
        return typeHandler.convertToBytes(val);
    }

    public static ByteBuffer toByteBuffer(Class<?> type, Object val) {
        AbstractTypeHandler typeHandler = findTypeHandler(type);
        return typeHandler.toByteBuffer(type, val);
    }

    public static Object toObject(Class<?> type, ByteBuffer buffer) {
        AbstractTypeHandler typeHandler = findTypeHandler(type);
        return typeHandler.toObject(type, buffer);
    }

    public static ByteBuffer toByteBuffer(Object val) {
        AbstractTypeHandler typeHandler = findTypeHandler(val.getClass());
        return typeHandler.convertToByteBuffer(val);
    }

    public static AbstractTypeHandler findTypeHandler(String type) {
        ObjUtil.checkEmptyString(type);
        if (typeHandlerCache == null || !typeHandlerCache.containsKey(type)) {
            synchronized (TypeHandlerFactory.class) {
                if (typeHandlerCache == null || !typeHandlerCache.containsKey(type)) {
                    if (typeHandlerCache == null) {
                        typeHandlerCache = new HashMap<>(4);
                    }
                    if (!typeHandlerCache.containsKey(type)) {
                        try {
                            final Class<?> c = ClassUtil.forName(type);
                            typeHandlerCache.put(type, findTypeHandler(c));
                        } catch (Exception e) {
                            throw new HBaseOperationsException(e);
                        }
                    }
                }
            }
        }
        return typeHandlerCache.get(type);
    }


    public static byte[] toBytes(String typeClassName, Object val) {
        AbstractTypeHandler typeHandler = findTypeHandler(typeClassName);
        return typeHandler.convertToBytes(val);
    }

    public static ByteBuffer toByteBuffer(String typeClassName, Object val) {
        AbstractTypeHandler typeHandler = findTypeHandler(typeClassName);
        return typeHandler.convertToByteBuffer(val);
    }

    public static void main(String[] args) {
        byte[] bytes = TypeHandlerFactory.findTypeHandler(Integer.class).toBytes(Integer.class, 100);
        Object val = TypeHandlerFactory.findTypeHandler(Integer.class).toObject(Integer.class, bytes);
        System.out.println(val);
    }
}

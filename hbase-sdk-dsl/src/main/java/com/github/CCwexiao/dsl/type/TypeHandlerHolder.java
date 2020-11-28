package com.github.CCwexiao.dsl.type;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCwexiao.dsl.util.ClassUtil;
import com.github.CCwexiao.dsl.util.Util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author leojie 2020/11/28 3:56 下午
 */
public class TypeHandlerHolder {
    private static final ConcurrentMap<String, TypeHandler> typeHandlerCache = new ConcurrentHashMap<>();

    public static TypeHandler findTypeHandler(String type) {
        Util.checkEmptyString(type);

        if (typeHandlerCache.get(type) == null) {
            try {
                final Class<?> c = ClassUtil.forName(type);
                typeHandlerCache.putIfAbsent(type, (TypeHandler) c.newInstance());
            } catch (Exception e) {
                throw new HBaseOperationsException(e);
            }
        }
        return typeHandlerCache.get(type);
    }
}

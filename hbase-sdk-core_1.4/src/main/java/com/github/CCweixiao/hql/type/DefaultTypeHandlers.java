package com.github.CCweixiao.hql.type;

import com.github.CCweixiao.hql.type.handler.EnumHandler;
import com.github.CCweixiao.hql.type.handler.*;
import com.github.CCwexiao.dsl.type.TypeHandler;
import com.github.CCwexiao.dsl.type.TypeHandlers;
import com.github.CCwexiao.dsl.util.ClassUtil;
import com.github.CCwexiao.dsl.util.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2020/11/28 7:39 下午
 */
public class DefaultTypeHandlers implements TypeHandlers {
    private static final EnumHandler enumHandler = new EnumHandler();
    private static final Map<Class<?>, TypeHandler> defaultHandlers = new HashMap<>();

    static {
        defaultHandlers.put(String.class, new StringHandler());
        defaultHandlers.put(Date.class, new DateHandler());

        defaultHandlers.put(Boolean.class, new BooleanHandler());

        defaultHandlers.put(Character.class, new CharacterHandler());

        defaultHandlers.put(Byte.class, new ByteHandler());
        defaultHandlers.put(Short.class, new ShortHandler());
        defaultHandlers.put(Integer.class, new IntegerHandler());
        defaultHandlers.put(Long.class, new LongHandler());
        defaultHandlers.put(Float.class, new FloatHandler());
        defaultHandlers.put(Double.class, new DoubleHandler());
    }

    @Override
    public TypeHandler findDefaultTypeHandler(Class<?> type) {
        Util.checkNull(type);

        type = ClassUtil.tryConvertToBoxClass(type);
        if (type.isEnum()) {
            return enumHandler;
        }
        return defaultHandlers.get(type);
    }
}

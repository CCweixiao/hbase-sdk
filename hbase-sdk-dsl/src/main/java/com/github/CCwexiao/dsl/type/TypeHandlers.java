package com.github.CCwexiao.dsl.type;

/**
 * @author leojie 2020/11/28 3:53 下午
 */
public interface TypeHandlers {

    /**
     * 获取默认的类型处理器
     *
     * @param type 类型
     * @return 类型处理器
     */
    TypeHandler findDefaultTypeHandler(Class<?> type);
}

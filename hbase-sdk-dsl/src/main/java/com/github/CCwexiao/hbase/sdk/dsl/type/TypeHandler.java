package com.github.CCwexiao.hbase.sdk.dsl.type;

/**
 * @author leojie 2020/11/28 2:50 下午
 */
public interface TypeHandler {
    /**
     * 转换java类为字节数据
     *
     * @param type  数据类型
     * @param value 数据值
     * @return 字节数组
     */
    byte[] toBytes(Class<?> type, Object value);

    /**
     * 转换字节数组为Java object类型
     *
     * @param type  数据类型
     * @param bytes 字节数组
     * @return Object 类型
     */
    Object toObject(Class<?> type, byte[] bytes);
}

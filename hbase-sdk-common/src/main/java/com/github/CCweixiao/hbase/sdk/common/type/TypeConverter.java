package com.github.CCweixiao.hbase.sdk.common.type;

/**
 * @author leojie 2022/12/3 14:22
 */
public interface TypeConverter<T> {
    T convertTo(String value) throws Exception;
}

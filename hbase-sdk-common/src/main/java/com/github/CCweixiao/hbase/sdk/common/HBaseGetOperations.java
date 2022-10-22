package com.github.CCweixiao.hbase.sdk.common;

import java.util.List;
import java.util.Map;

/**
 * HBase get查询
 *
 * @author leojie 2020/12/31 11:11 下午
 */
public interface HBaseGetOperations {
    /**
     * 根据rowKey查询数据，查询结果映射为一个Java Bean数据实体.
     *
     * @param rowKey rowKey
     * @param clazz  数据实体类型
     * @param <T>    泛型类型
     * @return 结果数据
     */
    <T> T getByRowKey(String rowKey, Class<T> clazz);

    /**
     * 根据rowKey查询某一个所属列簇的数据，查询结果映射为一个Java Bean数据实体.
     *
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param clazz      数据实体类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> T getByRowKeyWithFamily(String rowKey, String familyName, Class<T> clazz);

    /**
     * 根据rowKey查询某一个所属列簇以及某些字段的数据，查询结果映射为一个Java Bean.
     *
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param columns    需要筛选的字段名
     * @param clazz      数据实体类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> T getByRowKeyWithFamilyAndColumns(String rowKey, String familyName, List<String> columns, Class<T> clazz);

    /**
     * 根据RowKey查询数据，返回Map结构的数据
     * 返回的数据格式为：{"INFO:NAME": "leo", "INFO:AGE": "18"}
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return 结果数据
     */
    Map<String, String> getByRowKey(String tableName, String rowKey);

    /**
     * 根据RowKey和列簇查询数据，返回Map结构的数据
     * 返回的数据格式为：{"INFO:NAME": "leo", "INFO:AGE": "18"}
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @return 结果数据
     */
    Map<String, String> getByRowKeyWithFamily(String tableName, String rowKey, String familyName);

    /**
     * 根据RowKey和列簇以及字段名查询数据，返回Map结构的数据
     * 返回的数据格式为：{"INFO:NAME": "leo", "INFO:AGE": "18"}
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param columns    需要筛选的字段名
     * @return 结果数据
     */
    Map<String, String> getByRowKeyWithFamilyAndColumns(String tableName, String rowKey, String familyName, List<String> columns);

    /**
     * get查询数据，返回Map数据类型，例如：{"rowKey" : "10001", "familyName": "INFO:NAME", "timestamp": 1232234234242, "value": "leo"}
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return 获取查询结果
     */
    Map<String, Object> getByRowKeyToMap(String tableName, String rowKey);


    /**
     * get查询数据，返回Map数据类型，例如：{"rowKey" : "10001", "familyName": "INFO:NAME", "timestamp": 1232234234242, "value": "leo"}
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @return 获取查询结果
     */
    Map<String, Object> getByRowKeyToMapWithFamily(String tableName, String rowKey, String familyName);

    /**
     * get查询数据，返回Map数据类型，例如：{"rowKey" : "10001", "familyName": "INFO:NAME", "timestamp": 1232234234242, "value": "leo"}
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param columns    需要筛选的字段名
     * @return 获取查询结果
     */
    Map<String, Object> getByRowKeyToMapWithFamilyAndColumns(String tableName, String rowKey, String familyName, List<String> columns);
}

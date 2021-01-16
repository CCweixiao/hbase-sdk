package com.github.CCweixiao;

import java.util.List;
import java.util.Map;

/**
 * 定义HBase scan操作的API
 *
 * @author leojie 2020/12/31 11:53 下午
 */
public interface HBaseScanOperations {
    /**
     * scan 所有数据，查询结果集映射为JavaBean
     *
     * @param limit 查询结果集返回数量限制
     * @param clazz 结果集映射的JavaBean类型
     * @param <T>   泛型类型
     * @return 结果数据
     */
    <T> List<T> findAll(int limit, Class<T> clazz);

    /**
     * scan 所有数据，可以指定列簇，查询结果集映射为JavaBean
     *
     * @param familyName 列簇名
     * @param limit      查询结果集返回数量限制
     * @param clazz      结果集映射的JavaBean类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllWithFamily(String familyName, int limit, Class<T> clazz);

    /**
     * scan 所有数据，可以指定列簇和多个字段名，查询结果集映射为JavaBean
     *
     * @param familyName 列簇名
     * @param columns    字段名列表
     * @param limit      查询结果集返回数量限制
     * @param clazz      结果集映射的JavaBean类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllWithFamilyAndColumns(String familyName, List<String> columns, int limit, Class<T> clazz);

    /**
     * 根据前缀scan数据，查询结果集映射为JavaBean
     *
     * @param prefix scan的前缀
     * @param limit  查询结果集返回数量限制
     * @param clazz  结果集映射的JavaBean类型
     * @param <T>    泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllByPrefix(String prefix, int limit, Class<T> clazz);

    /**
     * 根据前缀scan数据，可以指定列簇，查询结果集映射为JavaBean
     *
     * @param prefix     scan的前缀
     * @param familyName 列簇
     * @param limit      查询结果集返回数量限制
     * @param clazz      结果集映射的JavaBean类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllByPrefixWithFamily(String prefix, String familyName, int limit, Class<T> clazz);

    /**
     * 根据前缀scan数据，可以指定列簇和多个字段名，查询结果集映射为JavaBean
     *
     * @param prefix     scan的前缀
     * @param familyName 列簇
     * @param columns    多个字段名
     * @param limit      查询结果集返回数量限制
     * @param clazz      结果集映射的JavaBean类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllByPrefixWithFamilyAndColumns(String prefix, String familyName, List<String> columns, int limit, Class<T> clazz);



    /**
     * scan查询数据，返回Map列表类型，例如：[{"rowName": "10001", "info:name": "leo", "info:age": 18}]
     *
     * @param tableName 表名
     * @param limit     限制的返回行数
     * @return 查询结果
     */
    List<Map<String, Map<String, String>>> findAllToMap(String tableName, int limit);

    /**
     * scan查询数据，可以指定列簇名称，返回Map列表类型，例如：[{"rowName": "10001", "info:name": "leo", "info:age": 18}]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<Map<String, Map<String, String>>> findAllToMapWithFamily(String tableName, String familyName, int limit);

    /**
     * scan查询数据，可以指定列簇名称以及需要筛选的字段列表，返回Map列表类型，例如：[{"rowName": "10001", "info:name": "leo", "info:age": 18}]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param columns    需要筛选的字段名列表
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<Map<String, Map<String, String>>> findAllToMapWithFamilyAndColumns(String tableName, String familyName, List<String> columns, int limit);



    /**
     * scan查询数据，返回Map列表类型，例如：[[{"rowName": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName 表名
     * @param limit     限制的返回行数
     * @return 查询结果
     */
    List<Map<String, Object>> scanToListMap(String tableName, Integer limit);


    /**
     * scan查询数据，可以指定列簇名称，返回Map列表类型，例如：[[{"rowKey": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<Map<String, Object>> scanToListMapWithFamily(String tableName, String familyName, Integer limit);

    /**
     * scan查询数据，可以指定列簇名称以及需要筛选的字段列表，返回Map列表类型
     * 例如：[[{"rowKey": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param columns    需要筛选的字段名列表
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<Map<String, Object>> scanToListMapWithFamilyAndColumns(String tableName, String familyName, List<String> columns, Integer limit);

    /**
     * scan查询数据，可以指定列簇名称以及开始的RowKey，返回Map列表类型
     * 例如：[[{"rowKey": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param startKey   起始RowKey
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<List<Map<String, Object>>> scanToListMap(String tableName, String familyName, String startKey, Integer limit);

    /**
     * scan查询数据，可以指定列簇名称和需要筛选的字段名以及开始的RowKey，返回Map列表类型
     * 例如：[[{"rowKey": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param columns    需要筛选的字段名列表
     * @param startKey   起始RowKey
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<List<Map<String, Object>>> scanToListMap(String tableName, String familyName, List<String> columns, String startKey, Integer limit);


    /**
     * scan查询数据，可以指定列簇名称和需要筛选的字段名以及开始的RowKey和结束的RowKey，返回Map列表类型
     * 例如：[[{"rowKey": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param columns    需要筛选的字段名列表
     * @param startKey   起始RowKey
     * @param endKey     结束RowKey
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<List<Map<String, Object>>> scanToListMap(String tableName, String familyName, List<String> columns, String startKey, String endKey, Integer limit);

}

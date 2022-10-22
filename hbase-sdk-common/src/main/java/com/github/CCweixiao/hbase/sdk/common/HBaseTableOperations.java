package com.github.CCweixiao.hbase.sdk.common;

import java.util.List;
import java.util.Map;

/**
 * 定义HBase的数据操作接口
 *
 * @author leojie 2020/9/26 11:04 上午
 */
public interface HBaseTableOperations {

    /**
     * 保存数据，构造Map类型的数据参数，例如： {"INFO:NAME": "leo", "INFO:AGE": 18}
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @param data      需要保存的数据. 例如： {"INFO:NAME":"leo"}
     */
    void save(String tableName, String rowKey, Map<String, Object> data);

    /**
     * 批量保存数据，构造Map类型结构的列表数据参数，例如：
     * {"row1": {"INFO:NAME": "leo1", "INFO:AGE": 18}, "row2": {"INFO:NAME": "leo2", "INFO:AGE": 17}}
     *
     * @param tableName 表名
     * @param data      需要保存的数据. 例如： {"row1": {"INFO:NAME": "leo1"}, "row2": {"INFO:NAME": "leo2"}}
     */
    void saveBatch(String tableName, Map<String, Map<String, Object>> data);

    /**
     * 保存数据，构造一个Java数据实体映射
     *
     * @param t   Java数据实体对象.
     * @param <T> 泛型类型.
     * @return 保存成功的数据对象.
     * @throws Exception 抛出异常
     */
    <T> T save(T t) throws Exception;

    /**
     * 批量保存数据，构造一个Java数据实体映射列表
     *
     * @param lst Java数据实体对象列表.
     * @param <T> 泛型数据类型.
     * @return 保存成功的数据列表中的第一条数据对象.
     * @throws Exception 抛出异常
     */
    <T> T saveBatch(List<T> lst) throws Exception;

    /**
     * 根据rowKey查询数据，查询结果映射为一个Java Bean数据实体.
     *
     * @param rowKey rowKey
     * @param clazz  Java Bean
     * @param <T>    泛型类型
     * @return 结果数据
     */
    <T> T getByRowKey(String rowKey, Class<T> clazz);

    /**
     * 根据rowKey查询某一个所属列簇的数据，查询结果映射为一个Java Bean数据实体.
     *
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param clazz      Java Bean
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> T getByRowKeyWithFamily(String rowKey, String familyName, Class<T> clazz);

    /**
     * 根据rowKey查询某一个所属列簇以及某些字段的数据，查询结果映射为一个Java Bean.
     *
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名列表
     * @param clazz      Java Bean
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> T getByRowKeyWithFamilyAndQualifiers(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz);

    /**
     * 根据RowKey查询数据，返回Map结构的数据
     * 返回的数据格式为：{"INFO:NAME": "leo", "INFO:AGE": 18}
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return 结果数据
     */
    Map<String, String> getByRowKey(String tableName, String rowKey);

    /**
     * 根据RowKey和列簇查询数据，返回Map结构的数据
     * 返回的数据格式为：{"INFO:NAME": "leo", "INFO:AGE": 18}
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @return 结果数据
     */
    Map<String, String> getByRowKeyWithFamily(String tableName, String rowKey, String familyName);

    /**
     * 根据RowKey和列簇以及字段名查询数据，返回Map结构的数据
     * 返回的数据格式为：{"INFO:NAME": "leo", "INFO:AGE": 18}
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 字段名列表
     * @return 结果数据
     */
    Map<String, String> getByRowKeyWithFamilyAndQualifiers(String tableName, String rowKey, String familyName, List<String> qualifiers);

    /**
     * get查询数据，返回Map数据类型，例如：{"rowKey" : "10001", "familyName": "INFO:NAME", "timestamp": 1232234234242, "value": "leo"}
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return 获取查询结果
     */
    Map<String, Object> getToMap(String tableName, String rowKey);


    /**
     * get查询数据，返回Map数据类型，例如：{"rowKey" : "10001", "familyName": "INFO:NAME", "timestamp": 1232234234242, "value": "leo"}
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @return 获取查询结果
     */
    Map<String, Object> getToMapWithFamily(String tableName, String rowKey, String familyName);

    /**
     * get查询数据，返回Map数据类型，例如：{"rowKey" : "10001", "familyName": "INFO:NAME", "timestamp": 1232234234242, "value": "leo"}
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名
     * @return 获取查询结果
     */
    Map<String, Object> getToMapWithFamilyAndQualifier(String tableName, String rowKey, String familyName, List<String> qualifiers);


    /**
     * get查询数据，返回Map列表结构，例如：[{"rowKey" : "10001", "familyName": "INFO:NAME", "timestamp": 1232234234242, "value": "leo"}]
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return 查询结果
     */
    List<Map<String, Object>> getToListMap(String tableName, String rowKey);

    /**
     * get查询数据，可以指定列簇，返回Map列表结构，例如：[{"rowKey" : "10001", "familyName": "INFO:NAME", "timestamp": 1232234234242, "value": "leo"}]
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @return 查询结果
     */
    List<Map<String, Object>> getToListMapWithFamily(String tableName, String rowKey, String familyName);

    /**
     * get查询数据，可以指定列簇以及需要筛选的字段名，返回Map列表结构，例如：[{"rowKey" : "10001", "familyName": "INFO:NAME", "timestamp": 1232234234242, "value": "leo"}]
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名
     * @return 查询结果
     */
    List<Map<String, Object>> getToListMapWithFamilyAndQualifier(String tableName, String rowKey, String familyName, List<String> qualifiers);

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
     * @param qualifiers 字段名列表
     * @param limit      查询结果集返回数量限制
     * @param clazz      结果集映射的JavaBean类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllWithFamilyAndQualifiers(String familyName, List<String> qualifiers, int limit, Class<T> clazz);

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
     * @param qualifiers 多个字段名
     * @param limit      查询结果集返回数量限制
     * @param clazz      结果集映射的JavaBean类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllByPrefixWithFamilyAndQualifiers(String prefix, String familyName, List<String> qualifiers, int limit, Class<T> clazz);

    /**
     * 根据start row 和 end row扫描数据，
     *
     * @param startRow 开始row
     * @param endRow   结束row
     * @param limit    查询结果集返回数量限制
     * @param clazz    结果集映射的JavaBean类型
     * @param <T>      泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllByStartAndEndRow(String startRow, String endRow, int limit, Class<T> clazz);


    /**
     * 根据start row 和 end row扫描数据，
     *
     * @param startRow   开始row
     * @param endRow     结束row
     * @param familyName 列簇
     * @param limit      查询结果集返回数量限制
     * @param clazz      结果集映射的JavaBean类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllByStartAndEndRowWithFamily(String startRow, String endRow, String familyName, int limit, Class<T> clazz);


    /**
     * 根据start row 和 end row扫描数据，
     *
     * @param startRow   开始row
     * @param endRow     结束row
     * @param familyName 列簇
     * @param qualifiers 多个字段名
     * @param limit      查询结果集返回数量限制
     * @param clazz      结果集映射的JavaBean类型
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> List<T> findAllByStartAndEndRowWithFamilyAndQualifiers(String startRow, String endRow, String familyName, List<String> qualifiers, int limit, Class<T> clazz);

    /**
     * scan查询数据，返回Map列表类型，例如：[{"rowName": "10001", "info:name": "leo", "info:age": 18}]
     *
     * @param tableName 表名
     * @param limit     限制的返回行数
     * @return 查询结果
     */
    List<Map<String, String>> findToMapList(String tableName, int limit);

    /**
     * scan查询数据，可以指定列簇名称，返回Map列表类型，例如：[{"rowName": "10001", "info:name": "leo", "info:age": 18}]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<Map<String, String>> findToMapListWithFamily(String tableName, String familyName, int limit);

    /**
     * scan查询数据，可以指定列簇名称以及需要筛选的字段列表，返回Map列表类型，例如：[{"rowName": "10001", "info:name": "leo", "info:age": 18}]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名列表
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<Map<String, String>> findToMapListWithFamilyAndQualifier(String tableName, String familyName, List<String> qualifiers, int limit);


    /**
     * scan查询数据，返回Map列表类型，例如：[[{"rowName": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName 表名
     * @param limit     限制的返回行数
     * @return 查询结果
     */
    List<List<Map<String, Object>>> findToListMap(String tableName, Integer limit);


    /**
     * scan查询数据，可以指定列簇名称，返回Map列表类型，例如：[[{"rowKey": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<List<Map<String, Object>>> findToListMapWithFamily(String tableName, String familyName, Integer limit);

    /**
     * scan查询数据，可以指定列簇名称以及需要筛选的字段列表，返回Map列表类型
     * 例如：[[{"rowKey": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名列表
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<List<Map<String, Object>>> findToListMapWithFamilyAndQualifier(String tableName, String familyName, List<String> qualifiers, Integer limit);

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
    List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, String startKey, Integer limit);

    /**
     * scan查询数据，可以指定列簇名称和需要筛选的字段名以及开始的RowKey，返回Map列表类型
     * 例如：[[{"rowKey": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名列表
     * @param startKey   起始RowKey
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, String startKey, Integer limit);


    /**
     * scan查询数据，可以指定列簇名称和需要筛选的字段名以及开始的RowKey和结束的RowKey，返回Map列表类型
     * 例如：[[{"rowKey": "10001", "info:name": "leo", "timestamp": 10000, "value": "leo"}]]
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名列表
     * @param startKey   起始RowKey
     * @param endKey     结束RowKey
     * @param limit      限制的返回行数
     * @return 查询结果
     */
    List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, String startKey, String endKey, Integer limit);

    /**
     * 根据RowKey删除数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     */
    void delete(String tableName, String rowKey);

    /**
     * 根据RowKey删除某一列簇下的数据
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     */
    void delete(String tableName, String rowKey, String familyName);

    /**
     * 根据RowKey删除某一列簇下的数据，同时可以指定字段名
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 多个字段名
     */
    void delete(String tableName, String rowKey, String familyName, List<String> qualifiers);

    /**
     * 根据RowKey删除某一列簇下的数据，同时可以指定字段名
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 多个字段名
     */
    void delete(String tableName, String rowKey, String familyName, String... qualifiers);


    /**
     * 根据RowKey 批量删除数据
     *
     * @param tableName 表名
     * @param rowKeys   rowKey列表
     */
    void deleteBatch(String tableName, List<String> rowKeys);

    /**
     * 根据RowKey批量删除某一列簇下的数据
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名
     */
    void deleteBatch(String tableName, List<String> rowKeys, String familyName);

    /**
     * 根据RowKey批量删除某一列簇以及指定字段下的数据
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名
     * @param qualifiers 多个字段名
     */
    void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers);

    /**
     * 根据RowKey批量删除某一列簇以及指定字段下的数据
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名
     * @param qualifiers 多个字段名
     */
    void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers);


}

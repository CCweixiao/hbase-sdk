package com.github.CCweixiao;

import java.util.List;
import java.util.Map;

/**
 * 定义HBase的数据操作接口
 *
 * @author leojie 2020/9/26 11:04 上午
 */
public interface HBaseTableOperations {
    /**
     * 保存Map类型结构的数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @param data      需要保存的数据. 例如： {"INFO:NAME":"leo"}
     */
    void save(String tableName, String rowKey, Map<String, Object> data);

    /**
     * 批量保存Map类型结构的数据
     *
     * @param tableName 表名
     * @param data      需要保存的数据. 例如： {"row1": {"INFO:NAME": "leo1"}, "row2": {"INFO:NAME": "leo2"}}
     */
    void saveBatch(String tableName, Map<String, Map<String, Object>> data);

    /**
     * 保存被java bean定义的数据.
     *
     * @param t   泛型数据
     * @param <T> 泛型类型
     * @return 保存成功的对象数据
     * @throws Exception 抛出异常
     */
    <T> T save(T t) throws Exception;

    /**
     * 批量保存被java bean定义的数据.
     *
     * @param lst 泛型数据集合.
     * @param <T> 泛型数据类型.
     * @return 保存成功的数据类型
     * @throws Exception 抛出异常
     */
    <T> T saveBatch(List<T> lst) throws Exception;

    /**
     * 根据rowKey查询数据，查询结果映射为一个Java Bean.
     *
     * @param rowKey rowKey
     * @param clazz  Java Bean
     * @param <T>    泛型类型
     * @return 结果数据
     */
    <T> T getByRowKey(String rowKey, Class<T> clazz);

    /**
     * 根据rowKey查询某一个所属列簇的数据，查询结果映射为一个Java Bean.
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
     * @param qualifiers 字段名列表
     * @param clazz      Java Bean
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> T getByRowKeyWithFamilyAndQualifiers(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz);

    /**
     * 根据RowKey查询数据，返回Map结构的数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @return 结果数据
     */
    Map<String, Object> getByRowKey(String tableName, String rowKey);

    /**
     * 根据RowKey和列簇查询数据，返回Map结构的数据
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @return 结果数据
     */
    Map<String, Object> getByRowKeyWithFamily(String tableName, String rowKey, String familyName);

    /**
     * 根据RowKey和列簇以及字段名查询数据，返回Map结构的数据
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 字段名列表
     * @return 结果数据
     */
    Map<String, Object> getByRowKeyWithFamilyAndQualifiers(String tableName, String rowKey, String familyName, List<String> qualifiers);


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
    <T> List<T> findByFamily(String familyName, int limit, Class<T> clazz);

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
    <T> List<T> findByFamilyAndQualifiers(String familyName, List<String> qualifiers, int limit, Class<T> clazz);

    /**
     * 根据前缀scan数据，查询结果集映射为JavaBean
     *
     * @param prefix scan的前缀
     * @param limit  查询结果集返回数量限制
     * @param clazz  结果集映射的JavaBean类型
     * @param <T>    泛型类型
     * @return 结果数据
     */
    <T> List<T> findByPrefix(String prefix, int limit, Class<T> clazz);

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
    <T> List<T> findByPrefix(String prefix, String familyName, int limit, Class<T> clazz);

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
    <T> List<T> findByPrefix(String prefix, String familyName, List<String> qualifiers, int limit, Class<T> clazz);


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

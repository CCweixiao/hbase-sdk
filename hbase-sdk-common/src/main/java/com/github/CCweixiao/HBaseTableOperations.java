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
     * save Map data to HBase
     *
     * @param tableName the name of table.
     * @param rowKey    row key
     * @param data      the data of saving. example. {"INFO:NAME":"leo"}
     */
    void save(String tableName, String rowKey, Map<String, Object> data);

    /**
     * save batch data to HBase,
     *
     * @param tableName 表名
     * @param data      the data of saving. example. {"row1": {"INFO:NAME": "leo"}, "row2": {"INFO:NAME": "leo2"}}
     */
    void saveBatch(String tableName, Map<String, Map<String, Object>> data);

    /**
     * save a pojo java bean to HBase.
     *
     * @param t   java bean
     * @param <T> java bean type
     * @return mapping java bean
     * @throws Exception exception
     */
    <T> T save(T t) throws Exception;

    /**
     * save batch pojo java bean to HBase.
     *
     * @param lst java bean list.
     * @param <T> java bean type.
     * @return mapping java bean.
     * @throws Exception exception
     */
    <T> T saveBatch(List<T> lst) throws Exception;

    /**
     * get by row key.
     *
     * @param rowName row key
     * @param clazz   mapping java bean class
     * @param <T>     class type
     * @return mapping java bean
     */
    <T> T getByRowKey(String rowName, Class<T> clazz);

    /**
     * get by row key family.
     *
     * @param rowName    row key
     * @param familyName family name
     * @param clazz      mapping java bean class
     * @param <T>        class type
     * @return mapping java bean
     */
    <T> T getByRowKeyWithFamily(String rowName, String familyName, Class<T> clazz);

    /**
     * get by row key family qualifier.
     *
     * @param rowName    row key
     * @param familyName family name
     * @param qualifiers qualifier list
     * @param clazz      mapping java bean class
     * @param <T>        class type
     * @return mapping java bean
     */
    <T> T getByRowKeyWithFamilyAndQualifiers(String rowName, String familyName, List<String> qualifiers, Class<T> clazz);

    /**
     * get by row key, return Map
     *
     * @param tableName table name
     * @param rowName   rowKey Name
     * @return map result
     */
    Map<String, Object> getByRowKey(String tableName, String rowName);

    /**
     * get by row key with family, return Map
     *
     * @param tableName  table name
     * @param rowName    rowKey Name
     * @param familyName family name
     * @return map result
     */
    Map<String, Object> getByRowKeyWithFamily(String tableName, String rowName, String familyName);

    /**
     * get by row key with family name and qualifier names, return
     *
     * @param tableName  table name
     * @param rowName    rowKey Name
     * @param familyName family name
     * @param qualifiers qualifier
     * @return map result
     */
    Map<String, Object> getByRowKeyWithFamilyAndQualifiers(String tableName, String rowName, String familyName, List<String> qualifiers);


    /**
     * scan all data
     *
     * @param limit limit
     * @param clazz mapping class obj
     * @param <T>   mapping class type
     * @return result
     */
    <T> List<T> findAll(int limit, Class<T> clazz);

    /**
     * scan HBase by family
     *
     * @param family familyName
     * @param limit  limit
     * @param clazz  mapping class obj
     * @param <T>    mapping class type
     * @return result
     */
    <T> List<T> findByFamily(String family, int limit, Class<T> clazz);

    /**
     * scan HBase by family and qualifiers
     *
     * @param family     familyName
     * @param qualifiers qualifiers
     * @param limit      limit
     * @param clazz      mapping class obj
     * @param <T>        mapping class type
     * @return result
     */
    <T> List<T> findByFamilyAndQualifiers(String family, List<String> qualifiers, int limit, Class<T> clazz);

    /**
     * findByPrefix
     *
     * @param prefix prefix of scan
     * @param limit  limit
     * @param clazz  mapping class type
     * @param <T>    class type
     * @return scan result
     */
    <T> List<T> findByPrefix(String prefix, int limit, Class<T> clazz);

    /**
     * findByPrefix with family and qualifiers
     *
     * @param prefix prefix of scan
     * @param family family name
     * @param limit  limit
     * @param clazz  mapping class type
     * @param <T>    class type
     * @return scan result
     */
    <T> List<T> findByPrefix(String prefix, String family, int limit, Class<T> clazz);

    /**
     * findByPrefix with family and qualifiers
     *
     * @param prefix     prefix of scan
     * @param family     family name
     * @param qualifiers qualifier list
     * @param limit      limit
     * @param clazz      mapping class type
     * @param <T>        class type
     * @return scan result
     */
    <T> List<T> findByPrefix(String prefix, String family, List<String> qualifiers, int limit, Class<T> clazz);


    /**
     * delete data
     *
     * @param tableName table name
     * @param rowKey    rowKey
     */
    void delete(String tableName, String rowKey);

    /**
     * delete data of family
     *
     * @param tableName table name
     * @param rowKey    row key
     * @param family    family
     */
    void delete(String tableName, String rowKey, String family);

    /**
     * delete data of family and column
     *
     * @param tableName  table name
     * @param rowKey     row key
     * @param family     family
     * @param qualifiers columns.
     */
    void delete(String tableName, String rowKey, String family, List<String> qualifiers);

    /**
     * delete data of family and column
     *
     * @param tableName  table name
     * @param rowKey     row key
     * @param family     family
     * @param qualifiers columns.
     */
    void delete(String tableName, String rowKey, String family, String... qualifiers);


    /**
     * delete batch data
     *
     * @param tableName table name
     * @param rowKeys   rowKey
     */
    void deleteBatch(String tableName, List<String> rowKeys);

    /**
     * delete batch data of family
     *
     * @param tableName table name
     * @param rowKeys   row key
     * @param family    family
     */
    void deleteBatch(String tableName, List<String> rowKeys, String family);

    /**
     * delete batch data of family and column
     *
     * @param tableName  table name
     * @param rowKeys    row key
     * @param family     family
     * @param qualifiers columns.
     */
    void deleteBatch(String tableName, List<String> rowKeys, String family, List<String> qualifiers);

    /**
     * delete batch data of family and column
     *
     * @param tableName  table name
     * @param rowKeys    row key
     * @param family     family
     * @param qualifiers columns.
     */
    void deleteBatch(String tableName, List<String> rowKeys, String family, String... qualifiers);


}

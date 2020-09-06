package com.github.CCweixiao;

import org.apache.hadoop.hbase.client.Mutation;
import org.apache.hadoop.hbase.client.Scan;

import java.util.List;
import java.util.Map;

/**
 * the interface is used to defining some operations of HBase table.
 *
 * @author leo.jie (weixiao.me@aliyun.com)
 */
public interface HBaseTableOperations {

    /**
     * save batch
     *
     * @param tableName the name of table.
     * @param mutations list of mutation,example: put list
     */
    void saveBatch(String tableName, List<Mutation> mutations);

    /**
     * save data to HBase by mutation
     *
     * @param tableName the name of table.
     * @param mutation  mutation,example: put
     */
    void save(String tableName, Mutation mutation);

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
     * get by rowKey family qualifier.
     *
     * @param tableName the name of table.
     * @param rowName   rowKey
     * @param rowMapper your rowMapper
     * @param <T>       mapping class type
     * @return get result
     */
    <T> T get(String tableName, String rowName, RowMapper<T> rowMapper);

    /**
     * get by rowKey family qualifier.
     *
     * @param tableName  the name of table.
     * @param rowName    rowKey
     * @param familyName familyName
     * @param rowMapper  your rowMapper
     * @param <T>        mapping class type
     * @return get result
     */
    <T> T get(String tableName, String rowName, String familyName, RowMapper<T> rowMapper);

    /**
     * get by rowKey family qualifier.
     *
     * @param tableName  the name of table.
     * @param rowName    rowKey
     * @param familyName familyName
     * @param qualifiers list of qualifier name
     * @param rowMapper  your rowMapper
     * @param <T>        mapping class type
     * @return get result
     */
    <T> T get(String tableName, String rowName, String familyName, List<String> qualifiers, RowMapper<T> rowMapper);

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
     * scan HBase
     *
     * @param tableName the name of table.
     * @param scan      scan condition
     * @param limit     result limit
     * @param clazz     mapping class obj
     * @param <T>       mapping class type
     * @return result
     */
    <T> List<T> find(String tableName, Scan scan, int limit, Class<T> clazz);

    /**
     * scan all
     *
     * @param tableName tableName
     * @param limit     limit
     * @param rowMapper rowMapper
     * @param <T>       mapping class type
     * @return result
     */
    <T> List<T> findAll(String tableName, int limit, RowMapper<T> rowMapper);

    /**
     * scan by family
     *
     * @param tableName tableName
     * @param family    family
     * @param limit     limit
     * @param rowMapper rowMapper
     * @param <T>       mapping class type
     * @return result
     */
    <T> List<T> findByFamily(String tableName, String family, int limit, RowMapper<T> rowMapper);

    /**
     * scan by family and qualifier
     *
     * @param tableName  tableName
     * @param family     family
     * @param qualifiers qualifiers
     * @param limit      limit
     * @param rowMapper  rowMapper
     * @param <T>        mapping class type
     * @return result
     */
    <T> List<T> findByFamilyAndQualifiers(String tableName, String family, List<String> qualifiers, int limit, RowMapper<T> rowMapper);

    /**
     * scan HBase
     *
     * @param tableName the name of table.
     * @param scan      scan condition
     * @param limit     result limit
     * @param rowMapper your row mapper
     * @param <T>       mapping class type
     * @return result
     */
    <T> List<T> find(String tableName, Scan scan, int limit, RowMapper<T> rowMapper);


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
     * scan table by prefix.
     *
     * @param tableName table name
     * @param prefix    prefix
     * @param limit     limit
     * @param rowMapper your row mapper
     * @param <T>       mapping class type.
     * @return class type
     */
    <T> List<T> findByPrefix(String tableName, String prefix, int limit, RowMapper<T> rowMapper);

    /**
     * scan table by prefix with family.
     *
     * @param tableName table name
     * @param prefix    prefix
     * @param family    family name
     * @param limit     limit
     * @param rowMapper your row mapper
     * @param <T>       mapping class type.
     * @return class type
     */
    <T> List<T> findByPrefix(String tableName, String prefix, String family, int limit, RowMapper<T> rowMapper);

    /**
     * scan table by prefix with family and qualifier.
     *
     * @param tableName  table name
     * @param prefix     prefix
     * @param family     family name
     * @param qualifiers qualifiers
     * @param limit      limit
     * @param rowMapper  your row mapper
     * @param <T>        mapping class type.
     * @return class type
     */
    <T> List<T> findByPrefix(String tableName, String prefix, String family, List<String> qualifiers, int limit, RowMapper<T> rowMapper);

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
     * @param rowKeys    rowKey
     */
    void deleteBatch(String tableName, List<String> rowKeys);

    /**
     * delete batch data of family
     *
     * @param tableName table name
     * @param rowKeys    row key
     * @param family    family
     */
    void deleteBatch(String tableName, List<String> rowKeys, String family);

    /**
     * delete batch data of family and column
     *
     * @param tableName  table name
     * @param rowKeys     row key
     * @param family     family
     * @param qualifiers columns.
     */
    void deleteBatch(String tableName, List<String> rowKeys, String family, List<String> qualifiers);

    /**
     * delete batch data of family and column
     *
     * @param tableName  table name
     * @param rowKeys     row key
     * @param family     family
     * @param qualifiers columns.
     */
    void deleteBatch(String tableName, List<String> rowKeys, String family, String... qualifiers);

}

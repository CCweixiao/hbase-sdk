package com.github.CCweixiao.hbase.sdk.common;

import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * 定义HBase的数据操作接口
 *
 * @author leojie 2020/9/26 11:04 上午
 */
public interface IHBaseTableOperations {

    /**
     * 保存数据，构造Map类型的数据参数，例如： {"INFO:NAME": "leo", "INFO:AGE": 18}
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @param data      需要保存的数据. 如样例格式数据
     */
    void save(String tableName, String rowKey, Map<String, Object> data);

    /**
     * 保存数据，构造一个Java数据实体映射
     *
     * @param t   Java数据实体对象.
     * @param <T> 泛型类型.
     * @return 保存成功的数据对象.
     */
    <T> T save(T t);

    /**
     * 批量保存数据，构造Map类型结构的列表数据参数，例如：
     * {"row_key1": {"INFO:NAME": "leojie1", "INFO:AGE": 18}, "row_key2": {"INFO:NAME": "leojie2", "INFO:AGE": 17}}
     *
     * @param tableName 表名
     * @param data      需要保存的数据. 如样例格式数据
     * @return 成功保存数据的条数
     */
    int saveBatch(String tableName, Map<String, Map<String, Object>> data);

    /**
     * 批量保存数据，构造一个Java数据实体映射列表
     *
     * @param list Java数据实体对象列表.
     * @param <T>  泛型数据类型.
     * @return 成功保存数据的条数
     */
    <T> int saveBatch(List<T> list);

    /**
     * 根据rowKey查询数据，查询结果映射为一个Java Bean数据实体.
     *
     * @param rowKey rowKey
     * @param clazz  java bean class type
     * @param <T>    泛型类型
     * @return 结果数据
     */
    <T> Optional<T> getRow(String rowKey, Class<T> clazz);

    /**
     * 根据rowKey查询某一个所属列簇的数据，查询结果映射为一个Java Bean数据实体.
     *
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param clazz      java bean class type
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> Optional<T> getRow(String rowKey, String familyName, Class<T> clazz);

    /**
     * 根据rowKey查询某一个所属列簇以及某些字段的数据，查询结果映射为一个Java Bean.
     *
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名列表
     * @param clazz      java bean class type
     * @param <T>        泛型类型
     * @return 结果数据
     */
    <T> Optional<T> getRow(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz);

    /**
     * get查询数据，可以指定自定义的Row Mapper
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @param rowMapper 自定义的RowMapper
     * @param <T>       泛型类型
     * @return get查询结果
     */
    <T> Optional<T> getRow(String tableName, String rowKey, RowMapper<T> rowMapper);

    /**
     * get查询某一列簇下的数据，可以指定自定义的Row Mapper
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return get查询结果
     */
    <T> Optional<T> getRow(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper);

    /**
     * get查询某一列簇下的数据，可以指定多个字段，可以指定自定义的Row Mapper
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return get查询结果
     */
    <T> Optional<T> getRow(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper);

    /**
     * get查询数据，返回Map数据类型，例如：{"f1:name": "leo", "f1:name:timestamp": "1667568422619", "f1:age": "18"}
     *
     * @param tableName     表名
     * @param rowKey        rowKey
     * @param withTimestamp 是否返回时间戳，返回时间戳格式是：{"f1:name:timestamp": "1667568422619"}
     * @return 获取查询结果
     */
    Map<String, String> getRowToMap(String tableName, String rowKey, boolean withTimestamp);

    /**
     * get查询数据，返回Map数据类型，例如：{"f1:name": "leo", "f1:name:timestamp": "1667568422619", "f1:age": "18"}
     *
     * @param tableName     表名
     * @param rowKey        rowKey
     * @param familyName    列簇名
     * @param withTimestamp 是否返回时间戳，返回时间戳格式是：{"f1:name:timestamp": "1667568422619"}
     * @return 获取查询结果
     */
    Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, boolean withTimestamp);

    /**
     * get查询数据，返回Map数据类型，例如：{"f1:name": "leo", "f1:name:timestamp": "1667568422619", "f1:age": "18"}
     *
     * @param tableName     表名
     * @param rowKey        rowKey
     * @param familyName    列簇名
     * @param qualifiers    需要筛选的字段名
     * @param withTimestamp 是否返回时间戳，返回时间戳格式是：{"f1:name:timestamp": "1667568422619"}
     * @return 获取查询结果
     */
    Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, List<String> qualifiers, boolean withTimestamp);

    /**
     * 根据一批RowKey查询所有列簇和字段下的数据，查询结果映射为一个Java Bean列表.
     *
     * @param rowKeys row key list
     * @param clazz   java bean class type
     * @param <T>     泛型类型
     * @return 范型结果集合
     */
    <T> List<T> getRows(List<String> rowKeys, Class<T> clazz);

    /**
     * 根据一批RowKey查询某一个所属列簇的数据，查询结果映射为一个Java Bean列表.
     *
     * @param rowKeys    row key list
     * @param familyName family name
     * @param clazz      java bean class type
     * @param <T>        泛型类型
     * @return 范型结果集合
     */
    <T> List<T> getRows(List<String> rowKeys, String familyName, Class<T> clazz);

    /**
     * 根据一批RowKey查询某一个所属列簇以及某些字段的数据，查询结果映射为一个Java Bean列表.
     *
     * @param rowKeys    row key list
     * @param familyName family name
     * @param qualifiers qualifiers
     * @param clazz      java bean class type
     * @param <T>        泛型类型
     * @return 范型结果集合
     */
    <T> List<T> getRows(List<String> rowKeys, String familyName, List<String> qualifiers, Class<T> clazz);

    /**
     * get查询数据，可以指定自定义的Row Mapper
     *
     * @param tableName 表名
     * @param rowKeys   rowKey列表
     * @param rowMapper 自定义的RowMapper
     * @param <T>       泛型类型
     * @return get查询结果
     */
    <T> List<T> getRows(String tableName, List<String> rowKeys, RowMapper<T> rowMapper);

    /**
     * get查询某一列簇下的数据，可以指定自定义的Row Mapper
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return get查询结果
     */
    <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, RowMapper<T> rowMapper);

    /**
     * get查询某一列簇下的数据，可以指定多个字段，可以指定自定义的Row Mapper
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return get查询结果
     */
    <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, RowMapper<T> rowMapper);

    /**
     * get查询数据，返回Map数据类型，例如：{"f1:name": "leo", "f1:name:timestamp": "1667568422619", "f1:age": "18"}
     *
     * @param tableName     表名
     * @param rowKeys       rowKey列表
     * @param withTimestamp 是否返回时间戳，返回时间戳格式是：{"f1:name:timestamp": "1667568422619"}
     * @return 获取查询结果
     */
    Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, boolean withTimestamp);

    /**
     * get查询数据，返回Map数据类型，例如：{"f1:name": "leo", "f1:name:timestamp": "1667568422619", "f1:age": "18"}
     *
     * @param tableName     表名
     * @param rowKeys       rowKey列表
     * @param familyName    列簇名
     * @param withTimestamp 是否返回时间戳，返回时间戳格式是：{"f1:name:timestamp": "1667568422619"}
     * @return 获取查询结果
     */
    Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, boolean withTimestamp);

    /**
     * get查询数据，返回Map数据类型，例如：{"f1:name": "leo", "f1:name:timestamp": "1667568422619", "f1:age": "18"}
     *
     * @param tableName     表名
     * @param rowKeys       rowKey列表
     * @param familyName    列簇名
     * @param qualifiers    需要筛选的字段名
     * @param withTimestamp 是否返回时间戳，返回时间戳格式是：{"f1:name:timestamp": "1667568422619"}
     * @return 获取查询结果
     */
    Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, boolean withTimestamp);


    /**
     * scan HBase表数据，查询结果集映射为JavaBean列表
     *
     * @param scanQueryParams scan查询参数
     * @param clazz           结果集映射的JavaBean类型
     * @param <T>             泛型类型
     * @return 结果数据
     */
    <T> List<T> scan(ScanQueryParamsBuilder scanQueryParams, Class<T> clazz);

    /**
     * scan 所有数据
     *
     * @param tableName       表名
     * @param scanQueryParams scan查询参数
     * @param rowMapper       自定义的RowMapper
     * @param <T>             泛型类型
     * @return 查询结果集
     */
    <T> List<T> scan(String tableName, ScanQueryParamsBuilder scanQueryParams, RowMapper<T> rowMapper);


    /**
     * scan查询数据，返回Map列表类型，例如：["1001": {"info:name": "leo", "info:age": 18}]
     *
     * @param tableName       表名
     * @param scanQueryParams scan查询参数
     * @return 查询结果
     */
    List<Map<String, Map<String, String>>> scan(String tableName, ScanQueryParamsBuilder scanQueryParams);

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

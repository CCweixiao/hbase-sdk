package com.github.CCweixiao;

import java.util.List;
import java.util.Map;

/**
 * <p>定义HBase Thrift 的操作接口</p>
 *
 * @author leojie 2020/12/27 2:46 下午
 */
public interface HBaseThriftOperations {
    /**
     * 保存单条数据，构造Map类型的数据参数，例如： {"INFO:NAME": "leo", "INFO:AGE": 18}
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @param data      需要保存的数据. 例如： {"INFO:NAME":"leo"}
     */
    void save(String tableName, String rowKey, Map<String, String> data);

    /**
     * 批量保存数据，构造Map类型结构的列表数据参数，例如：
     * {"row1": {"INFO:NAME": "leo1", "INFO:AGE": 18}, "row2": {"INFO:NAME": "leo2", "INFO:AGE": 17}}
     *
     * @param tableName 表名
     * @param data      需要保存的数据. 例如： {"row1": {"INFO:NAME": "leo1"}, "row2": {"INFO:NAME": "leo2"}}
     */
    void saveBatch(String tableName, Map<String, Map<String, String>> data);

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
    Map<String, String> getByRowKeyToMap(String tableName, String rowKey);

    /**
     * 根据RowKey和列簇查询数据，返回Map结构的数据
     * 返回的数据格式为：{"INFO:NAME": "leo", "INFO:AGE": 18}
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @return 结果数据
     */
    Map<String, String> getByRowKeyWithFamilyToMap(String tableName, String rowKey, String familyName);

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
    Map<String, String> getByRowKeyWithFamilyAndQualifiersToMap(String tableName, String rowKey, String familyName, List<String> qualifiers);


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
    Map<String, String> getByRowKeyWithFamilyAndQualifiersToMap(String tableName, String rowKey, String familyName, String... qualifiers);

    /**
     * 根据RowKey查询数据，返回Map结构的数据
     * 返回的数据格式为：{"a1001" : {"INFO:NAME": "leo", "INFO:AGE": 18}}
     *
     * @param tableName  表名
     * @param rowKeyList rowKey列表
     * @return 结果数据
     */
    Map<String, Map<String, String>> getRowsByRowKeysToMap(String tableName, List<String> rowKeyList);

    /**
     * 根据RowKey和列簇查询数据，返回Map结构的数据
     * 返回的数据格式为：{"a1001" : {"INFO:NAME": "leo", "INFO:AGE": 18}}
     *
     * @param tableName  表名
     * @param rowKeyList rowKey列表
     * @param familyName 列簇名
     * @return 结果数据
     */
    Map<String, Map<String, String>> getRowsByRowKeysWithFamilyToMap(String tableName, List<String> rowKeyList, String familyName);

    /**
     * 根据RowKey和列簇以及字段名查询数据，返回Map结构的数据
     * 返回的数据格式为：{"a1001" : {"INFO:NAME": "leo", "INFO:AGE": 18}}
     *
     * @param tableName  表名
     * @param rowKeyList rowKey列表
     * @param familyName 列簇名
     * @param qualifiers 字段名列表
     * @return 结果数据
     */
    Map<String, Map<String, String>> getRowsByRowKeysWithFamilyAndQualifiersToMap(String tableName, List<String> rowKeyList, String familyName, List<String> qualifiers);

    /**
     * 根据RowKey和列簇以及字段名查询数据，返回Map结构的数据
     * 返回的数据格式为：{"a1001" : {"INFO:NAME": "leo", "INFO:AGE": 18}}
     *
     * @param tableName  表名
     * @param rowKeyList rowKey列表
     * @param familyName 列簇名
     * @param qualifiers 字段名列表
     * @return 结果数据
     */
    Map<String, Map<String, String>> getRowsByRowKeysWithFamilyAndQualifiersToMap(String tableName, List<String> rowKeyList, String familyName, String... qualifiers);

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

    /**
     * scan查询数据，返回Map列表类型，例如：[{"a10001" : {"info:name": "leo", "info:age": 18}}]
     *
     * @param tableName 表名
     * @param limit     限制的返回行数
     * @return 查询结果
     */
    List<Map<String, Map<String, String>>> findToMapList(String tableName, int limit);

    /**
     * scan 查询
     *
     * @param tableName    表名
     * @param startRow     开始rowKey
     * @param stopRow      结束rowKey
     * @param rowPrefix    rowKey前缀
     * @param familyName   列簇名
     * @param qualifiers   字段名列表
     * @param filterStr    过滤字符串
     * @param timestamp    时间戳
     * @param batchSize    批次大小
     * @param scanBatching scan缓存大小
     * @param reverse      是否反转
     * @param limit        limit条数
     * @return 查询结果
     */
    List<Map<String, Map<String, String>>> findToMapList(String tableName, String startRow, String stopRow,
                                                         String rowPrefix, String familyName,
                                                         List<String> qualifiers, String filterStr,
                                                         Long timestamp, Integer batchSize, Integer scanBatching,
                                                         boolean reverse, Integer limit);


    /**
     * 获取所有表名
     *
     * @return 所有表名
     */
    List<String> getTableNames();
}

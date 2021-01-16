package com.github.CCweixiao;

import java.util.List;

/**
 * HBase数据删除相关操作的API
 *
 * @author leojie 2020/12/31 11:16 下午
 */
public interface HBaseDeleteOperations {
    /**
     * 根据RowKey删除数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     */
    void deleteByRowKey(String tableName, String rowKey);

    /**
     * 根据RowKey删除某一列簇下的数据
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名称
     */
    void deleteByRowKeyWithFamily(String tableName, String rowKey, String familyName);

    /**
     * 根据RowKey删除某一列簇下的数据，同时可以指定字段名
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名称
     * @param columns    字段名列表
     */
    void deleteByRowKeyWithFamilyAndColumns(String tableName, String rowKey, String familyName, List<String> columns);

    /**
     * 根据RowKey删除某一列簇下的数据，同时可以指定字段名
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名称
     * @param columns    字段名列表
     */
    void deleteByRowKeyWithFamilyAndColumns(String tableName, String rowKey, String familyName, String... columns);


    /**
     * 根据RowKey 批量删除数据
     *
     * @param tableName 表名
     * @param rowKeys   rowKey列表
     */
    void deleteBatchByRowKeys(String tableName, List<String> rowKeys);

    /**
     * 根据RowKey批量删除某一列簇下的数据
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名称
     */
    void deleteBatchByRowKeysWithFamily(String tableName, List<String> rowKeys, String familyName);

    /**
     * 根据RowKey批量删除某一列簇以及指定字段下的数据
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名称
     * @param columns    字段名列表
     */
    void deleteBatchByRowKeysWithFamilyAndColumns(String tableName, List<String> rowKeys, String familyName, List<String> columns);

    /**
     * 根据RowKey批量删除某一列簇以及指定字段下的数据
     *
     * @param tableName  表名
     * @param rowKeys    rowKey列表
     * @param familyName 列簇名称
     * @param columns    字段名列表
     */
    void deleteBatchByRowKeysWithFamilyAndColumns(String tableName, List<String> rowKeys, String familyName, String... columns);
}

package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

import java.util.Map;

/**
 * @author leojie 2022/12/7 20:41
 */
public interface IHBaseSqlAdapter {

    void createVirtualTable(String hql);

    HBaseDataSet select(String hql);

    HBaseDataSet select(String hql, Map<String, Object> params);

    void insert(String hql);

    void delete(String hql);

    String parseTableNameFromHql(String hql);

    HQLType parseHQLType(String hql);

    void registerTableSchema(HBaseTableSchema tableSchema);
    void printTableSchema(String tableName);
}

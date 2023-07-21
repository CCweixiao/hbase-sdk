package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;

import java.util.Map;

/**
 * @author leojie 2022/12/7 20:41
 */
public interface IHBaseSqlAdapter {

    HBaseDataSet select(String hsql);

    HBaseDataSet select(String hsql, Map<String, Object> params);

    void insert(String hsql);

    void delete(String hsql);

    String parseTableNameFromHql(String hql);

    HQLType parseHQLType(String hql);
}

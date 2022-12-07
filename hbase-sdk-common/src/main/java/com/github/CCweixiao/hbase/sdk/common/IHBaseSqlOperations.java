package com.github.CCweixiao.hbase.sdk.common;

import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import java.util.Map;

/**
 * @author leojie 2022/12/7 20:41
 */
public interface IHBaseSqlOperations {

    HBaseDataSet select(String hsql);

    HBaseDataSet select(String hsql, Map<String, Object> params);

    void insert(String hsql);

    void delete(String hsql);
}

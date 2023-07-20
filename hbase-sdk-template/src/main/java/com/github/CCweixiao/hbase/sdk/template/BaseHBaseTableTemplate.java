package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOpAdapter;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import org.apache.hadoop.hbase.client.Get;

import java.util.List;

/**
 * @author leojie 2023/7/20 22:12
 */
public abstract class BaseHBaseTableTemplate implements IHBaseTableOpAdapter {
    abstract HBaseRowData getToRowData(String tableName, Get get);
    abstract  HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, Get get, int versions);

    abstract List<HBaseRowData> getRowsToRowData(String tableName, List<Get> gets);

    abstract <T> List<T> getRowsToRowData(String tableName, List<Get> gets, RowMapper<T> rowMapper);
}

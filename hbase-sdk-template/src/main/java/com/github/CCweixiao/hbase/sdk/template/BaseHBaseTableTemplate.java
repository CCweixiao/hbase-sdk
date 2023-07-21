package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOpAdapter;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;

import java.util.List;

/**
 * @author leojie 2023/7/20 22:12
 */
public abstract class BaseHBaseTableTemplate implements IHBaseTableOpAdapter {
    abstract <T> T getRow(String tableName, Get get, Class<T> clazz);

    abstract <T> T getRow(String tableName, Get get, RowMapper<T> rowMapper);

    abstract HBaseRowData getRowToRowData(String tableName, Get get);

    abstract HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, Get get, int versions);

    abstract List<HBaseRowData> getRowsToRowData(String tableName, List<Get> gets);

    abstract <T> List<T> getRowsToRowData(String tableName, List<Get> gets, RowMapper<T> rowMapper);

    abstract List<HBaseRowData> scan(String tableName, String startRow, String endRow);

    abstract List<HBaseRowData> scanToRowDataList(String tableName, Scan scan);

    abstract List<HBaseRowDataWithMultiVersions> scanToMultiVersions(String tableName, String startRow, String endRow, int versions);

    abstract List<HBaseRowDataWithMultiVersions> scanToMultiVersions(String tableName, Scan scan, int versions);
}

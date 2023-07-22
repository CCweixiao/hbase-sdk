package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import org.apache.hadoop.hbase.client.Scan;

import java.util.List;

/**
 * @author leojie 2023/7/20 19:36
 */
public interface IHBaseTableScanAdapter {
    Scan buildScan(ScanParams scanParams);
    <T> List<T> scan(Scan scan, Class<T> clazz);

    <T> List<T> scan(String tableName, Scan scan, RowMapper<T> rowMapper);

    List<HBaseRowData> scan(String tableName, Scan scan);

    List<HBaseRowDataWithMultiVersions> scanWithMultiVersions(String tableName, Scan scan, int versions);
}

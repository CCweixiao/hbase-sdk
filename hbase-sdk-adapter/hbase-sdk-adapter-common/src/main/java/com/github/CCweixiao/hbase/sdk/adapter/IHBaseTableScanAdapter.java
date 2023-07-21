package com.github.CCweixiao.hbase.sdk.adapter;

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

    List<HBaseRowData> scan(String tableName, String startRow, String endRow);

    List<HBaseRowData> scanToRowDataList(String tableName, Scan scan);

    List<HBaseRowDataWithMultiVersions> scanToMultiVersions(String tableName, String startRow, String endRow, int versions);

    List<HBaseRowDataWithMultiVersions> scanToMultiVersions(String tableName, Scan scan, int versions);

}

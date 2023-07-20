package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import org.apache.hadoop.hbase.client.Scan;

/**
 * @author leojie 2023/7/20 19:36
 */
public interface IHBaseTableScanAdapter {
    Scan buildScan(ScanParams scanQueryParams);
}

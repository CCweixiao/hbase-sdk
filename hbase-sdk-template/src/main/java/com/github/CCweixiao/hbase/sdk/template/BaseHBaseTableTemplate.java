package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.adapter.IHBaseTableScanAdapter;
import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOpAdapter;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import org.apache.hadoop.hbase.client.Get;
import java.util.List;
import java.util.Optional;

/**
 * @author leojie 2023/7/20 22:12
 */
public abstract class BaseHBaseTableTemplate implements IHBaseTableOpAdapter, IHBaseTableScanAdapter {
    abstract <T> Optional<T> get(Get get, Class<T> clazz);
    abstract <T> Optional<T> get(String tableName, Get get, RowMapper<T> rowMapper);
    abstract HBaseRowData get(String tableName, Get get);
    abstract <T> List<T> getWithMultiVersions(Get get, int versions, Class<T> clazz);
    abstract <T> List<T> getWithMultiVersions(String tableName, Get get, int versions, RowMapper<T> rowMapper);
    abstract HBaseRowDataWithMultiVersions getWithMultiVersions(String tableName, Get get, int versions);
    abstract <T> List<T> gets(String tableName, List<Get> gets, Class<T> clazz);
    abstract <T> List<T> gets(String tableName, List<Get> gets, RowMapper<T> rowMapper);
    abstract List<HBaseRowData> gets(String tableName, List<Get> gets);
}

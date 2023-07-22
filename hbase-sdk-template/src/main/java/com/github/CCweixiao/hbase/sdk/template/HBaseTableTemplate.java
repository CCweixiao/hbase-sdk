package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.HBaseTableAdapter;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowsParam;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * @author leojie 2022/10/22 18:58
 */
public class HBaseTableTemplate extends BaseHBaseTableTemplate {
    private final Configuration configuration;
    private final HBaseTableAdapter tableOpAdapter;

    private HBaseTableTemplate(Builder builder) {
        this.configuration = builder.configuration;
        this.tableOpAdapter = new HBaseTableAdapter(this.configuration);
    }

    @Override
    public void save(String tableName, String rowKey, Map<String, Object> data) {
        tableOpAdapter.save(tableName, rowKey, data);
    }

    @Override
    public void saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        tableOpAdapter.saveBatch(tableName, data);
    }

    @Override
    public <T> void save(T t) {
        tableOpAdapter.save(t);
    }

    @Override
    public <T> void saveBatch(List<T> list) {
        tableOpAdapter.saveBatch(list);
    }

    @Override
    public <T> Optional<T> getRow(GetRowParam getRowParam, Class<T> clazz) {
        return tableOpAdapter.getRow(getRowParam, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper) {
        return tableOpAdapter.getRow(tableName, getRowParam, rowMapper);
    }

    @Override
    public HBaseRowData getRow(String tableName, GetRowParam getRowParam) {
        return tableOpAdapter.getRow(tableName, getRowParam);
    }

    @Override
    public <T> List<T> getWithMultiVersions(GetRowParam getRowParam, Class<T> clazz) {
        return tableOpAdapter.getWithMultiVersions(getRowParam, clazz);
    }

    @Override
    public <T> List<T> getWithMultiVersions(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper) {
        return tableOpAdapter.getWithMultiVersions(tableName, getRowParam, rowMapper);
    }

    @Override
    public HBaseRowDataWithMultiVersions getWithMultiVersions(String tableName, GetRowParam getRowParam) {
        return tableOpAdapter.getWithMultiVersions(tableName, getRowParam);
    }

    @Override
    <T> Optional<T> get(Get get, Class<T> clazz) {
        return tableOpAdapter.get(get, clazz);
    }

    @Override
    <T> Optional<T> get(String tableName, Get get, RowMapper<T> rowMapper) {
        return tableOpAdapter.get(tableName, get, rowMapper);
    }

    @Override
    public HBaseRowData get(String tableName, Get get) {
        return tableOpAdapter.get(tableName, get);
    }

    @Override
    public <T> List<T> getWithMultiVersions(Get get, int versions, Class<T> clazz) {
        return tableOpAdapter.getWithMultiVersions(get, versions, clazz);
    }

    @Override
    public <T> List<T> getWithMultiVersions(String tableName, Get get, int versions, RowMapper<T> rowMapper) {
        return tableOpAdapter.getWithMultiVersions(tableName, get, versions, rowMapper);
    }

    @Override
    public HBaseRowDataWithMultiVersions getWithMultiVersions(String tableName, Get get, int versions) {
        return tableOpAdapter.getWithMultiVersions(tableName, get, versions);
    }

    @Override
    public <T> List<T> getRows(GetRowsParam getRowsParam, Class<T> clazz) {
        return tableOpAdapter.getRows(getRowsParam, clazz);
    }

    @Override
    public <T> List<T> getRows(String tableName, GetRowsParam getRowsParam, RowMapper<T> rowMapper) {
        return tableOpAdapter.getRows(tableName, getRowsParam, rowMapper);
    }

    @Override
    public List<HBaseRowData> getRows(String tableName, GetRowsParam getRowsParam) {
        return tableOpAdapter.getRows(tableName, getRowsParam);
    }

    @Override
    <T> List<T> gets(String tableName, List<Get> gets, Class<T> clazz) {
        return tableOpAdapter.gets(tableName, gets, clazz);
    }

    @Override
    <T> List<T> gets(String tableName, List<Get> gets, RowMapper<T> rowMapper) {
        return tableOpAdapter.gets(tableName, gets, rowMapper);
    }

    @Override
    List<HBaseRowData> gets(String tableName, List<Get> gets) {
        return tableOpAdapter.gets(tableName, gets);
    }

    @Override
    public Scan buildScan(ScanParams scanParams) {
        return tableOpAdapter.buildScan(scanParams);
    }

    @Override
    public <T> List<T> scan(ScanParams scanParams, Class<T> clazz) {
        return tableOpAdapter.scan(scanParams, clazz);
    }

    @Override
    public <T> List<T> scan(String tableName, ScanParams scanParams, RowMapper<T> rowMapper) {
        return tableOpAdapter.scan(tableName, scanParams, rowMapper);
    }

    @Override
    public List<HBaseRowData> scan(String tableName, ScanParams scanParams) {
        return tableOpAdapter.scan(tableName, scanParams);
    }

    @Override
    public List<HBaseRowDataWithMultiVersions> scanWithMultiVersions(String tableName, ScanParams scanParams) {
        return tableOpAdapter.scanWithMultiVersions(tableName, scanParams);
    }

    @Override
    public <T> List<T> scan(Scan scan, Class<T> clazz) {
        return tableOpAdapter.scan(scan, clazz);
    }

    @Override
    public <T> List<T> scan(String tableName, Scan scan, RowMapper<T> rowMapper) {
        return tableOpAdapter.scan(tableName, scan, rowMapper);
    }

    @Override
    public List<HBaseRowData> scan(String tableName, Scan scan) {
        return tableOpAdapter.scan(tableName, scan);
    }

    @Override
    public List<HBaseRowDataWithMultiVersions> scanWithMultiVersions(String tableName, Scan scan, int versions) {
        return tableOpAdapter.scanWithMultiVersions(tableName, scan, versions);
    }

    @Override
    public <T> void delete(T t) {
        tableOpAdapter.delete(t);
    }

    @Override
    public void delete(String tableName, String rowKey) {
        tableOpAdapter.delete(tableName, rowKey);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName) {
        tableOpAdapter.delete(tableName, rowKey, familyName);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        tableOpAdapter.delete(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, String... qualifiers) {
        tableOpAdapter.delete(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public <T> void deleteBatch(List<T> list) {
        tableOpAdapter.deleteBatch(list);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys) {
        tableOpAdapter.deleteBatch(tableName, rowKeys);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName) {
        tableOpAdapter.deleteBatch(tableName, rowKeys, familyName);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        tableOpAdapter.deleteBatch(tableName, rowKeys, familyName, qualifiers);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers) {
        tableOpAdapter.deleteBatch(tableName, rowKeys, familyName, qualifiers);
    }

    public static class Builder extends BaseTemplateBuilder<HBaseTableTemplate> {
        @Override
        public HBaseTableTemplate build() {
            return new HBaseTableTemplate(this);
        }
    }

    public static HBaseTableTemplate of(Configuration configuration) {
        return new HBaseTableTemplate.Builder().configuration(configuration).build();
    }

    public static HBaseTableTemplate of(Properties properties) {
        return new HBaseTableTemplate.Builder().configuration(properties).build();
    }

    public static HBaseTableTemplate of(String zkQuorum, String zkClientPort) {
        return new HBaseTableTemplate.Builder().configuration(zkQuorum, zkClientPort).build();
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}

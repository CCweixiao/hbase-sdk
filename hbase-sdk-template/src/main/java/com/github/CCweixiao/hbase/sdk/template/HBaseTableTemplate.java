package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.HBaseTableAdapter;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Get;

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
    public int saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        return tableOpAdapter.saveBatch(tableName, data);
    }

    @Override
    public <T> void save(T t) {
        tableOpAdapter.save(t);
    }

    @Override
    public <T> int saveBatch(List<T> list) {
        return tableOpAdapter.saveBatch(list);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, Class<T> clazz) {
        return tableOpAdapter.getRow(rowKey, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, Class<T> clazz) {
        return tableOpAdapter.getRow(rowKey, familyName, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        return tableOpAdapter.getRow(rowKey, familyName, qualifiers, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, RowMapper<T> rowMapper) {
        return tableOpAdapter.getRow(tableName, rowKey, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper) {
        return tableOpAdapter.getRow(tableName, rowKey, familyName, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return tableOpAdapter.getRow(tableName, rowKey, familyName, qualifiers, rowMapper);
    }

    @Override
    public HBaseRowData getToRowData(String tableName, String rowKey) {
        return tableOpAdapter.getToRowData(tableName, rowKey);
    }

    @Override
    public HBaseRowData getToRowData(String tableName, String rowKey, String familyName) {
        return tableOpAdapter.getToRowData(tableName, rowKey, familyName);
    }

    @Override
    public HBaseRowData getToRowData(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        return tableOpAdapter.getToRowData(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public HBaseRowData getToRowData(String tableName, Get get) {
        return tableOpAdapter.getToRowData(tableName, get);
    }

    @Override
    public HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, String rowKey, int versions) {
        return tableOpAdapter.getToRowDataWithMultiVersions(tableName, rowKey, versions);
    }

    @Override
    public HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, String rowKey, String familyName, int versions) {
        return tableOpAdapter.getToRowDataWithMultiVersions(tableName, rowKey, familyName, versions);
    }

    @Override
    public HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, String rowKey, String familyName, List<String> qualifiers, int versions) {
        return tableOpAdapter.getToRowDataWithMultiVersions(tableName, rowKey, familyName, qualifiers, versions);
    }

    @Override
    public HBaseRowDataWithMultiVersions getToRowDataWithMultiVersions(String tableName, Get get, int versions) {
        return tableOpAdapter.getToRowDataWithMultiVersions(tableName, get, versions);
    }

    @Override
    List<HBaseRowData> getRowsToRowData(String tableName, List<Get> gets) {
        return tableOpAdapter.getRowsToRowData(tableName, gets);
    }

    @Override
    <T> List<T> getRowsToRowData(String tableName, List<Get> gets, RowMapper<T> rowMapper) {
        return tableOpAdapter.getRowsToRowData(tableName, gets, rowMapper);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, Class<T> clazz) {
        return tableOpAdapter.getRows(rowKeys, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, Class<T> clazz) {
        return tableOpAdapter.getRows(rowKeys, familyName, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, List<String> qualifiers, Class<T> clazz) {
        return tableOpAdapter.getRows(rowKeys, familyName, qualifiers, clazz);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, RowMapper<T> rowMapper) {
        return tableOpAdapter.getRows(tableName, rowKeys, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, RowMapper<T> rowMapper) {
        return tableOpAdapter.getRows(tableName, rowKeys, familyName, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return tableOpAdapter.getRows(tableName, rowKeys, familyName, qualifiers, rowMapper);
    }

    @Override
    public List<HBaseRowData> getToRowsData(String tableName, List<String> rowKeys) {
        return tableOpAdapter.getToRowsData(tableName, rowKeys);
    }

    @Override
    public List<HBaseRowData> getToRowsData(String tableName, List<String> rowKeys, String familyName) {
        return tableOpAdapter.getToRowsData(tableName, rowKeys, familyName);
    }

    @Override
    public List<HBaseRowData> getToRowsData(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        return tableOpAdapter.getToRowsData(tableName, rowKeys, familyName, qualifiers);
    }

    @Override
    public <T> List<T> scan(ScanParams scanQueryParams, Class<T> clazz) {
        return tableOpAdapter.scan(scanQueryParams, clazz);
    }

    @Override
    public <T> List<T> scan(String tableName, ScanParams scanQueryParams, RowMapper<T> rowMapper) {
        return tableOpAdapter.scan(tableName, scanQueryParams, rowMapper);
    }

    @Override
    public List<HBaseRowData> scan(String tableName, ScanParams scanQueryParams) {
        return tableOpAdapter.scan(tableName, scanQueryParams);
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

    public static class Builder {
        private Configuration configuration;

        private Builder() {
        }

        public Builder configuration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder configuration(Properties properties) {
            if (properties == null || properties.isEmpty()) {
                this.configuration = HBaseConfiguration.create();
                return this;
            }
            if (this.configuration == null) {
                this.configuration = HBaseConfiguration.create();
                for (String k : properties.stringPropertyNames()) {
                    this.configuration.set(k, properties.getProperty(k));
                }
            }
            return this;
        }

        public Builder configuration(String key, String value) {
            if (this.configuration == null) {
                this.configuration = HBaseConfiguration.create();
            }
            this.configuration.set(key, value);
            return this;
        }

        public Builder zookeeperQuorum(String zkQuorum) {
            return this.configuration(HConstants.ZOOKEEPER_QUORUM, zkQuorum);
        }

        public Builder zookeeperClientPort(String zkClientPort) {
            return this.configuration(HConstants.ZOOKEEPER_CLIENT_PORT, zkClientPort);
        }

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

    public static HBaseTableTemplate.Builder builder() {
        return new HBaseTableTemplate.Builder();
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}

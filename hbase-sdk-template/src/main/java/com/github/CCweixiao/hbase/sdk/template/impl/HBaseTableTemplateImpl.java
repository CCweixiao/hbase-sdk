package com.github.CCweixiao.hbase.sdk.template.impl;

import com.github.CCweixiao.hbase.sdk.HBaseTableAdapterImpl;
import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseColData;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;
import com.github.CCweixiao.hbase.sdk.template.IHBaseTableTemplate;
import org.apache.hadoop.hbase.HConstants;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * @author leojie 2022/10/22 18:58
 */
public class HBaseTableTemplateImpl implements IHBaseTableTemplate {
    private final Properties properties;
    private final IHBaseTableOperations tableOperations;

    private HBaseTableTemplateImpl(Builder builder) {
        this.properties = builder.properties;
        this.tableOperations = new HBaseTableAdapterImpl(properties);
    }

    @Override
    public void save(String tableName, String rowKey, Map<String, Object> data) {
        tableOperations.save(tableName, rowKey, data);
    }

    @Override
    public int saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        return tableOperations.saveBatch(tableName, data);
    }

    @Override
    public <T> T save(T t) {
        return tableOperations.save(t);
    }

    @Override
    public <T> int saveBatch(List<T> list) {
        return tableOperations.saveBatch(list);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, Class<T> clazz) {
        return tableOperations.getRow(rowKey, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, Class<T> clazz) {
        return tableOperations.getRow(rowKey, familyName, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        return tableOperations.getRow(rowKey, familyName, qualifiers, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, RowMapper<T> rowMapper) {
        return tableOperations.getRow(tableName, rowKey, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper) {
        return tableOperations.getRow(tableName, rowKey, familyName, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return tableOperations.getRow(tableName, rowKey, familyName, qualifiers, rowMapper);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, boolean withTimestamp) {
        return tableOperations.getRowToMap(tableName, rowKey, withTimestamp);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, boolean withTimestamp) {
        return tableOperations.getRowToMap(tableName, rowKey, familyName, withTimestamp);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, List<String> qualifiers, boolean withTimestamp) {
        return tableOperations.getRowToMap(tableName, rowKey, familyName, qualifiers, withTimestamp);
    }

    @Override
    public Map<String, List<HBaseColData>> getRowToMapWithMultiVersions(String tableName, String rowKey, String familyName, List<String> qualifiers, int version) {
        return tableOperations.getRowToMapWithMultiVersions(tableName, rowKey, familyName, qualifiers, version);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, Class<T> clazz) {
        return tableOperations.getRows(rowKeys, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, Class<T> clazz) {
        return tableOperations.getRows(rowKeys, familyName, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, List<String> qualifiers, Class<T> clazz) {
        return tableOperations.getRows(rowKeys, familyName, qualifiers, clazz);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, RowMapper<T> rowMapper) {
        return tableOperations.getRows(tableName, rowKeys, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, RowMapper<T> rowMapper) {
        return tableOperations.getRows(tableName, rowKeys, familyName, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return tableOperations.getRows(tableName, rowKeys, familyName, qualifiers, rowMapper);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, boolean withTimestamp) {
        return tableOperations.getRowsToMap(tableName, rowKeys, withTimestamp);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, boolean withTimestamp) {
        return tableOperations.getRowsToMap(tableName, rowKeys, familyName, withTimestamp);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, boolean withTimestamp) {
        return tableOperations.getRowsToMap(tableName, rowKeys, familyName, qualifiers, withTimestamp);
    }

    @Override
    public <T> List<T> scan(ScanQueryParamsBuilder scanQueryParams, Class<T> clazz) {
        return tableOperations.scan(scanQueryParams, clazz);
    }

    @Override
    public <T> List<T> scan(String tableName, ScanQueryParamsBuilder scanQueryParams, RowMapper<T> rowMapper) {
        return tableOperations.scan(tableName, scanQueryParams, rowMapper);
    }

    @Override
    public List<Map<String, Map<String, String>>> scan(String tableName, ScanQueryParamsBuilder scanQueryParams) {
        return tableOperations.scan(tableName, scanQueryParams);
    }

    @Override
    public <T> void delete(T t) {
        tableOperations.delete(t);
    }

    @Override
    public void delete(String tableName, String rowKey) {
        tableOperations.delete(tableName, rowKey);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName) {
        tableOperations.delete(tableName, rowKey, familyName);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        tableOperations.delete(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, String... qualifiers) {
        tableOperations.delete(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public <T> void deleteBatch(List<T> list) {
        tableOperations.deleteBatch(list);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys) {
        tableOperations.deleteBatch(tableName, rowKeys);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName) {
        tableOperations.deleteBatch(tableName, rowKeys, familyName);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        tableOperations.deleteBatch(tableName, rowKeys, familyName, qualifiers);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers) {
        tableOperations.deleteBatch(tableName, rowKeys, familyName, qualifiers);
    }

    public static class Builder {
        private Properties properties;

        public Builder() {
        }

        public Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder addProp(String key, String value) {
            if (this.properties == null) {
                this.properties = new Properties();
            }
            this.properties.setProperty(key, value);
            return this;
        }

        public Builder zookeeperQuorum(String zookeeperQuorum) {
            addProp(HConstants.ZOOKEEPER_QUORUM, zookeeperQuorum);
            return this;
        }

        public Builder zookeeperClientPort(String zookeeperClientPort) {
            addProp(HConstants.ZOOKEEPER_CLIENT_PORT, zookeeperClientPort);
            return this;
        }

        public HBaseTableTemplateImpl build() {
            return new HBaseTableTemplateImpl(this);
        }
    }

    public Properties getProperties() {
        return properties;
    }
}

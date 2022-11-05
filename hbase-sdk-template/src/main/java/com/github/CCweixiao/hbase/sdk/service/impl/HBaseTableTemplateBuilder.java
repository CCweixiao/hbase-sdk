package com.github.CCweixiao.hbase.sdk.service.impl;

import com.github.CCweixiao.hbase.sdk.common.HBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;
import com.github.CCweixiao.hbase.sdk.service.HBaseTemplateFactory;
import com.github.CCweixiao.hbase.sdk.service.IHBaseTableTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * @author leojie 2022/10/22 18:58
 */
public class HBaseTableTemplateBuilder implements IHBaseTableTemplate {
    private final String sdkVersion;
    private final Properties properties;

    private HBaseTableTemplateBuilder(Builder builder) {
        this.sdkVersion = builder.sdkAdapterVersion;
        this.properties = builder.properties;
    }

    @Override
    public void save(String tableName, String rowKey, Map<String, Object> data) {
        getHBaseTableOperations().save(tableName, rowKey, data);
    }

    @Override
    public int saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        return getHBaseTableOperations().saveBatch(tableName, data);
    }

    @Override
    public <T> T save(T t) throws Exception {
        return getHBaseTableOperations().save(t);
    }

    @Override
    public <T> int saveBatch(List<T> list) throws Exception {
        return getHBaseTableOperations().saveBatch(list);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, Class<T> clazz) {
        return getHBaseTableOperations().getRow(rowKey, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, Class<T> clazz) {
        return getHBaseTableOperations().getRow(rowKey, familyName, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        return getHBaseTableOperations().getRow(rowKey, familyName, qualifiers, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, RowMapper<T> rowMapper) {
        return getHBaseTableOperations().getRow(tableName, rowKey, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper) {
        return getHBaseTableOperations().getRow(tableName, rowKey, familyName, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return getHBaseTableOperations().getRow(tableName, rowKey, familyName, qualifiers, rowMapper);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, boolean withTimestamp) {
        return getHBaseTableOperations().getRowToMap(tableName, rowKey, withTimestamp);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, boolean withTimestamp) {
        return getHBaseTableOperations().getRowToMap(tableName, rowKey, familyName, withTimestamp);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, List<String> qualifiers, boolean withTimestamp) {
        return getHBaseTableOperations().getRowToMap(tableName, rowKey, familyName, qualifiers, withTimestamp);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, Class<T> clazz) {
        return getHBaseTableOperations().getRows(rowKeys, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, Class<T> clazz) {
        return getHBaseTableOperations().getRows(rowKeys, familyName, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, List<String> qualifiers, Class<T> clazz) {
        return getHBaseTableOperations().getRows(rowKeys, familyName, qualifiers, clazz);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, RowMapper<T> rowMapper) {
        return getHBaseTableOperations().getRows(tableName, rowKeys, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, RowMapper<T> rowMapper) {
        return getHBaseTableOperations().getRows(tableName, rowKeys, familyName, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return getHBaseTableOperations().getRows(tableName, rowKeys, familyName, qualifiers, rowMapper);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, boolean withTimestamp) {
        return getHBaseTableOperations().getRowsToMap(tableName, rowKeys, withTimestamp);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, boolean withTimestamp) {
        return getHBaseTableOperations().getRowsToMap(tableName, rowKeys, familyName, withTimestamp);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, boolean withTimestamp) {
        return getHBaseTableOperations().getRowsToMap(tableName, rowKeys, familyName, qualifiers, withTimestamp);
    }

    @Override
    public <T> List<T> scan(ScanQueryParamsBuilder scanQueryParams, Class<T> clazz) {
        return getHBaseTableOperations().scan(scanQueryParams, clazz);
    }

    @Override
    public <T> List<T> scan(String tableName, ScanQueryParamsBuilder scanQueryParams, RowMapper<T> rowMapper) {
        return getHBaseTableOperations().scan(tableName, scanQueryParams, rowMapper);
    }

    @Override
    public List<Map<String, Map<String, String>>> scan(String tableName, ScanQueryParamsBuilder scanQueryParams) {
        return getHBaseTableOperations().scan(tableName, scanQueryParams);
    }

    @Override
    public void delete(String tableName, String rowKey) {
        getHBaseTableOperations().delete(tableName, rowKey);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName) {
        getHBaseTableOperations().delete(tableName, rowKey, familyName);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        getHBaseTableOperations().delete(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, String... qualifiers) {
        getHBaseTableOperations().delete(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys) {
        getHBaseTableOperations().deleteBatch(tableName, rowKeys);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName) {
        getHBaseTableOperations().deleteBatch(tableName, rowKeys, familyName);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        getHBaseTableOperations().deleteBatch(tableName, rowKeys, familyName, qualifiers);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers) {
        getHBaseTableOperations().deleteBatch(tableName, rowKeys, familyName, qualifiers);
    }

    public static class Builder {
        private String sdkAdapterVersion;
        private Properties properties;

        public Builder() {
        }

        public Builder sdkAdapterVersion(String sdkAdapterVersion) {
            this.sdkAdapterVersion = sdkAdapterVersion;
            return this;
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

        public HBaseTableTemplateBuilder build() {
            return new HBaseTableTemplateBuilder(this);
        }
    }

    private HBaseTableOperations getHBaseTableOperations() {
        return HBaseTemplateFactory.getHBaseTableOperations(this.getSdkVersion(), this.getProperties());
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public Properties getProperties() {
        return properties;
    }
}

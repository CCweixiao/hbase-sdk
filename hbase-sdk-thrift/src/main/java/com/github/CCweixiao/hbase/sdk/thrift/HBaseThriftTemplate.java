package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseColData;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author leojie 2020/12/27 11:41 下午
 */
public class HBaseThriftTemplate implements IHBaseThriftOperations {

    private final HBaseThriftPool pool;

    public HBaseThriftTemplate(String host, int port) {
        HBaseThriftPoolConfig config = new HBaseThriftPoolConfig();
        pool = new HBaseThriftPool(config, host, port);
    }

    public HBaseThriftTemplate(String host, int port, int poolSize) {
        HBaseThriftPoolConfig config = new HBaseThriftPoolConfig();
        config.setMaxTotal(poolSize);
        config.setMaxIdle(poolSize);
        pool = new HBaseThriftPool(config, host, port);
    }

    public HBaseThriftTemplate(String host, int port, HBaseThriftPoolConfig config){
        pool = new HBaseThriftPool(config, host, port);
    }

    /**
     * Clear active connection objects actively in connection pool if you need.
     */
    public void clearThriftPool() {
        pool.clearInternalPool();
    }

    @Override
    public void save(String tableName, String rowKey, Map<String, Object> data) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.save(tableName, rowKey, data);
        }
    }
    @Override
    public int saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
           return hBaseThrift.saveBatch(tableName, data);
        }
    }
    @Override
    public <T> T save(T t) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.save(t);
        }
    }

    @Override
    public <T> int saveBatch(List<T> list) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.saveBatch(list);
        }
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRow(rowKey, clazz);
        }
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRow(rowKey, familyName, clazz);
        }
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRow(rowKey, familyName, qualifiers, clazz);
        }
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRow(tableName, rowKey, rowMapper);
        }
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRow(tableName, rowKey, familyName, rowMapper);
        }
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRow(tableName, rowKey, familyName, qualifiers, rowMapper);
        }
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, boolean withTimestamp) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowToMap(tableName, rowKey, withTimestamp);
        }
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, boolean withTimestamp) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowToMap(tableName, rowKey, familyName, withTimestamp);
        }
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, List<String> qualifiers, boolean withTimestamp) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowToMap(tableName, rowKey, familyName, qualifiers, withTimestamp);
        }
    }

    @Override
    public Map<String, List<HBaseColData>> getRowToMapWithMultiVersions(String tableName, String rowKey, String familyName, List<String> qualifiers, int version) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowToMapWithMultiVersions(tableName, rowKey, familyName, qualifiers, version);
        }
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRows(rowKeys, clazz);
        }
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRows(rowKeys, familyName, clazz);
        }
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, List<String> qualifiers, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRows(rowKeys, familyName, qualifiers, clazz);
        }
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRows(tableName, rowKeys, rowMapper);
        }
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRows(tableName, rowKeys, familyName, rowMapper);
        }
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRows(tableName, rowKeys, familyName, qualifiers, rowMapper);
        }
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, boolean withTimestamp) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowsToMap(tableName, rowKeys, withTimestamp);
        }
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, boolean withTimestamp) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowsToMap(tableName, rowKeys, familyName, withTimestamp);
        }
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, boolean withTimestamp) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowsToMap(tableName, rowKeys, familyName, qualifiers, withTimestamp);
        }
    }

    @Override
    public <T> List<T> scan(ScanQueryParamsBuilder scanQueryParams, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.scan(scanQueryParams, clazz);
        }
    }

    @Override
    public <T> List<T> scan(String tableName, ScanQueryParamsBuilder scanQueryParams, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.scan(tableName, scanQueryParams, rowMapper);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> scan(String tableName, ScanQueryParamsBuilder scanQueryParams) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.scan(tableName, scanQueryParams);
        }
    }

    @Override
    public <T> void delete(T t) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.delete(t);
        }
    }

    @Override
    public void delete(String tableName, String rowKey) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.delete(tableName, rowKey);
        }
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.delete(tableName, rowKey, familyName);
        }
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.delete(tableName, rowKey, familyName, qualifiers);
        }
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, String... qualifiers) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.delete(tableName, rowKey, familyName, qualifiers);
        }
    }

    @Override
    public <T> void deleteBatch(List<T> list) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.deleteBatch(list);
        }
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.deleteBatch(tableName, rowKeys);
        }
    }
    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.deleteBatch(tableName, rowKeys, familyName);
        }
    }
    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.deleteBatch(tableName, rowKeys, familyName, qualifiers);
        }
    }
    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.deleteBatch(tableName, rowKeys, familyName, qualifiers);
        }
    }
}

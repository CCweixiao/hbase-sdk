package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowsParam;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;

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
    public void saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.saveBatch(tableName, data);
        }
    }
    @Override
    public <T> void save(T t) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.save(t);
        }
    }

    @Override
    public <T> void saveBatch(List<T> list) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.saveBatch(list);
        }
    }

    @Override
    public <T> Optional<T> getRow(GetRowParam getRowParam, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRow(getRowParam, clazz);
        }
    }

    @Override
    public <T> Optional<T> getRow(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRow(tableName, getRowParam, rowMapper);
        }
    }

    @Override
    public HBaseRowData getRow(String tableName, GetRowParam getRowParam) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRow(tableName, getRowParam);
        }
    }

    @Override
    public <T> List<T> getWithMultiVersions(GetRowParam getRowParam, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getWithMultiVersions(getRowParam, clazz);
        }
    }

    @Override
    public <T> List<T> getWithMultiVersions(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getWithMultiVersions(tableName, getRowParam, rowMapper);
        }
    }

    @Override
    public HBaseRowDataWithMultiVersions getWithMultiVersions(String tableName, GetRowParam getRowParam) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getWithMultiVersions(tableName, getRowParam);
        }
    }

    @Override
    public <T> List<T> getRows(GetRowsParam getRowsParam, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRows(getRowsParam, clazz);
        }
    }

    @Override
    public <T> List<T> getRows(String tableName, GetRowsParam getRowsParams, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRows(tableName, getRowsParams, rowMapper);
        }
    }

    @Override
    public List<HBaseRowData> getRows(String tableName, GetRowsParam getRowsParam) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRows(tableName, getRowsParam);
        }
    }


    @Override
    public <T> List<T> scan(ScanParams scanQueryParams, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.scan(scanQueryParams, clazz);
        }
    }

    @Override
    public <T> List<T> scan(String tableName, ScanParams scanQueryParams, RowMapper<T> rowMapper) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.scan(tableName, scanQueryParams, rowMapper);
        }
    }

    @Override
    public List<HBaseRowData> scan(String tableName, ScanParams scanQueryParams) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.scan(tableName, scanQueryParams);
        }
    }

    @Override
    public List<HBaseRowDataWithMultiVersions> scanWithMultiVersions(String tableName, ScanParams scanParams) {
        return null;
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

package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseColData;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;

import java.io.Closeable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author leojie 2020/12/27 2:48 下午
 */
public class HBaseThrift implements Closeable, IHBaseThriftOperations {
    protected HBaseThriftClient hBaseThriftClient;
    protected HBaseThriftPoolAbstract dataSource = null;

    public HBaseThrift() {
        hBaseThriftClient = new HBaseThriftClient();
    }

    public HBaseThrift(final String host) {
        hBaseThriftClient = new HBaseThriftClient(host);
    }

    public HBaseThrift(final String host, final int port) {
        hBaseThriftClient = new HBaseThriftClient(host, port);
    }

    public HBaseThrift(final String host, final int port, final int timeout) {
        this(host, port, timeout, timeout);
    }

    public HBaseThrift(final String host, final int port, final int connectionTimeout, final int socketTimeout) {
        hBaseThriftClient = new HBaseThriftClient(host, port, connectionTimeout, socketTimeout);
    }

    public HBaseThrift(final IHBaseThriftTSocket thriftTSocketFactory) {
        hBaseThriftClient = new HBaseThriftClient(thriftTSocketFactory);
    }

    @Override
    public void save(String tableName, String rowKey, Map<String, Object> data) {
        hBaseThriftClient.save(tableName, rowKey, data);
    }

    @Override
    public <T> T save(T t) {
        return hBaseThriftClient.save(t);
    }

    @Override
    public int saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        return hBaseThriftClient.saveBatch(tableName, data);
    }

    @Override
    public <T> int saveBatch(List<T> list) {
        return hBaseThriftClient.saveBatch(list);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, Class<T> clazz) {
        return hBaseThriftClient.getRow(rowKey, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, Class<T> clazz) {
        return hBaseThriftClient.getRow(rowKey, familyName, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        return hBaseThriftClient.getRow(rowKey, familyName, qualifiers, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, RowMapper<T> rowMapper) {
        return hBaseThriftClient.getRow(tableName, rowKey, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper) {
        return hBaseThriftClient.getRow(tableName, rowKey, familyName, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return hBaseThriftClient.getRow(tableName, rowKey, familyName, qualifiers, rowMapper);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, boolean withTimestamp) {
        return hBaseThriftClient.getRowToMap(tableName, rowKey, withTimestamp);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, boolean withTimestamp) {
        return hBaseThriftClient.getRowToMap(tableName, rowKey, familyName, withTimestamp);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, List<String> qualifiers, boolean withTimestamp) {
        return hBaseThriftClient.getRowToMap(tableName, rowKey, familyName, qualifiers, withTimestamp);
    }

    @Override
    public Map<String, List<HBaseColData>> getRowToMapWithMultiVersions(String tableName, String rowKey, String familyName, List<String> qualifiers, int version) {
        return null;
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, Class<T> clazz) {
        return hBaseThriftClient.getRows(rowKeys, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, Class<T> clazz) {
        return hBaseThriftClient.getRows(rowKeys, familyName, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, List<String> qualifiers, Class<T> clazz) {
        return hBaseThriftClient.getRows(rowKeys, familyName, qualifiers, clazz);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, RowMapper<T> rowMapper) {
        return hBaseThriftClient.getRows(tableName, rowKeys, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, RowMapper<T> rowMapper) {
        return hBaseThriftClient.getRows(tableName, rowKeys, familyName, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return hBaseThriftClient.getRows(tableName, rowKeys, familyName, qualifiers, rowMapper);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, boolean withTimestamp) {
        return hBaseThriftClient.getRowsToMap(tableName, rowKeys, withTimestamp);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, boolean withTimestamp) {
        return hBaseThriftClient.getRowsToMap(tableName, rowKeys, familyName, withTimestamp);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, boolean withTimestamp) {
        return hBaseThriftClient.getRowsToMap(tableName, rowKeys, familyName, qualifiers, withTimestamp);
    }

    @Override
    public <T> List<T> scan(ScanParams scanQueryParams, Class<T> clazz) {
        return hBaseThriftClient.scan(scanQueryParams, clazz);
    }

    @Override
    public <T> List<T> scan(String tableName, ScanParams scanQueryParams, RowMapper<T> rowMapper) {
        return hBaseThriftClient.scan(tableName, scanQueryParams, rowMapper);
    }

    @Override
    public List<Map<String, Map<String, String>>> scan(String tableName, ScanParams scanQueryParams) {
        return hBaseThriftClient.scan(tableName, scanQueryParams);
    }

    @Override
    public <T> void delete(T t) {
        hBaseThriftClient.delete(t);
    }

    @Override
    public void delete(String tableName, String rowKey) {
        hBaseThriftClient.delete(tableName, rowKey);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName) {
        hBaseThriftClient.delete(tableName, rowKey, familyName);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        hBaseThriftClient.delete(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, String... qualifiers) {
        hBaseThriftClient.delete(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public <T> void deleteBatch(List<T> list) {
        hBaseThriftClient.deleteBatch(list);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys) {
        hBaseThriftClient.deleteBatch(tableName, rowKeys, null);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName) {
        hBaseThriftClient.deleteBatch(tableName, rowKeys, familyName);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        hBaseThriftClient.deleteBatch(tableName, rowKeys, familyName, qualifiers);
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers) {
        hBaseThriftClient.deleteBatch(tableName, rowKeys, familyName, qualifiers);
    }

    public void connect() {
        hBaseThriftClient.connect();
    }

    public boolean isConnected() {
        return hBaseThriftClient.isConnected();
    }

    public void disconnect() {
        hBaseThriftClient.disconnect();
    }

    public boolean ping() {
        try {
            return hBaseThriftClient.ping();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() {
        if (dataSource != null) {
            HBaseThriftPoolAbstract pool = this.dataSource;
            this.dataSource = null;
            if (hBaseThriftClient.isBroken()) {
                pool.returnBrokenResource(this);
            } else {
                pool.returnResource(this);
            }
        } else {
            hBaseThriftClient.close();
        }
    }

    public void setDataSource(HBaseThriftPoolAbstract poolAbstract) {
        this.dataSource = poolAbstract;
    }
}


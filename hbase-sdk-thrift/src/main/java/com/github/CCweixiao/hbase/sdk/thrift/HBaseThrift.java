package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowsParam;
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
    public <T> void save(T t) {
        hBaseThriftClient.save(t);
    }

    @Override
    public void saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        hBaseThriftClient.saveBatch(tableName, data);
    }

    @Override
    public <T> void saveBatch(List<T> list) {
        hBaseThriftClient.saveBatch(list);
    }

    @Override
    public <T> Optional<T> getRow(GetRowParam getRowParam, Class<T> clazz) {
        return hBaseThriftClient.getRow(getRowParam, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper) {
        return hBaseThriftClient.getRow(tableName, getRowParam, rowMapper);
    }

    @Override
    public HBaseRowData getRow(String tableName, GetRowParam getRowParam) {
        return hBaseThriftClient.getRow(tableName, getRowParam);
    }

    @Override
    public <T> List<T> getWithMultiVersions(GetRowParam getRowParam, Class<T> clazz) {
        return hBaseThriftClient.getWithMultiVersions(getRowParam, clazz);
    }

    @Override
    public <T> List<T> getWithMultiVersions(String tableName, GetRowParam getRowParam, RowMapper<T> rowMapper) {
        return hBaseThriftClient.getWithMultiVersions(tableName, getRowParam, rowMapper);
    }

    @Override
    public HBaseRowDataWithMultiVersions getWithMultiVersions(String tableName, GetRowParam getRowParam) {
        return hBaseThriftClient.getWithMultiVersions(tableName, getRowParam);
    }

    @Override
    public <T> List<T> getRows(GetRowsParam getRowsParam, Class<T> clazz) {
        return hBaseThriftClient.getRows(getRowsParam, clazz);
    }

    @Override
    public <T> List<T> getRows(String tableName, GetRowsParam getRowsParams, RowMapper<T> rowMapper) {
        return hBaseThriftClient.getRows(tableName, getRowsParams, rowMapper);
    }

    @Override
    public List<HBaseRowData> getRows(String tableName, GetRowsParam getRowsParam) {
        return hBaseThriftClient.getRows(tableName, getRowsParam);
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
    public List<HBaseRowData> scan(String tableName, ScanParams scanQueryParams) {
        return hBaseThriftClient.scan(tableName, scanQueryParams);
    }

    @Override
    public List<HBaseRowDataWithMultiVersions> scanWithMultiVersions(String tableName, ScanParams scanParams) {
        return null;
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


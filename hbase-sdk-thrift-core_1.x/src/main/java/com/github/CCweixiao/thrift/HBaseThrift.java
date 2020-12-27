package com.github.CCweixiao.thrift;

import com.github.CCweixiao.HBaseThriftOperations;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/12/27 2:48 下午
 */
public class HBaseThrift implements Closeable, HBaseThriftOperations {
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

    public HBaseThrift(final HBaseThriftTSocketFactory thriftTSocketFactory) {
        hBaseThriftClient = new HBaseThriftClient(thriftTSocketFactory);
    }


    @Override
    public void save(String tableName, String rowKey, Map<String, String> data) {
        hBaseThriftClient.save(tableName, rowKey, data);
    }

    @Override
    public void saveBatch(String tableName, Map<String, Map<String, String>> data) {
        hBaseThriftClient.saveBatch(tableName, data);
    }

    @Override
    public <T> T save(T t) throws Exception {
        return hBaseThriftClient.save(t);
    }

    @Override
    public <T> T saveBatch(List<T> lst) throws Exception {
        return hBaseThriftClient.saveBatch(lst);
    }

    @Override
    public <T> T getByRowKey(String rowKey, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getByRowKeyWithFamily(String rowKey, String familyName, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getByRowKeyWithFamilyAndQualifiers(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        return null;
    }

    @Override
    public Map<String, String> getByRowKeyToMap(String tableName, String rowKey) {
        return hBaseThriftClient.getByRowKeyToMap(tableName, rowKey);
    }

    @Override
    public Map<String, String> getByRowKeyWithFamilyToMap(String tableName, String rowKey, String familyName) {
        return hBaseThriftClient.getByRowKeyWithFamilyToMap(tableName, rowKey, familyName);
    }

    @Override
    public Map<String, String> getByRowKeyWithFamilyAndQualifiersToMap(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        return hBaseThriftClient.getByRowKeyWithFamilyAndQualifiersToMap(tableName, rowKey, familyName, qualifiers);
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysToMap(String tableName, List<String> rowKeyList) {
        return hBaseThriftClient.getRowsByRowKeysToMap(tableName, rowKeyList);
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysWithFamilyToMap(String tableName, List<String> rowKeyList, String familyName) {
        return hBaseThriftClient.getRowsByRowKeysWithFamilyToMap(tableName, rowKeyList, familyName);

    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysWithFamilyAndQualifiersToMap(String tableName, List<String> rowKeyList, String familyName, List<String> qualifiers) {
        return hBaseThriftClient.getRowsByRowKeysWithFamilyAndQualifiersToMap(tableName, rowKeyList, familyName, qualifiers);
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

    @Override
    public List<String> getTableNames() {
        return hBaseThriftClient.getTableNames();
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
            hBaseThriftClient.ping();
            return true;
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


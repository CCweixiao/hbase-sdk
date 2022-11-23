package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.IHBaseThriftOperations;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

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
    public Map<String, String> getByRowKeyWithFamilyAndQualifiersToMap(String tableName, String rowKey, String familyName, String... qualifiers) {
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
    public Map<String, Map<String, String>> getRowsByRowKeysWithFamilyAndQualifiersToMap(String tableName, List<String> rowKeyList, String familyName, String... qualifiers) {
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
    public List<Map<String, Map<String, String>>> findAllRowToMapList(String tableName, int limit) {
        return hBaseThriftClient.findAllRowToMapList(tableName, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithFamilyToMapList(String tableName, String familyName, int limit) {
        return hBaseThriftClient.findAllRowWithFamilyToMapList(tableName, familyName, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithFamilyAndQualifiersToMapList(String tableName, String familyName, List<String> qualifiers, int limit) {
        return hBaseThriftClient.findAllRowWithFamilyAndQualifiersToMapList(tableName, familyName, qualifiers, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartRowToMapList(String tableName, String startRow, int limit) {
        return hBaseThriftClient.findAllRowWithStartRowToMapList(tableName, startRow, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartRowAndFamilyToMapList(String tableName, String startRow, String familyName, int limit) {
        return hBaseThriftClient.findAllRowWithStartRowAndFamilyToMapList(tableName, startRow, familyName, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartRowAndFamilyAndQualifiersToMapList(String tableName, String startRow, String familyName, List<String> qualifiers, int limit) {
        return hBaseThriftClient.findAllRowWithStartRowAndFamilyAndQualifiersToMapList(tableName, startRow, familyName, qualifiers, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartAndStopRowToMapList(String tableName, String startRow, String stopRow, int limit) {
        return hBaseThriftClient.findAllRowWithStartAndStopRowToMapList(tableName, startRow, stopRow, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartAndStopRowAndFamilyToMapList(String tableName, String startRow, String stopRow, String familyName, int limit) {
        return hBaseThriftClient.findAllRowWithStartAndStopRowAndFamilyToMapList(tableName, startRow, stopRow, familyName, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartAndStopRowAndFamilyAndQualifiersToMapList(String tableName, String startRow, String stopRow, String familyName, List<String> qualifiers, int limit) {
        return hBaseThriftClient.findAllRowWithStartAndStopRowAndFamilyAndQualifiersToMapList(tableName, startRow, stopRow, familyName, qualifiers, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithPrefixToMapList(String tableName, String rowPrefix, int limit) {
        return hBaseThriftClient.findAllRowWithPrefixToMapList(tableName, rowPrefix, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithPrefixAndFamilyToMapList(String tableName, String rowPrefix, String familyName, int limit) {
        return hBaseThriftClient.findAllRowWithPrefixAndFamilyToMapList(tableName, rowPrefix, familyName, limit);
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithPrefixAndFamilyAndQualifiersToMapList(String tableName, String rowPrefix, String familyName, List<String> qualifiers, int limit) {
        return hBaseThriftClient.findAllRowWithPrefixAndFamilyAndQualifiersToMapList(tableName, rowPrefix, familyName, qualifiers, limit);
    }


    @Override
    public List<Map<String, Map<String, String>>> scan(String tableName, String startRow, String stopRow, String rowPrefix, String familyName, List<String> qualifiers, String filterStr, Long timestamp, Integer batchSize, Integer scanBatching, boolean reverse, Integer limit) {
        return hBaseThriftClient.scan(tableName, startRow, stopRow, rowPrefix, familyName, qualifiers, filterStr, timestamp, batchSize, scanBatching, reverse, limit);
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

    @Override
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


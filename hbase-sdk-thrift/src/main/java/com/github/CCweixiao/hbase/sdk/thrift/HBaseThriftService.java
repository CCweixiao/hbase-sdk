package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.HBaseThriftOperations;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/12/27 11:41 下午
 */
public class HBaseThriftService implements HBaseThriftOperations {

    private final HBaseThriftPool pool;


    public HBaseThriftService(String host, int port) {
        HBaseThriftPoolConfig config = new HBaseThriftPoolConfig();
        pool = new HBaseThriftPool(config, host, port);
    }

    public HBaseThriftService(String host, int port, int poolSize) {
        HBaseThriftPoolConfig config = new HBaseThriftPoolConfig();
        config.setMaxTotal(poolSize);
        config.setMaxIdle(poolSize);
        pool = new HBaseThriftPool(config, host, port);
    }

    public HBaseThriftService(String host, int port, HBaseThriftPoolConfig config){
        pool = new HBaseThriftPool(config, host, port);
    }

    /**
     * Clear active connection objects actively in connection pool if you need.
     */
    public void clearThriftPool() {
        pool.clearInternalPool();
    }

    @Override
    public void save(String tableName, String rowKey, Map<String, String> data) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.save(tableName, rowKey, data);
        }
    }

    @Override
    public void saveBatch(String tableName, Map<String, Map<String, String>> data) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            hBaseThrift.saveBatch(tableName, data);
        }
    }

    @Override
    public <T> T save(T t) throws Exception {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.save(t);
        }
    }

    @Override
    public <T> T saveBatch(List<T> lst) throws Exception {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.saveBatch(lst);
        }
    }

    @Override
    public <T> T getByRowKey(String rowKey, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getByRowKey(rowKey, clazz);
        }
    }

    @Override
    public <T> T getByRowKeyWithFamily(String rowKey, String familyName, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getByRowKeyWithFamily(rowKey, familyName, clazz);
        }
    }

    @Override
    public <T> T getByRowKeyWithFamilyAndQualifiers(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getByRowKeyWithFamilyAndQualifiers(rowKey, familyName, qualifiers, clazz);
        }
    }

    @Override
    public Map<String, String> getByRowKeyToMap(String tableName, String rowKey) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getByRowKeyToMap(tableName, rowKey);
        }
    }

    @Override
    public Map<String, String> getByRowKeyWithFamilyToMap(String tableName, String rowKey, String familyName) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getByRowKeyWithFamilyToMap(tableName, rowKey, familyName);
        }
    }

    @Override
    public Map<String, String> getByRowKeyWithFamilyAndQualifiersToMap(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getByRowKeyWithFamilyAndQualifiersToMap(tableName, rowKey, familyName, qualifiers);
        }
    }

    @Override
    public Map<String, String> getByRowKeyWithFamilyAndQualifiersToMap(String tableName, String rowKey, String familyName, String... qualifiers) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getByRowKeyWithFamilyAndQualifiersToMap(tableName, rowKey, familyName, qualifiers);
        }
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysToMap(String tableName, List<String> rowKeyList) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowsByRowKeysToMap(tableName, rowKeyList);
        }
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysWithFamilyToMap(String tableName, List<String> rowKeyList, String familyName) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowsByRowKeysWithFamilyToMap(tableName, rowKeyList, familyName);
        }
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysWithFamilyAndQualifiersToMap(String tableName, List<String> rowKeyList, String familyName, List<String> qualifiers) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowsByRowKeysWithFamilyAndQualifiersToMap(tableName, rowKeyList, familyName, qualifiers);
        }
    }

    @Override
    public Map<String, Map<String, String>> getRowsByRowKeysWithFamilyAndQualifiersToMap(String tableName, List<String> rowKeyList, String familyName, String... qualifiers) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getRowsByRowKeysWithFamilyAndQualifiersToMap(tableName, rowKeyList, familyName, qualifiers);
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

    @Override
    public List<Map<String, Map<String, String>>> findAllRowToMapList(String tableName, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowToMapList(tableName, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithFamilyToMapList(String tableName, String familyName, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithFamilyToMapList(tableName, familyName, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithFamilyAndQualifiersToMapList(String tableName, String familyName, List<String> qualifiers, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithFamilyAndQualifiersToMapList(tableName, familyName, qualifiers, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartRowToMapList(String tableName, String startRow, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithStartRowToMapList(tableName, startRow, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartRowAndFamilyToMapList(String tableName, String startRow, String familyName, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithStartRowAndFamilyToMapList(tableName, startRow, familyName, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartRowAndFamilyAndQualifiersToMapList(String tableName, String startRow, String familyName, List<String> qualifiers, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithStartRowAndFamilyAndQualifiersToMapList(tableName, startRow, familyName, qualifiers, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartAndStopRowToMapList(String tableName, String startRow, String stopRow, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithStartAndStopRowToMapList(tableName, startRow, stopRow, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartAndStopRowAndFamilyToMapList(String tableName, String startRow, String stopRow, String familyName, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithStartAndStopRowAndFamilyToMapList(tableName, startRow, stopRow, familyName, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithStartAndStopRowAndFamilyAndQualifiersToMapList(String tableName, String startRow, String stopRow, String familyName, List<String> qualifiers, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithStartAndStopRowAndFamilyAndQualifiersToMapList(tableName, startRow, stopRow, familyName, qualifiers, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithPrefixToMapList(String tableName, String rowPrefix, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithPrefixToMapList(tableName, rowPrefix, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithPrefixAndFamilyToMapList(String tableName, String rowPrefix, String familyName, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithPrefixAndFamilyToMapList(tableName, rowPrefix, familyName, limit);
        }
    }

    @Override
    public List<Map<String, Map<String, String>>> findAllRowWithPrefixAndFamilyAndQualifiersToMapList(String tableName, String rowPrefix, String familyName, List<String> qualifiers, int limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.findAllRowWithPrefixAndFamilyAndQualifiersToMapList(tableName, rowPrefix, familyName, qualifiers, limit);
        }
    }


    @Override
    public List<Map<String, Map<String, String>>> scan(String tableName, String startRow, String stopRow, String rowPrefix, String familyName, List<String> qualifiers, String filterStr, Long timestamp, Integer batchSize, Integer scanBatching, boolean reverse, Integer limit) {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.scan(tableName, startRow, stopRow, rowPrefix, familyName, qualifiers, filterStr, timestamp, batchSize, scanBatching, reverse, limit);
        }
    }

    @Override
    public List<String> getTableNames() {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getTableNames();
        }
    }
}

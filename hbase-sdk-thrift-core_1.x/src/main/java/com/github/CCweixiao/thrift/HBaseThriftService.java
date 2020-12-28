package com.github.CCweixiao.thrift;

import com.github.CCweixiao.HBaseThriftOperations;

import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/12/27 11:41 下午
 */
public class HBaseThriftService implements HBaseThriftOperations {
    private final HBaseThriftPool pool;


    private HBaseThriftService(String host, int port) {
        HBaseThriftPoolConfig config = new HBaseThriftPoolConfig();
        pool = new HBaseThriftPool(config, host, port);
    }

    private static class HBaseThriftServiceHolder {
        private static HBaseThriftService instance(String host, int port) {
            return new HBaseThriftService(host, port);
        }
    }

    public static HBaseThriftService getInstance(String host, int port) {
        return HBaseThriftServiceHolder.instance(host, port);
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
    public List<String> getTableNames() {
        try (HBaseThrift hBaseThrift = pool.getResource()) {
            return hBaseThrift.getTableNames();
        }
    }
}

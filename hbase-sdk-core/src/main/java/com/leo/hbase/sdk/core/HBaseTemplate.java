package com.leo.hbase.sdk.core;

import com.leo.hbase.sdk.core.annotation.HBaseRowKey;
import com.leo.hbase.sdk.core.exception.HBaseOperationsException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import com.leo.hbase.sdk.core.util.HBytesUtil;
import com.leo.hbase.sdk.core.util.ReflectUtil;
import com.leo.hbase.sdk.core.util.StrUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


/**
 * @author leo.jie (weixiao.me@aliyun.com)
 */
public class HBaseTemplate extends AbstractHBaseTemplate implements HBaseTableOperations {
    public HBaseTemplate(Configuration configuration) {
        super(configuration);
    }

    public HBaseTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public HBaseTemplate(Properties properties) {
        super(properties);
    }


    @Override
    public void save(String tableName, Mutation mutation) {
        this.execute(tableName, mutator -> {
            mutator.mutate(mutation);
        });
    }

    @Override
    public void saveBatch(String tableName, List<Mutation> mutations) {
        this.execute(tableName, mutator -> {
            mutator.mutate(mutations);
        });
    }


    @Override
    public void save(String tableName, String rowKey, Map<String, Object> data) {
        this.save(tableName, put(rowKey, data));
    }

    @Override
    public void saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        List<Mutation> puts = new ArrayList<>(data.size());
        data.forEach((row, d) -> puts.add(put(row, d)));
        this.saveBatch(tableName, puts);
    }


    @Override
    public <T> T save(T t) throws Exception {
        final Class<?> clazz = t.getClass();
        final String tableName = ReflectUtil.getHBaseTableName(clazz);
        String rowKeyK = createRandomRowKeyName();
        final String uniqueFamily = ReflectUtil.getUniqueTableFamily(clazz);
        Field[] fields = ReflectUtil.getAllFields(clazz);
        Map<String, Method> methodMap = ReflectUtil.getAllMethodsMap(clazz);
        if (fields == null || fields.length == 0) {
            throw new HBaseOperationsException("please assign values for " + clazz.getSimpleName() + ".");
        }
        if (methodMap == null || methodMap.isEmpty()) {
            throw new HBaseOperationsException("please assign getter and setter for " + clazz.getSimpleName() + ".");
        }
        Map<String, Object> data = new HashMap<>(fields.length);
        for (Field field : fields) {
            if (ReflectUtil.isNotGeneralProperty(field)) {
                continue;
            }
            Method getMethod = methodMap.getOrDefault(ReflectUtil.getGetterName(field), null);
            if (ReflectUtil.isNotGeneralGetterMethod(getMethod)) {
                throw new HBaseOperationsException("please assign a standard getter method for " + clazz.getSimpleName() + ".");
            }
            Object fieldValue = getMethod.invoke(t);
            if (fieldValue == null) {
                continue;
            }
            if (field.isAnnotationPresent(HBaseRowKey.class)) {
                HBaseRowKey rowKey = field.getAnnotation(HBaseRowKey.class);
                if (rowKey.rowKey()) {
                    data.put(rowKeyK, fieldValue);
                }
            } else {
                String fieldName = ReflectUtil.getHBaseColumnName(uniqueFamily, field);
                data.put(fieldName, fieldValue);
            }
        }
        if (!data.containsKey(rowKeyK) || data.get(rowKeyK) == null) {
            throw new HBaseOperationsException("please provided a rowKey.");
        }
        Put put = new Put(Bytes.toBytes(String.valueOf(data.get(rowKeyK))));
        data.remove(rowKeyK);
        data.forEach((fieldName, fieldValue) -> put.addColumn(Bytes.toBytes(fieldName.substring(0, fieldName.lastIndexOf(":"))),
                Bytes.toBytes(fieldName.substring(fieldName.lastIndexOf(":") + 1)), HBytesUtil.toBytes(fieldValue)));
        this.save(tableName, put);
        return t;
    }

    @Override
    public <T> T saveBatch(List<T> lst) throws Exception {
        if (lst == null || lst.isEmpty()) {
            return null;
        }
        final Class<?> clazz0 = lst.get(0).getClass();
        final String tableName = ReflectUtil.getHBaseTableName(clazz0);

        final String uniqueFamily = ReflectUtil.getUniqueTableFamily(clazz0);
        Field[] fields = ReflectUtil.getAllFields(clazz0);
        Map<String, Method> methodMap = ReflectUtil.getAllMethodsMap(clazz0);

        if (fields == null || fields.length == 0) {
            throw new HBaseOperationsException("please assign values for " + clazz0.getSimpleName() + ".");
        }
        if (methodMap == null || methodMap.isEmpty()) {
            throw new HBaseOperationsException("please assign getter and setter for " + clazz0.getSimpleName() + ".");
        }
        List<Mutation> putList = new ArrayList<>(lst.size());

        for (T t : lst) {
            String rowKeyK = createRandomRowKeyName();
            Map<String, Object> data = new HashMap<>(fields.length);
            for (Field field : fields) {
                if (ReflectUtil.isNotGeneralProperty(field)) {
                    continue;
                }
                Method getMethod = methodMap.getOrDefault(ReflectUtil.getGetterName(field), null);
                if (ReflectUtil.isNotGeneralGetterMethod(getMethod)) {
                    throw new HBaseOperationsException("please assign a standard getter method for " + field.getName() + ".");
                }
                Object fieldValue = getMethod.invoke(t);
                if (fieldValue == null) {
                    continue;
                }
                if (field.isAnnotationPresent(HBaseRowKey.class)) {
                    HBaseRowKey rowKey = field.getAnnotation(HBaseRowKey.class);
                    if (rowKey.rowKey()) {
                        data.put(rowKeyK, fieldValue);
                    }
                } else {
                    String fieldName = ReflectUtil.getHBaseColumnName(uniqueFamily, field);
                    data.put(fieldName, fieldValue);
                }
            }
            if (!data.containsKey(rowKeyK) || data.get(rowKeyK) == null) {
                throw new HBaseOperationsException("please provided a rowKey.");
            }
            Put put = new Put(HBytesUtil.toBytes(data.get(rowKeyK)));
            data.remove(rowKeyK);
            data.forEach((fieldName, fieldValue) -> put.addColumn(Bytes.toBytes(fieldName.substring(0, fieldName.lastIndexOf(":"))),
                    Bytes.toBytes(fieldName.substring(fieldName.lastIndexOf(":") + 1)), HBytesUtil.toBytes(fieldValue)));
            putList.add(put);
        }
        this.saveBatch(tableName, putList);
        return lst.get(0);
    }

    @Override
    public <T> T getByRowKey(String rowName, Class<T> clazz) {
        return this.getByRowKeyWithFamilyAndQualifiers(rowName, null, null, clazz);
    }


    @Override
    public <T> T getByRowKeyWithFamily(String rowName, String familyName, Class<T> clazz) {
        return this.getByRowKeyWithFamilyAndQualifiers(rowName, familyName, null, clazz);
    }

    @Override
    public <T> T getByRowKeyWithFamilyAndQualifiers(String rowName, String familyName, List<String> qualifiers, Class<T> clazz) {
        String tableName = ReflectUtil.getHBaseTableName(clazz);
        return this.execute(tableName, table -> {
            Get get = get(rowName, familyName, qualifiers);
            Result result = table.get(get);
            return mapperRowToT(result, clazz);
        });
    }

    @Override
    public Map<String, Object> getByRowKey(String tableName, String rowName) {
        return this.execute(tableName, table -> {
            Get get = new Get(Bytes.toBytes(rowName));
            Result result = table.get(get);
            return mapperRowToMap(result);
        });
    }

    @Override
    public Map<String, Object> getByRowKeyWithFamily(String tableName, String rowName, String familyName) {
        return this.getByRowKeyWithFamilyAndQualifiers(tableName, rowName, familyName, null);
    }

    @Override
    public Map<String, Object> getByRowKeyWithFamilyAndQualifiers(String tableName, String rowName, String familyName, List<String> qualifiers) {
        return this.execute(tableName, table -> {
            Get get = get(rowName, familyName, qualifiers);
            Result result = table.get(get);
            return mapperRowToMap(result);
        });
    }

    @Override
    public <T> T get(String tableName, String rowName, RowMapper<T> rowMapper) {
        return this.get(tableName, rowName, null, null, rowMapper);
    }

    @Override
    public <T> T get(String tableName, String rowName, String familyName, RowMapper<T> rowMapper) {
        return this.get(tableName, rowName, familyName, null, rowMapper);
    }

    @Override
    public <T> T get(String tableName, String rowName, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            Get get = get(rowName, familyName, qualifiers);
            Result result = table.get(get);
            return rowMapper.mapRow(result, 0);
        });
    }

    @Override
    public <T> List<T> findAll(int limit, Class<T> clazz) {
        return findByFamilyAndQualifiers(null, null, limit, clazz);
    }

    @Override
    public <T> List<T> findByFamily(String family, int limit, Class<T> clazz) {
        return findByFamilyAndQualifiers(family, null, limit, clazz);
    }

    @Override
    public <T> List<T> findByFamilyAndQualifiers(String family, List<String> qualifiers, int limit, Class<T> clazz) {
        String tableName = ReflectUtil.getHBaseTableName(clazz);
        return find(tableName, scan(family, qualifiers), limit, clazz);
    }

    @Override
    public <T> List<T> find(String tableName, Scan scan, int limit, Class<T> clazz) {
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(scan(scan, limit))) {
                List<T> rs = new ArrayList<>();
                for (Result result : scanner) {
                    rs.add(mapperRowToT(result, clazz));
                }
                return rs;
            }
        });
    }

    @Override
    public <T> List<T> findAll(String tableName, int limit, RowMapper<T> rowMapper) {
        return findByFamilyAndQualifiers(tableName, null, null, limit, rowMapper);
    }

    @Override
    public <T> List<T> findByFamily(String tableName, String family, int limit, RowMapper<T> rowMapper) {
        return findByFamilyAndQualifiers(tableName, family, null, limit, rowMapper);
    }

    @Override
    public <T> List<T> findByFamilyAndQualifiers(String tableName, String family, List<String> qualifiers, int limit, RowMapper<T> rowMapper) {
        return this.find(tableName, scan(family, qualifiers), limit, rowMapper);
    }

    @Override
    public <T> List<T> find(String tableName, Scan scan, int limit, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(scan(scan, limit))) {
                List<T> rs = new ArrayList<>();
                int rowNum = 0;
                for (Result result : scanner) {
                    rs.add(rowMapper.mapRow(result, rowNum++));
                }
                return rs;
            }
        });
    }

    @Override
    public <T> List<T> findByPrefix(String prefix, int limit, Class<T> clazz) {
        return this.findByPrefix(prefix, null, null, limit, clazz);
    }

    @Override
    public <T> List<T> findByPrefix(String prefix, String family, int limit, Class<T> clazz) {
        return this.findByPrefix(prefix, family, null, limit, clazz);
    }

    @Override
    public <T> List<T> findByPrefix(String prefix, String family, List<String> qualifiers, int limit, Class<T> clazz) {
        if (StrUtil.isBlank(prefix)) {
            throw new HBaseOperationsException("the prefix scan is not empty.");
        }
        Scan scan = scan(family, qualifiers);
        scan.setRowPrefixFilter(Bytes.toBytes(prefix));
        String tableName = ReflectUtil.getHBaseTableName(clazz);
        return find(tableName, scan, limit, clazz);
    }

    @Override
    public <T> List<T> findByPrefix(String tableName, String prefix, int limit, RowMapper<T> rowMapper) {
        return this.findByPrefix(tableName, prefix, null, null, limit, rowMapper);
    }

    @Override
    public <T> List<T> findByPrefix(String tableName, String prefix, String family, int limit, RowMapper<T> rowMapper) {
        return this.findByPrefix(tableName, prefix, family, null, limit, rowMapper);
    }

    @Override
    public <T> List<T> findByPrefix(String tableName, String prefix, String family, List<String> qualifiers, int limit, RowMapper<T> rowMapper) {
        if (StrUtil.isBlank(prefix)) {
            throw new HBaseOperationsException("the prefix scan is not empty.");
        }
        Scan scan = scan(family, qualifiers);
        scan.setRowPrefixFilter(Bytes.toBytes(prefix));
        return find(tableName, scan, limit, rowMapper);
    }

    private Get get(String rowName, String familyName, List<String> qualifiers) {
        Get get = new Get(Bytes.toBytes(rowName));
        if (StrUtil.isNotBlank(familyName)) {
            byte[] family = Bytes.toBytes(familyName);
            if (qualifiers != null && !qualifiers.isEmpty()) {
                qualifiers.forEach(qualifier -> get.addColumn(family, Bytes.toBytes(qualifier)));
            } else {
                get.addFamily(family);
            }
        }
        return get;
    }

    private Scan scan(String family, List<String> qualifiers) {
        Scan scan = new Scan();
        if (StrUtil.isNotBlank(family)) {
            if (qualifiers != null && !qualifiers.isEmpty()) {
                qualifiers.forEach(qualifier -> scan.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier)));
            } else {
                scan.addFamily(Bytes.toBytes(family));
            }
        }
        return scan;
    }

    private Scan scan(Scan scan, int limit) {
        int caching = scan.getCaching();
        // 如果caching未设置(默认是1)，将默认配置成5000
        if (caching == 1) {
            scan.setCaching(5000);
        }
        if (limit > 0) {
            scan.setLimit(limit);
        }
        return scan;
    }

    private Put put(String rowKey, Map<String, Object> data) {
        Put put = new Put(HBytesUtil.toBytes(rowKey));
        data.forEach((fieldName, fieldValue) -> put.addColumn(Bytes.toBytes(fieldName.substring(0, fieldName.lastIndexOf(":"))),
                Bytes.toBytes(fieldName.substring(fieldName.lastIndexOf(":") + 1)), HBytesUtil.toBytes(fieldValue)));
        return put;
    }
}

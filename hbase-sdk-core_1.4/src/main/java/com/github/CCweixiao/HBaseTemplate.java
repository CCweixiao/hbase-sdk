package com.github.CCweixiao;

import com.github.CCweixiao.annotation.HBaseRowKey;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.util.HBytesUtil;
import com.github.CCweixiao.util.ReflectUtil;
import com.github.CCweixiao.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author leo.jie (weixiao.me@aliyun.com)
 */
public class HBaseTemplate extends AbstractHBaseTemplate {
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
        data.forEach((row, d) -> {
            if(d != null && !d.isEmpty()){
                puts.add(put(row, d));
            }
        });
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
    public <T> T getByRowKey(String rowKey, Class<T> clazz) {
        return this.getByRowKeyWithFamilyAndQualifiers(rowKey, null, null, clazz);
    }


    @Override
    public <T> T getByRowKeyWithFamily(String rowKey, String familyName, Class<T> clazz) {
        return this.getByRowKeyWithFamilyAndQualifiers(rowKey, familyName, null, clazz);
    }

    @Override
    public <T> T getByRowKeyWithFamilyAndQualifiers(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        String tableName = ReflectUtil.getHBaseTableName(clazz);
        return this.execute(tableName, table -> {
            Get get = get(rowKey, familyName, qualifiers);
            Result result = table.get(get);
            return mapperRowToT(result, clazz);
        });
    }

    @Override
    public Map<String, Object> getByRowKey(String tableName, String rowKey) {
        return this.getByRowKeyWithFamilyAndQualifiers(tableName, rowKey, "", null);
    }

    @Override
    public Map<String, Object> getByRowKeyWithFamily(String tableName, String rowKey, String familyName) {
        return this.getByRowKeyWithFamilyAndQualifiers(tableName, rowKey, familyName, null);
    }

    @Override
    public Map<String, Object> getByRowKeyWithFamilyAndQualifiers(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        return this.execute(tableName, table -> {
            Get get = get(rowKey, familyName, qualifiers);
            Result result = table.get(get);
            return mapperRowToMap(result);
        });
    }

    @Override
    public <T> T get(String tableName, String rowKey, RowMapper<T> rowMapper) {
        return this.get(tableName, rowKey, null, null, rowMapper);
    }

    @Override
    public <T> T get(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper) {
        return this.get(tableName, rowKey, familyName, null, rowMapper);
    }

    @Override
    public <T> T get(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            Get get = get(rowKey, familyName, qualifiers);
            Result result = table.get(get);
            return rowMapper.mapRow(result, 0);
        });
    }

    @Override
    public <T> List<T> findAll(int limit, Class<T> clazz) {
        return this.findByFamilyAndQualifiers(null, null, limit, clazz);
    }

    @Override
    public <T> List<T> findByFamily(String familyName, int limit, Class<T> clazz) {
        return this.findByFamilyAndQualifiers(familyName, null, limit, clazz);
    }

    @Override
    public <T> List<T> findByFamilyAndQualifiers(String familyName, List<String> qualifiers, int limit, Class<T> clazz) {
        String tableName = ReflectUtil.getHBaseTableName(clazz);
        return this.find(tableName, scan(familyName, qualifiers), limit, clazz);
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
        return this.findByFamilyAndQualifiers(tableName, null, null, limit, rowMapper);
    }

    @Override
    public <T> List<T> findByFamily(String tableName, String familyName, int limit, RowMapper<T> rowMapper) {
        return this.findByFamilyAndQualifiers(tableName, familyName, null, limit, rowMapper);
    }

    @Override
    public <T> List<T> findByFamilyAndQualifiers(String tableName, String familyName, List<String> qualifiers, int limit, RowMapper<T> rowMapper) {
        return this.find(tableName, scan(familyName, qualifiers), limit, rowMapper);
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
    public <T> List<T> findByPrefix(String prefix, String familyName, int limit, Class<T> clazz) {
        return this.findByPrefix(prefix, familyName, null, limit, clazz);
    }

    @Override
    public <T> List<T> findByPrefix(String prefix, String familyName, List<String> qualifiers, int limit, Class<T> clazz) {
        if (StrUtil.isBlank(prefix)) {
            throw new HBaseOperationsException("the query prefix of scan is not empty.");
        }
        Scan scan = scan(familyName, qualifiers);
        scan.setRowPrefixFilter(Bytes.toBytes(prefix));
        String tableName = ReflectUtil.getHBaseTableName(clazz);
        return this.find(tableName, scan, limit, clazz);
    }

    @Override
    public <T> List<T> findByPrefix(String tableName, String prefix, int limit, RowMapper<T> rowMapper) {
        return this.findByPrefix(tableName, prefix, null, null, limit, rowMapper);
    }

    @Override
    public <T> List<T> findByPrefix(String tableName, String prefix, String familyName, int limit, RowMapper<T> rowMapper) {
        return this.findByPrefix(tableName, prefix, familyName, null, limit, rowMapper);
    }

    @Override
    public <T> List<T> findByPrefix(String tableName, String prefix, String familyName, List<String> qualifiers, int limit, RowMapper<T> rowMapper) {
        if (StrUtil.isBlank(prefix)) {
            throw new HBaseOperationsException("the prefix scan is not empty.");
        }
        Scan scan = scan(familyName, qualifiers);
        scan.setRowPrefixFilter(Bytes.toBytes(prefix));
        return find(tableName, scan, limit, rowMapper);
    }

    @Override
    public Map<String, Object> getToMap(String tableName, String rowKey, String familyName, String qualifier) {
        Get get = get(rowKey, familyName, Collections.singletonList(qualifier));
        return this.execute(tableName, table -> {
            Result result = table.get(get);
            return getOneToResultMap(result);
        });
    }

    @Override
    public List<Map<String, Object>> getToListMap(String tableName, String rowKey) {
        return this.getToListMap(tableName, rowKey, null, null);
    }

    @Override
    public List<Map<String, Object>> getToListMap(String tableName, String rowKey, String familyName) {
        return this.getToListMap(tableName, rowKey, familyName, null);
    }

    @Override
    public List<Map<String, Object>> getToListMap(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        Get get = get(rowKey, familyName, qualifiers);
        return this.execute(tableName, table -> {
            Result result = table.get(get);
            return getToResultMap(result);
        });
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, Integer limit) {
        return this.findToListMap(tableName, null, null, null, null, limit);
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, Integer limit) {
        return this.findToListMap(tableName, familyName, null, null, null, limit);
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, Integer limit) {
        return this.findToListMap(tableName, familyName, qualifiers, null, null, limit);
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, String startKey, Integer limit) {
        return this.findToListMap(tableName, familyName, null, startKey, null, limit);
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, String startKey, Integer limit) {
        return this.findToListMap(tableName, familyName, qualifiers, startKey, null, limit);
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, String startKey, String endKey, Integer limit) {
        Scan scan = scan(familyName, qualifiers);
        if (StrUtil.isNotBlank(startKey)) {
            scan.withStartRow(Bytes.toBytes(startKey));
        }
        if (StrUtil.isNotBlank(endKey)) {
            scan.withStopRow(Bytes.toBytes(endKey));
        }
        return this.find(tableName, scan, limit, (result, rowNum) -> getToResultMap(result));
    }

    @Override
    public void delete(String tableName, String rowKey) {
        this.delete(tableName, rowKey, null, new ArrayList<>());
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName) {
        this.delete(tableName, rowKey, familyName, new ArrayList<>());
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, List<String> qualifiers) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("the table name is not empty.");
        }
        if (StrUtil.isBlank(rowKey)) {
            throw new HBaseOperationsException("the row key of the table will be deleted is not empty.");
        }
        Delete delete = delete(rowKey, familyName, qualifiers);
        this.execute(tableName, table -> {
            table.mutate(delete);
        });
    }

    @Override
    public void delete(String tableName, String rowKey, String familyName, String... qualifiers) {
        if (qualifiers == null || qualifiers.length == 0) {
            this.delete(tableName, rowKey, familyName);
        } else {
            this.delete(tableName, rowKey, familyName, Arrays.asList(qualifiers));
        }
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys) {
        this.deleteBatch(tableName, rowKeys, null, new ArrayList<>());
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName) {
        this.deleteBatch(tableName, rowKeys, familyName, new ArrayList<>());
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("the table name is not empty.");
        }
        if (rowKeys == null || rowKeys.isEmpty()) {
            throw new HBaseOperationsException("the row keys of the table will be deleted is not empty.");
        }
        List<Mutation> mutations = rowKeys.stream().map(rowKey -> delete(rowKey, familyName, qualifiers)).collect(Collectors.toList());

        this.execute(tableName, mutator -> {
            mutator.mutate(mutations);
        });

    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String familyName, String... qualifiers) {
        if (qualifiers == null || qualifiers.length == 0) {
            this.deleteBatch(tableName, rowKeys, familyName);
        } else {
            this.deleteBatch(tableName, rowKeys, familyName, Arrays.asList(qualifiers));
        }
    }

}

package com.github.CCweixiao.hbase.sdk.adapter_22;

import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseRowKey;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.adapter_22.util.HBytesUtil;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;
import com.github.CCweixiao.hbase.sdk.common.util.ReflectUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
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
    public void save(String tableName, String rowKey, Map<String, Object> data) {
        this.save(tableName, buildPutCondition(rowKey, data));
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
    public void saveBatch(String tableName, List<Mutation> mutations) {
        this.execute(tableName, mutator -> {
            mutator.mutate(mutations);
        });
    }

    @Override
    public int saveBatch(String tableName, Map<String, Map<String, Object>> data) {
        if (data == null || data.isEmpty()) {
            return 0;
        }
        List<Mutation> puts = new ArrayList<>(data.size());
        data.forEach((row, d) -> {
            if (d != null && !d.isEmpty()) {
                puts.add(buildPutCondition(row, d));
            }
        });
        this.saveBatch(tableName, puts);
        return puts.size();
    }

    @Override
    public <T> int saveBatch(List<T> list) throws Exception {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        final Class<?> clazz0 = list.get(0).getClass();
        final String tableName = ReflectUtil.getHBaseTableName(clazz0);

        final String uniqueFamily = ReflectUtil.getUniqueTableFamily(clazz0);
        Field[] fields = ReflectUtil.getAllFields(clazz0);
        Map<String, Method> methodMap = ReflectUtil.getAllMethodsMap(clazz0);

        if (fields == null || fields.length == 0) {
            throw new HBaseOperationsException("Please assign values for " + clazz0.getSimpleName() + ".");
        }
        if (methodMap == null || methodMap.isEmpty()) {
            throw new HBaseOperationsException("Please assign getter and setter for " + clazz0.getSimpleName() + ".");
        }
        List<Mutation> putList = new ArrayList<>(list.size());

        for (T t : list) {
            String rowKeyK = createRandomRowKeyName();
            Map<String, Object> data = new HashMap<>(fields.length);
            for (Field field : fields) {
                if (ReflectUtil.isNotGeneralProperty(field)) {
                    continue;
                }
                Method getMethod = methodMap.getOrDefault(ReflectUtil.getGetterName(field), null);
                if (ReflectUtil.isNotGeneralGetterMethod(getMethod)) {
                    throw new HBaseOperationsException("Please assign a standard getter method for " + field.getName() + ".");
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
        return list.size();
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, Class<T> clazz) {
        return this.getRow(rowKey, null, null, clazz);
    }


    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, Class<T> clazz) {
        return this.getRow(rowKey, familyName, null, clazz);
    }

    @Override
    public <T> Optional<T> getRow(String rowKey, String familyName, List<String> qualifiers, Class<T> clazz) {
        String tableName = ReflectUtil.getHBaseTableName(clazz);
        return this.execute(tableName, table -> {
            Get get = buildGetCondition(rowKey, familyName, qualifiers);
            Result result = checkGetResultIsNull(get, table);
            if (result == null) {
                return null;
            }
            return mapperRowToT(result, clazz);
        });
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, RowMapper<T> rowMapper) {
        return this.getRow(tableName, rowKey, null, null, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper) {
        return this.getRow(tableName, rowKey, familyName, null, rowMapper);
    }

    @Override
    public <T> Optional<T> getRow(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            Get get = buildGetCondition(rowKey, familyName, qualifiers);
            Result result = checkGetResultIsNull(get, table);
            if (result == null) {
                return null;
            }
            return rowMapper.mapRow(result, 0);
        });
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, boolean withTimestamp) {
        return this.getRowToMap(tableName, rowKey, null, null, withTimestamp);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, boolean withTimestamp) {
        return this.getRowToMap(tableName, rowKey, familyName, null, withTimestamp);
    }

    @Override
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName, List<String> qualifiers, boolean withTimestamp) {
        return this.execute(tableName, table -> {
            Get get = buildGetCondition(rowKey, familyName, qualifiers);
            Result result = checkGetResultIsNull(get, table);
            if (result == null) {
                return null;
            }
            return parseResultToMap(result, withTimestamp);
        }).orElse(new HashMap<>());
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, Class<T> clazz) {
        return getRows(rowKeys, null, null, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, Class<T> clazz) {
        return getRows(rowKeys, familyName, null, clazz);
    }

    @Override
    public <T> List<T> getRows(List<String> rowKeys, String familyName, List<String> qualifiers, Class<T> clazz) {
        String tableName = ReflectUtil.getHBaseTableName(clazz);
        return this.execute(tableName, table -> {
            List<Get> gets = buildBatchGetCondition(rowKeys, familyName, qualifiers);
            Result[] results = checkBatchGetResultIsNull(gets, table);
            if (results == null) {
                return null;
            }
            List<T> data = new ArrayList<>();
            for (Result result : results) {
                data.add(mapperRowToT(result, clazz));
            }
            return data;
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, RowMapper<T> rowMapper) {
        return getRows(tableName, rowKeys, null, null, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, RowMapper<T> rowMapper) {
        return getRows(tableName, rowKeys, familyName, null, rowMapper);
    }

    @Override
    public <T> List<T> getRows(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            List<Get> gets = buildBatchGetCondition(rowKeys, familyName, qualifiers);
            Result[] results = checkBatchGetResultIsNull(gets, table);
            if (results == null) {
                return null;
            }
            List<T> data = new ArrayList<>(results.length);
            for (Result result : results) {
                data.add(rowMapper.mapRow(result, 0));
            }
            return data;
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, boolean withTimestamp) {
        return getRowsToMap(tableName, rowKeys, null, null, withTimestamp);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, boolean withTimestamp) {
        return getRowsToMap(tableName, rowKeys, familyName, null, withTimestamp);
    }

    @Override
    public Map<String, Map<String, String>> getRowsToMap(String tableName, List<String> rowKeys, String familyName, List<String> qualifiers, boolean withTimestamp) {
        return this.execute(tableName, table -> {
            List<Get> gets = buildBatchGetCondition(rowKeys, familyName, qualifiers);
            Result[] results = checkBatchGetResultIsNull(gets, table);
            if (results == null) {
                return null;
            }
            Map<String, Map<String, String>> data = new HashMap<>(results.length);
            for (Result result : results) {
                data.put(Bytes.toString(result.getRow()), parseResultToMap(result, withTimestamp));
            }
            return data;
        }).orElse(new HashMap<>(0));
    }

    @Override
    public <T> List<T> scan(ScanQueryParamsBuilder scanQueryParams, Class<T> clazz) {
        String tableName = ReflectUtil.getHBaseTableName(clazz);
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(buildScanCondition(scanQueryParams))) {
                List<T> rs = new ArrayList<>();
                for (Result result : scanner) {
                    rs.add(mapperRowToT(result, clazz));
                }
                return rs;
            }
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public List<Map<String, Map<String, String>>> scan(String tableName, ScanQueryParamsBuilder scanQueryParams) {
        List<Map<String, Map<String, String>>> data = new ArrayList<>(4);
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(buildScanCondition(scanQueryParams))) {

                for (Result result : scanner) {
                    Map<String, Map<String, String>> tmpData = new HashMap<>(result.size());
                    tmpData.put(Bytes.toString(result.getRow()), mapperRowToMap(result));
                    data.add(tmpData);
                }
                return data;
            }
        }).orElse(new ArrayList<>(0));
    }

    @Override
    public <T> List<T> scan(String tableName, ScanQueryParamsBuilder scanQueryParams, RowMapper<T> rowMapper) {
        return this.execute(tableName, table -> {
            try (ResultScanner scanner = table.getScanner(buildScanCondition(scanQueryParams))) {
                List<T> rs = new ArrayList<>();
                int rowNum = 0;
                for (Result result : scanner) {
                    rs.add(rowMapper.mapRow(result, rowNum++));
                }
                return rs;
            }
        }).orElse(new ArrayList<>(0));
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
        Delete delete = buildDeleteCondition(rowKey, familyName, qualifiers);
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
        List<Mutation> mutations = rowKeys.stream().map(rowKey -> buildDeleteCondition(rowKey, familyName, qualifiers)).collect(Collectors.toList());

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

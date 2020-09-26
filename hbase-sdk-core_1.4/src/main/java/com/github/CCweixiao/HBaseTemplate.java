package com.github.CCweixiao;

import com.github.CCweixiao.annotation.HBaseRowKey;
import com.github.CCweixiao.exception.HBaseOperationsException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import com.github.CCweixiao.util.HBytesUtil;
import com.github.CCweixiao.util.ReflectUtil;
import com.github.CCweixiao.util.StrUtil;

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

    @Override
    public List<Map<String, Object>> getToListMap(String tableName, String rowKey) {
        return getToListMap(tableName, rowKey, null, null);
    }

    @Override
    public List<Map<String, Object>> getToListMap(String tableName, String rowKey, String familyName) {
        return getToListMap(tableName, rowKey, familyName, null);
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
        return findToListMap(tableName, null, null, null, null, limit);
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, Integer limit) {
        return findToListMap(tableName, familyName, null, null, null, limit);
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, Integer limit) {
        return findToListMap(tableName, familyName, qualifiers, null, null, limit);
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, String startKey, Integer limit) {
        return findToListMap(tableName, familyName, null, startKey, null, limit);
    }

    @Override
    public List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, String startKey, Integer limit) {
        return findToListMap(tableName, familyName, qualifiers, startKey, null, limit);
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
        return find(tableName, scan, limit, (result, rowNum) -> getToResultMap(result));
    }

    @Override
    public void delete(String tableName, String rowKey) {
        delete(tableName, rowKey, null, new ArrayList<>());
    }

    @Override
    public void delete(String tableName, String rowKey, String family) {
        delete(tableName, rowKey, family, new ArrayList<>());
    }

    @Override
    public void delete(String tableName, String rowKey, String family, List<String> qualifiers) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("the table name is not empty.");
        }
        if (StrUtil.isBlank(rowKey)) {
            throw new HBaseOperationsException("the row key of the table will be deleted is not empty.");
        }
        Delete delete = delete(rowKey, family, qualifiers);
        this.execute(tableName, table -> {
            table.mutate(delete);
        });
    }

    @Override
    public void delete(String tableName, String rowKey, String family, String... qualifiers) {
        if (qualifiers == null || qualifiers.length == 0) {
            delete(tableName, rowKey, family);
        } else {
            delete(tableName, rowKey, family, Arrays.asList(qualifiers));
        }
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys) {
        deleteBatch(tableName, rowKeys, null, new ArrayList<>());
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String family) {
        deleteBatch(tableName, rowKeys, family, new ArrayList<>());
    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String family, List<String> qualifiers) {
        if (StrUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("the table name is not empty.");
        }
        if (rowKeys == null || rowKeys.isEmpty()) {
            throw new HBaseOperationsException("the row keys of the table will be deleted is not empty.");
        }
        List<Mutation> mutations = rowKeys.stream().map(rowKey -> delete(rowKey, family, qualifiers)).collect(Collectors.toList());

        this.execute(tableName, mutator -> {
            mutator.mutate(mutations);
        });

    }

    @Override
    public void deleteBatch(String tableName, List<String> rowKeys, String family, String... qualifiers) {
        if (qualifiers == null || qualifiers.length == 0) {
            deleteBatch(tableName, rowKeys, family);
        } else {
            deleteBatch(tableName, rowKeys, family, Arrays.asList(qualifiers));
        }
    }

    /**
     * 构造get
     *
     * @param rowName    row key
     * @param familyName 列簇名
     * @param qualifiers 筛选字段
     * @return get
     */
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

    /**
     * 构造scan
     *
     * @param family     列簇名
     * @param qualifiers 字段名筛选列表
     * @return scan
     */
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

    /**
     * 构造put的对象
     *
     * @param rowKey rowKey
     * @param data   map类型的数据
     * @return put
     */
    private Put put(String rowKey, Map<String, Object> data) {
        Put put = new Put(HBytesUtil.toBytes(rowKey));
        data.forEach((fieldName, fieldValue) -> put.addColumn(Bytes.toBytes(fieldName.substring(0, fieldName.lastIndexOf(":"))),
                Bytes.toBytes(fieldName.substring(fieldName.lastIndexOf(":") + 1)), HBytesUtil.toBytes(fieldValue)));
        return put;
    }

    /**
     * 构造delete
     *
     * @param rowKey     row key
     * @param family     列簇名
     * @param qualifiers 字段名筛选
     * @return delete
     */
    private Delete delete(String rowKey, String family, List<String> qualifiers) {
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        if (qualifiers != null && qualifiers.size() > 0) {
            if (StrUtil.isBlank(family)) {
                throw new HBaseOperationsException("the family is not empty, when qualifiers not empty.");
            }
            for (String qualifier : qualifiers) {
                delete.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            }
        } else {
            if (StrUtil.isNotBlank(family)) {
                delete.addFamily(Bytes.toBytes(family));
            }
        }
        return delete;
    }

    private Map<String, Object> resultToMap(Result result, Cell cell) {
        Map<String, Object> resultMap = new HashMap<>(4);
        String fieldName = Bytes.toString(CellUtil.cloneFamily(cell)) + ":" + Bytes.toString(CellUtil.cloneQualifier(cell));
        byte[] value = CellUtil.cloneValue(cell);
        resultMap.put("rowKey", Bytes.toString(result.getRow()));
        resultMap.put("familyName", fieldName);
        resultMap.put("timestamp", cell.getTimestamp());
        resultMap.put("value", HBytesUtil.toObject(value, Object.class));
        return resultMap;
    }

    private List<Map<String, Object>> getToResultMap(Result result) {
        List<Cell> cs = result.listCells();
        List<Map<String, Object>> dataMaps = new ArrayList<>(cs.size());
        for (Cell cell : cs) {
            Map<String, Object> resultMap = resultToMap(result, cell);
            dataMaps.add(resultMap);
        }
        return dataMaps;
    }
}

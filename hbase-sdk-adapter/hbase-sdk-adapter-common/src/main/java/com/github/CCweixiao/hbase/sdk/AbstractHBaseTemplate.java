package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseRowKey;
import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;
import com.github.CCweixiao.hbase.sdk.common.util.ReflectUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import com.github.CCweixiao.hbase.sdk.util.HBytesUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>the abstract class of HBaseTemplate,</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public abstract class AbstractHBaseTemplate extends AbstractHBaseOperations implements IHBaseTableOperations {
    public AbstractHBaseTemplate(Configuration configuration) {
        super(configuration);
    }

    public AbstractHBaseTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public AbstractHBaseTemplate(Properties properties) {
        super(properties);
    }


    @Override
    public void save(String tableName, String rowKey, Map<String, Object> data) {
        this.save(tableName, buildPutCondition(rowKey, data));
    }

    @Override
    public <T> T save(T t) throws Exception {
        final Class<?> clazz = t.getClass();
        final String tableName = ReflectUtil.getHBaseTableName(clazz);
        String randomRowKeyName = createRandomRowKeyName();
        final String defaultFamilyName = ReflectUtil.getTableDefaultFamilyName(clazz);
        Field[] fields = getFieldArr(clazz);
        Map<String, Method> methodMap = getMethodMap(clazz);
        Map<String, Object> data = createPutData(fields, methodMap, t, randomRowKeyName, defaultFamilyName);
        Put put = new Put(Bytes.toBytes(String.valueOf(data.get(randomRowKeyName))));
        data.remove(randomRowKeyName);
        data.forEach((fieldName, fieldValue) ->
                put.addColumn(Bytes.toBytes(fieldName.substring(0, fieldName.lastIndexOf(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR))),
                Bytes.toBytes(fieldName.substring(fieldName.lastIndexOf(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR) + 1)),
                        HBytesUtil.toBytes(fieldValue)));
        this.save(tableName, put);
        return t;
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
        final String defaultFamilyName = ReflectUtil.getTableDefaultFamilyName(clazz0);
        Field[] fields = getFieldArr(clazz0);
        Map<String, Method> methodMap = getMethodMap(clazz0);
        List<Mutation> putList = new ArrayList<>(list.size());

        for (T t : list) {
            String randomRowKeyName = createRandomRowKeyName();
            Map<String, Object> data = createPutData(fields, methodMap, t, randomRowKeyName, defaultFamilyName);
            Put put = new Put(HBytesUtil.toBytes(data.get(randomRowKeyName)));
            data.remove(randomRowKeyName);
            data.forEach((fieldName, fieldValue) ->
                    put.addColumn(Bytes.toBytes(fieldName.substring(0, fieldName.lastIndexOf(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR))),
                    Bytes.toBytes(fieldName.substring(fieldName.lastIndexOf(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR) + 1)),
                            HBytesUtil.toBytes(fieldValue)));
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

    protected abstract Scan buildScanCondition(ScanQueryParamsBuilder scanQueryParams);

    /**
     * 构造get的查询条件
     *
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return get
     */
    protected Get buildGetCondition(String rowKey, String familyName, List<String> qualifiers) {
        if (StrUtil.isBlank(rowKey)) {
            return null;
        }
        Get get = new Get(Bytes.toBytes(rowKey));
        if (familyNameOnly(familyName, qualifiers)) {
            get.addFamily(Bytes.toBytes(familyName));
        }
        if (familyAndColumnNames(familyName, qualifiers)) {
            qualifiers.forEach(qualifier -> {
                if (StrUtil.isNotBlank(qualifier)) {
                    get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifier));
                }
            });
        }
        return get;
    }

    protected List<Get> buildBatchGetCondition(List<String> rowKeys, String familyName, List<String> qualifiers) {
        if (null == rowKeys || rowKeys.isEmpty()) {
            return new ArrayList<>();
        }
        return rowKeys.stream().distinct().map(rowKey -> buildGetCondition(rowKey, familyName, qualifiers))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    protected Result checkGetResultIsNull(Get get, Table table) throws IOException {
        if (get == null) {
            return null;
        }
        Result result = table.get(get);
        if (result == null || result.getRow() == null) {
            return null;
        }
        return result;
    }

    protected Result[] checkBatchGetResultIsNull(List<Get> gets, Table table) throws IOException {
        if (gets.isEmpty()) {
            return null;
        }
        Result[] results = table.get(gets);
        if (results == null || results.length == 0) {
            return null;
        }
        return results;
    }

    /**
     * 构造put的对象
     *
     * @param rowKey rowKey
     * @param data   map类型的数据
     * @return put
     */
    protected Put buildPutCondition(String rowKey, Map<String, Object> data) {
        if (StrUtil.isBlank(rowKey)) {
            throw new HBaseOperationsException("RowKey must not be empty.");
        }
        Put put = new Put(HBytesUtil.toBytes(rowKey));
        data.forEach((fieldName, fieldValue) -> put.addColumn(Bytes.toBytes(fieldName.substring(0, fieldName.lastIndexOf(":"))),
                Bytes.toBytes(fieldName.substring(fieldName.lastIndexOf(":") + 1)), HBytesUtil.toBytes(fieldValue)));
        return put;
    }

    /**
     * 构造delete的对象
     *
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名列表
     * @return delete
     */
    protected Delete buildDeleteCondition(String rowKey, String familyName, List<String> qualifiers) {
        if (StrUtil.isBlank(rowKey)) {
            throw new HBaseOperationsException("RowKey must not be empty.");
        }
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        if (familyNameOnly(familyName, qualifiers)) {
            delete.addFamily(Bytes.toBytes(familyName));
        }
        if (familyAndColumnNames(familyName, qualifiers)) {
            qualifiers.forEach(qualifier -> {
                if (StrUtil.isNotBlank(qualifier)) {
                    delete.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifier));
                }
            });
        }
        return delete;
    }

    protected Map<String, String> parseResultToMap(Result result, boolean withTimestamp) {
        List<Cell> cells = result.listCells();
        if (cells == null || cells.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, String> data = new HashMap<>(cells.size());
        for (Cell cell : cells) {
            String fieldName = Bytes.toString(CellUtil.cloneFamily(cell)) + HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR
                    + Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            data.put(fieldName, value);
            if (withTimestamp) {
                data.put(fieldName + ":timestamp", String.valueOf(cell.getTimestamp()));
            }
        }
        return data;
    }


    /**
     * 把Result查询结果集映射为Map类型的结构
     *
     * @param result Result对象
     * @return Map结果的数据
     */
    protected Map<String, String> mapperRowToMap(Result result) {
        byte[] row = result.getRow();
        if (row == null) {
            return new HashMap<>(0);
        }
        List<Cell> cs = result.listCells();
        Map<String, String> resultMap = new HashMap<>(cs.size());
        StringBuilder fieldNameSb = new StringBuilder();
        for (Cell cell : cs) {
            fieldNameSb.delete(0, fieldNameSb.length());
            fieldNameSb.append(Bytes.toString(CellUtil.cloneFamily(cell)));
            fieldNameSb.append(":");
            fieldNameSb.append(Bytes.toString(CellUtil.cloneQualifier(cell)));
            String value = "";
            if (cell.getValueArray() != null) {
                value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            }
            resultMap.put(fieldNameSb.toString(), value);
        }
        return resultMap;
    }

    /**
     * 利用反射，绑定查询结果集到定义的JavaBean
     *
     * @param result 数据集合
     * @param clazz  映射的JavaBean
     * @param <T>    泛型类型
     * @return 映射JavaBean之后的查询结果集
     * @throws Exception 异常抛出
     */
    protected <T> T mapperRowToT(Result result, Class<T> clazz) throws Exception {
        final String rowKey = Bytes.toString(result.getRow());
        if (rowKey == null) {
            return null;
        }
        List<Cell> cs = result.listCells();
        Map<String, byte[]> resultMap = new HashMap<>(cs.size());
        StringBuilder fieldNameSb = new StringBuilder();
        for (Cell cell : cs) {
            fieldNameSb.delete(0, fieldNameSb.length());
            fieldNameSb.append(Bytes.toString(CellUtil.cloneFamily(cell)));
            fieldNameSb.append(":");
            fieldNameSb.append(Bytes.toString(CellUtil.cloneQualifier(cell)));
            byte[] value = CellUtil.cloneValue(cell);
            resultMap.put(fieldNameSb.toString(), value);
        }

        T t = clazz.getDeclaredConstructor().newInstance();
        Method getMethod;
        Method setMethod;
        Map<String, Method> allMethodMap = ReflectUtil.getAllMethodsMap(t.getClass());
        if (allMethodMap == null || allMethodMap.isEmpty()) {
            throw new HBaseOperationsException("please assign standard getter and setter methods for " + clazz.getSimpleName() + ".");
        }
        final Field[] allFields = ReflectUtil.getAllFields(clazz);

        for (Field field : allFields) {
            if (ReflectUtil.isNotGeneralProperty(field)) {
                continue;
            }
            setMethod = allMethodMap.getOrDefault(ReflectUtil.getSetterName(field), null);
            if (ReflectUtil.isNotGeneralSetterMethod(setMethod)) {
                throw new HBaseOperationsException("please assign a standard setter method for property: " + field.getName() + ".");
            }
            if (field.isAnnotationPresent(HBaseRowKey.class)) {
                Object[] args = new Object[]{rowKey};
                setMethod.invoke(t, args);
            } else {
                String columnName = ReflectUtil.getHBaseColumnName(ReflectUtil.getTableDefaultFamilyName(clazz), field);
                if (resultMap.containsKey(columnName)) {
                    byte[] byteArrValue = resultMap.get(columnName);
                    getMethod = allMethodMap.getOrDefault(ReflectUtil.getGetterName(field), null);
                    if (ReflectUtil.isNotGeneralGetterMethod(getMethod)) {
                        throw new HBaseOperationsException("please assign a standard getter method for property: " + field.getName() + ".");
                    }
                    Object[] args = new Object[1];
                    if (byteArrValue != null) {
                        args[0] = HBytesUtil.toObject(byteArrValue, getMethod.getReturnType());
                        setMethod.invoke(t, args);
                    }
                }
            }

        }
        return t;
    }

    private Field[] getFieldArr(Class<?> clazz) {
        Field[] fields = ReflectUtil.getAllFields(clazz);
        if (fields == null || fields.length == 0) {
            throw new HBaseOperationsException("Please assign values for " + clazz.getSimpleName() + ".");
        }
        return fields;
    }

    private Map<String, Method> getMethodMap(Class<?> clazz) {
        Map<String, Method> methodMap = ReflectUtil.getAllMethodsMap(clazz);
        if (methodMap == null || methodMap.isEmpty()) {
            throw new HBaseOperationsException("Please assign getter and setter for " + clazz.getSimpleName() + ".");
        }
        return methodMap;
    }

    private <T> Map<String, Object> createPutData(Field[] fields, Map<String, Method> methodMap, T t, String randomRowKeyName, String defaultFamilyName)
            throws InvocationTargetException, IllegalAccessException {
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
                    data.put(randomRowKeyName, fieldValue);
                }
            } else {
                String fieldName = ReflectUtil.getHBaseColumnName(defaultFamilyName, field);
                data.put(fieldName, fieldValue);
            }
        }
        if (!data.containsKey(randomRowKeyName) || data.get(randomRowKeyName) == null) {
            throw new HBaseOperationsException("Please provided a rowKey.");
        }
        return data;
    }

    /**
     * 创建随机的RowKey name
     *
     * @return 随机RowKey
     */
    protected String createRandomRowKeyName() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return "row_key_" + uuid;
    }

    /**
     * 判断列簇名满足条件，需要筛选的字段列表未指定
     *
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return 最终数据
     */
    protected boolean familyNameOnly(String familyName, List<String> qualifiers) {
        return StrUtil.isNotBlank(familyName) && (qualifiers == null || qualifiers.isEmpty());
    }

    /**
     * 判断列簇名和需要筛选的字段列表同时成立
     *
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return 最终数据
     */
    protected boolean familyAndColumnNames(String familyName, List<String> qualifiers) {
        return StrUtil.isNotBlank(familyName) && (qualifiers != null && !qualifiers.isEmpty());
    }
}

package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseMetaDataException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;
import com.github.CCweixiao.hbase.sdk.common.reflect.FieldStruct;
import com.github.CCweixiao.hbase.sdk.common.reflect.HBaseTableMeta;
import com.github.CCweixiao.hbase.sdk.common.reflect.ReflectFactory;
import com.github.CCweixiao.hbase.sdk.common.type.AbstractTypeHandler;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.common.type.TypeHandler;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
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
    public <T> T save(T t){
        final Class<?> clazz = t.getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        this.save(tableMeta.getTableName(), new Put(createPut(t)));
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
    public <T> int saveBatch(List<T> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        final Class<?> clazz0 = list.get(0).getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz0);
        List<Mutation> putList = new ArrayList<>(list.size());
        for (T t : list) {
            putList.add(new Put(createPut(t)));
        }
        this.saveBatch(tableMeta.getTableName(), putList);
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
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
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
    public Map<String, String> getRowToMap(String tableName, String rowKey, String familyName,
                                           List<String> qualifiers, boolean withTimestamp) {
        return this.execute(tableName, table -> {
            Get get = buildGetCondition(rowKey, familyName, qualifiers);
            Result result = checkGetResultIsNull(get, table);
            if (result == null) {
                return null;
            }
            return parseResultToMap(result, withTimestamp);
        }).orElse(new HashMap<>(0));
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
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
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
        String tableName = ReflectFactory.getHBaseTableMeta(clazz).getTableName();
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
        if (StringUtil.isBlank(tableName)) {
            throw new HBaseOperationsException("the table name is not empty.");
        }
        if (StringUtil.isBlank(rowKey)) {
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
        if (StringUtil.isBlank(tableName)) {
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
        if (StringUtil.isBlank(rowKey)) {
            return null;
        }
        Get get = new Get(Bytes.toBytes(rowKey));
        if (familyNameOnly(familyName, qualifiers)) {
            get.addFamily(Bytes.toBytes(familyName));
        }
        if (familyAndColumnNames(familyName, qualifiers)) {
            qualifiers.forEach(qualifier -> {
                if (StringUtil.isNotBlank(qualifier)) {
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
        if (StringUtil.isBlank(rowKey)) {
            throw new HBaseOperationsException("RowKey must not be empty.");
        }
        Put put = new Put(Bytes.toBytes(rowKey));
        data.forEach((fieldName, fieldValue) -> {
            String[] familyQualifierArr = fieldName.split(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR);
            TypeHandler<?> fieldTypeHandler = ColumnType.findTypeHandler(fieldValue.getClass());
            put.addColumn(Bytes.toBytes(familyQualifierArr[0]), Bytes.toBytes(familyQualifierArr[1]),
                    ColumnType.StringType.getTypeHandler().toBytes(fieldTypeHandler.toString(fieldValue)));
        });
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
        if (StringUtil.isBlank(rowKey)) {
            throw new HBaseOperationsException("RowKey must not be empty.");
        }
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        if (familyNameOnly(familyName, qualifiers)) {
            delete.addFamily(Bytes.toBytes(familyName));
        }
        if (familyAndColumnNames(familyName, qualifiers)) {
            qualifiers.forEach(qualifier -> {
                if (StringUtil.isNotBlank(qualifier)) {
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
        //TODO 这里的反射调用构造函数是否可以再优化
        T t = clazz.getDeclaredConstructor().newInstance();
        HBaseTableMeta hBaseTableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        List<FieldStruct> fieldColStructMap = hBaseTableMeta.getFieldStructList();
        fieldColStructMap.forEach(fieldStruct -> {
            if (fieldStruct.isRowKey()) {
                Object rowKey = fieldStruct.getTypeHandler().toObject(fieldStruct.getType(), result.getRow());
                hBaseTableMeta.getMethodAccess().invoke(t, fieldStruct.getSetterMethodIndex(), rowKey);
            } else {
                byte[] valBytes = result.getValue(Bytes.toBytes(fieldStruct.getFamily()), Bytes.toBytes(fieldStruct.getQualifier()));
                Object value = fieldStruct.getTypeHandler().toObject(fieldStruct.getType(), valBytes);
                hBaseTableMeta.getMethodAccess().invoke(t, fieldStruct.getSetterMethodIndex(), value);
            }
        });
        return t;
    }

    private <T> Put createPut(T t)
            throws HBaseMetaDataException {
        Class<?> clazz = t.getClass();
        HBaseTableMeta tableMeta = ReflectFactory.getHBaseTableMeta(clazz);
        List<FieldStruct> fieldStructList = tableMeta.getFieldStructList();
        FieldStruct rowFieldStruct = fieldStructList.get(0);
        if (!rowFieldStruct.isRowKey()) {
            throw new HBaseMetaDataException("The first field is not row key, please check hbase table mata data.");
        }
        Object value = tableMeta.getMethodAccess().invoke(t, rowFieldStruct.getGetterMethodIndex());
        MyAssert.checkArgument(value != null, "The value of row key must not be null.");
        Put put = new Put(rowFieldStruct.getTypeHandler().toBytes(rowFieldStruct.getType(), value));

        fieldStructList.forEach(fieldStruct -> {
            if (!fieldStruct.isRowKey()) {
                Object fieldValue = tableMeta.getMethodAccess().invoke(t, fieldStruct.getGetterMethodIndex());
                put.addColumn(Bytes.toBytes(fieldStruct.getFamily()),
                        Bytes.toBytes(fieldStruct.getQualifier()),
                        fieldStruct.getTypeHandler().toBytes(fieldStruct.getType(), fieldValue));
            }
        });

        return put;
    }

    /**
     * 判断列簇名满足条件，需要筛选的字段列表未指定
     *
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return 最终数据
     */
    protected boolean familyNameOnly(String familyName, List<String> qualifiers) {
        return StringUtil.isNotBlank(familyName) && (qualifiers == null || qualifiers.isEmpty());
    }

    /**
     * 判断列簇名和需要筛选的字段列表同时成立
     *
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return 最终数据
     */
    protected boolean familyAndColumnNames(String familyName, List<String> qualifiers) {
        return StringUtil.isNotBlank(familyName) && (qualifiers != null && !qualifiers.isEmpty());
    }
}

package com.github.CCweixiao.hbase.sdk.adapter_22;

import com.github.CCweixiao.hbase.sdk.common.HBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseRowKey;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.adapter_22.util.HBytesUtil;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseQueryParamsException;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;
import com.github.CCweixiao.hbase.sdk.common.util.ReflectUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>the abstract class of HBaseTemplate,</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public abstract class AbstractHBaseTemplate extends AbstractHBaseConfig implements HBaseTableOperations {
    public AbstractHBaseTemplate(Configuration configuration) {
        super(configuration);
    }

    public AbstractHBaseTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public AbstractHBaseTemplate(Properties properties) {
        super(properties);
    }

    /**
     * 批量保存数据
     *
     * @param tableName 表名
     * @param mutations list of mutation,example: put list
     */
    public abstract void saveBatch(String tableName, List<Mutation> mutations);

    /**
     * 保存单条数据
     *
     * @param tableName 表名
     * @param mutation  mutation,example: put
     */
    public abstract void save(String tableName, Mutation mutation);

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

    protected Scan buildScanCondition(ScanQueryParamsBuilder scanQueryParams) {
        Scan scan = new Scan();
        if (familyNameOnly(scanQueryParams.getFamilyName(), scanQueryParams.getColumnNames())) {
            scan.addFamily(Bytes.toBytes(scanQueryParams.getFamilyName()));
        }
        if (familyAndColumnNames(scanQueryParams.getFamilyName(), scanQueryParams.getColumnNames())) {
            scanQueryParams.getColumnNames().forEach(colName -> {
                if (StrUtil.isNotBlank(colName)) {
                    scan.addColumn(Bytes.toBytes(scanQueryParams.getFamilyName()), Bytes.toBytes(colName));
                }
            });
        }

        if (StrUtil.isNotBlank(scanQueryParams.getStartRow())) {
            scan.withStartRow(Bytes.toBytes(scanQueryParams.getStartRow()), scanQueryParams.isInclusiveStartRow());
        }

        if (StrUtil.isNotBlank(scanQueryParams.getStopRow())) {
            scan.withStopRow(Bytes.toBytes(scanQueryParams.getStopRow()), scanQueryParams.isInclusiveStopRow());
        }

        if (scanQueryParams.getFilter() != null && scanQueryParams.getFilter().customFilter() instanceof Filter) {
            scan.setFilter(scanQueryParams.getFilter().customFilter());
        }

        if (scanQueryParams.getMinTimestamp() > 0 && scanQueryParams.getMaxTimestamp() > 0) {
            try {
                scan.setTimeRange(scanQueryParams.getMinTimestamp(), scanQueryParams.getMaxTimestamp());
            } catch (IOException e) {
                throw new HBaseQueryParamsException(e);
            }
        }

        if (scanQueryParams.getTimestamp() > 0) {
            scan.setTimestamp(scanQueryParams.getTimestamp());
        }

        if (scanQueryParams.getReadVersions() > 0) {
            scan.readVersions(scanQueryParams.getReadVersions());
        }

        if (scanQueryParams.isCacheBlocks()) {
            scan.setCacheBlocks(scanQueryParams.isCacheBlocks());
        }

        if (scanQueryParams.isReversed()) {
            scan.setReversed(scan.isReversed());
        }

        if (scanQueryParams.getCaching() > 0) {
            scan.setCaching(scanQueryParams.getCaching());
        }

        if (scanQueryParams.getBatch() > 0) {
            scan.setBatch(scanQueryParams.getBatch());
        }

        if (scanQueryParams.getMaxResultSize() > 0) {
            scan.setMaxResultSize(scanQueryParams.getMaxResultSize());
        }

        if (scanQueryParams.getLimit() > 0) {
            scan.setLimit(scanQueryParams.getLimit());
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
            String field = Bytes.toString(CellUtil.cloneFamily(cell)) + ":" + Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            data.put(field, value);
            if (withTimestamp) {
                data.put(field + ":timestamp", String.valueOf(cell.getTimestamp()));
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
                String columnName = ReflectUtil.getHBaseColumnName(ReflectUtil.getUniqueTableFamily(clazz), field);
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
}

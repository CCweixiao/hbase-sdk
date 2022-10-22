package com.github.CCweixiao.hbase.sdk.adapter_14;

import com.github.CCweixiao.hbase.sdk.common.HBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseRowKey;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.adapter_14.util.HBytesUtil;
import com.github.CCweixiao.hbase.sdk.common.util.ReflectUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>the abstract class of HBaseTemplate,</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public abstract class AbstractHBaseTemplate extends AbstractHBaseConfig implements HBaseTableOperations {
    public AbstractHBaseTemplate(String zkHost, String zkPort) {
        super(zkHost, zkPort);
    }

    public AbstractHBaseTemplate(Configuration configuration) {
        super(configuration);
    }

    public AbstractHBaseTemplate(Properties properties) {
        super(properties);
    }

    /**
     * 把Result查询结果集映射为Map类型的结构
     *
     * @param result Result对象
     * @return Map结果的数据
     */
    protected Map<String, String> mapperRowToMap(Result result) {
        final String rowKey = Bytes.toString(result.getRow());
        if (rowKey == null) {
            return new HashMap<>();
        }
        List<Cell> cs = result.listCells();
        Map<String, String> resultMap = new HashMap<>(cs.size());
        StringBuilder fieldNameSb = new StringBuilder();
        for (Cell cell : cs) {
            fieldNameSb.delete(0, fieldNameSb.length());
            fieldNameSb.append(Bytes.toString(CellUtil.cloneFamily(cell)));
            fieldNameSb.append(":");
            fieldNameSb.append(Bytes.toString(CellUtil.cloneQualifier(cell)));
            String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
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
     * get查询数据，可以指定自定义的Row Mapper
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @param rowMapper 自定义的RowMapper
     * @param <T>       泛型类型
     * @return get查询结果
     */
    public abstract <T> T get(String tableName, String rowKey, RowMapper<T> rowMapper);

    /**
     * get查询某一列簇下的数据，可以指定自定义的Row Mapper
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return get查询结果
     */
    public abstract <T> T get(String tableName, String rowKey, String familyName, RowMapper<T> rowMapper);

    /**
     * get查询某一列簇下的数据，可以指定多个字段，可以指定自定义的Row Mapper
     *
     * @param tableName  表名
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return get查询结果
     */
    public abstract <T> T get(String tableName, String rowKey, String familyName, List<String> qualifiers, RowMapper<T> rowMapper);


    /**
     * scan 查询数据
     *
     * @param tableName 表名
     * @param scan      scan对象
     * @param limit     限制返回的结果集条数
     * @param clazz     查询结果集绑定的数据类型
     * @param <T>       泛型类型
     * @return 查询结果集
     */
    public abstract <T> List<T> find(String tableName, Scan scan, int limit, Class<T> clazz);

    /**
     * scan 数据
     *
     * @param tableName 表名
     * @param scan      scan条件对象
     * @param limit     限制返回的结果集条数
     * @param rowMapper 自定义的RowMapper
     * @param <T>       泛型类型
     * @return 查询结果集
     */
    public abstract <T> List<T> find(String tableName, Scan scan, int limit, RowMapper<T> rowMapper);

    /**
     * scan 数据，返回List结构
     *
     * @param tableName 表名
     * @param scan      scan条件对象
     * @param limit     限制返回的结果集条数
     * @return 查询结果集
     */
    public abstract List<Map<String, String>> find(String tableName, Scan scan, int limit);


    /**
     * scan 所有数据
     *
     * @param tableName 表名
     * @param limit     限制返回的结果集条数
     * @param rowMapper 自定义的RowMapper
     * @param <T>       泛型类型
     * @return 查询结果集
     */
    public abstract <T> List<T> findAll(String tableName, int limit, RowMapper<T> rowMapper);

    /**
     * scan 某一列簇下的数据
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param limit      限制返回的结果集条数
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return 查询结果集
     */
    public abstract <T> List<T> findAllByFamily(String tableName, String familyName, int limit, RowMapper<T> rowMapper);

    /**
     * scan 某一列簇下的数据，可以指定需要筛选的字段
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段
     * @param limit      限制返回的结果集条数
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return 查询结果集
     */
    public abstract <T> List<T> findAllByFamilyAndQualifiers(String tableName, String familyName, List<String> qualifiers, int limit, RowMapper<T> rowMapper);


    /**
     * 根据前缀scan 数据
     *
     * @param tableName 表名
     * @param prefix    前缀
     * @param limit     限制返回的结果集条数
     * @param rowMapper 自定义的RowMapper
     * @param <T>       泛型类型
     * @return 查询结果集
     */
    public abstract <T> List<T> findAllByPrefix(String tableName, String prefix, int limit, RowMapper<T> rowMapper);

    /**
     * 根据前缀scan数据，指定列簇
     *
     * @param tableName  表名
     * @param prefix     前缀
     * @param familyName 列簇
     * @param limit      限制返回的结果集条数
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return 查询结果集
     */
    public abstract <T> List<T> findAllByPrefixWithFamily(String tableName, String prefix, String familyName, int limit, RowMapper<T> rowMapper);

    /**
     * 根据前缀scan数据，指定列簇名和需要筛选的字段
     *
     * @param tableName  表名
     * @param prefix     前缀
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段
     * @param limit      限制返回的结果集条数
     * @param rowMapper  自定义的RowMapper
     * @param <T>        泛型类型
     * @return 查询结果集
     */
    public abstract <T> List<T> findAllByPrefixWithFamilyAndQualifiers(String tableName, String prefix, String familyName, List<String> qualifiers, int limit, RowMapper<T> rowMapper);

    /**
     * 构造get的查询条件
     *
     * @param rowKey     rowKey
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return get
     */
    protected Get get(String rowKey, String familyName, List<String> qualifiers) {
        if (StrUtil.isBlank(rowKey)) {
            throw new HBaseOperationsException("RowKey must not be empty.");
        }
        Get get = new Get(Bytes.toBytes(rowKey));

        if (familySatisfied(familyName, qualifiers)) {
            get.addFamily(Bytes.toBytes(familyName));
        }
        if (familyAndQualifierSatisfied(familyName, qualifiers)) {
            qualifiers.forEach(qualifier -> {
                if (StrUtil.isNotBlank(qualifier)) {
                    get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifier));
                }
            });
        }
        return get;
    }

    /**
     * 构造scan的查询对象
     *
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段名列表
     * @return scan的查询对象
     */
    protected Scan scan(String familyName, List<String> qualifiers) {
        Scan scan = scan(new Scan(), 0);

        if (familySatisfied(familyName, qualifiers)) {
            scan.addFamily(Bytes.toBytes(familyName));
        }
        if (familyAndQualifierSatisfied(familyName, qualifiers)) {
            qualifiers.forEach(qualifier -> {
                if (StrUtil.isNotBlank(qualifier)) {
                    scan.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifier));
                }
            });
        }
        return scan;
    }

    protected Scan scan(Scan scan, int limit) {
        int caching = scan.getCaching();
        // 如果caching未设置(默认是1)，将默认配置成5000
        if (caching == 1) {
            scan.setCaching(5000);
        }
        if (limit > 0) {
            scan.setLimit(limit);
        }
        if (scan.getLimit() <= 0 && limit <= 0) {
            scan.setLimit(1000);
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
    protected Put put(String rowKey, Map<String, Object> data) {
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
    protected Delete delete(String rowKey, String familyName, List<String> qualifiers) {
        if (StrUtil.isBlank(rowKey)) {
            throw new HBaseOperationsException("RowKey must not be empty.");
        }
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        if (familySatisfied(familyName, qualifiers)) {
            delete.addFamily(Bytes.toBytes(familyName));
        }
        if (familyAndQualifierSatisfied(familyName, qualifiers)) {
            qualifiers.forEach(qualifier -> {
                if (StrUtil.isNotBlank(qualifier)) {
                    delete.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifier));
                }
            });
        }
        return delete;
    }

    /**
     * 判断列簇名和需要筛选的字段列表同时成立
     *
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return 最终数据
     */
    private boolean familyAndQualifierSatisfied(String familyName, List<String> qualifiers) {
        return StrUtil.isNotBlank(familyName) && (qualifiers != null && !qualifiers.isEmpty());
    }

    /**
     * 判断列簇名满足条件，需要筛选的字段列表未指定
     *
     * @param familyName 列簇名
     * @param qualifiers 需要筛选的字段列表
     * @return 最终数据
     */
    private boolean familySatisfied(String familyName, List<String> qualifiers) {
        return StrUtil.isNotBlank(familyName) && (qualifiers == null || qualifiers.isEmpty());
    }

    protected Map<String, Object> resultToMap(Result result, Cell cell) {
        Map<String, Object> resultMap = new HashMap<>(4);
        if (result == null) {
            resultMap.put("rowKey", "");
            resultMap.put("familyName", "");
            resultMap.put("timestamp", -1);
            resultMap.put("value", "");
            return resultMap;
        }
        if (cell == null) {
            resultMap.put("rowKey", Bytes.toString(result.getRow()));
            resultMap.put("familyName", "");
            resultMap.put("timestamp", -1);
            resultMap.put("value", "");
            return resultMap;
        }
        String fieldName = Bytes.toString(CellUtil.cloneFamily(cell)) + ":" + Bytes.toString(CellUtil.cloneQualifier(cell));
        String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
        resultMap.put("rowKey", Bytes.toString(result.getRow()));
        resultMap.put("familyName", fieldName);
        resultMap.put("timestamp", cell.getTimestamp());
        resultMap.put("value", value);
        return resultMap;
    }

    protected List<Map<String, Object>> getToResultMap(Result result) {
        if (result == null) {
            return new ArrayList<>();
        }
        List<Cell> cs = result.listCells();
        List<Map<String, Object>> dataMaps = new ArrayList<>(cs.size());
        for (Cell cell : cs) {
            Map<String, Object> resultMap = resultToMap(result, cell);
            dataMaps.add(resultMap);
        }
        return dataMaps;
    }

    protected Map<String, Object> getOneToResultMap(Result result) {
        List<Cell> cells = result.listCells();
        if (cells == null || cells.isEmpty()) {
            return resultToMap(result, null);
        }
        return resultToMap(result, cells.get(0));
    }

}

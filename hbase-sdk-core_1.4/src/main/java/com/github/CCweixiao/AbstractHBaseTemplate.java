package com.github.CCweixiao;

import com.github.CCweixiao.annotation.HBaseRowKey;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.util.HBytesUtil;
import com.github.CCweixiao.util.ReflectUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.*;
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
public abstract class AbstractHBaseTemplate implements HBaseOperations, HBaseTableOperations {
    private Configuration configuration;

    private volatile Connection connection;

    public AbstractHBaseTemplate(Configuration configuration) {
        this.setConfiguration(configuration);
        if (this.configuration == null) {
            throw new HBaseOperationsException("a valid configuration is provided.");
        }
    }

    public AbstractHBaseTemplate(String zkHost, String zkPort) {
        Configuration configuration = getConfiguration(zkHost, zkPort);
        if (configuration == null) {
            throw new HBaseOperationsException("a valid configuration is provided.");
        }
        this.setConfiguration(configuration);
    }

    public AbstractHBaseTemplate(Properties properties) {
        Configuration configuration = getConfiguration(properties);
        if (configuration == null) {
            throw new HBaseOperationsException("a valid configuration is provided.");
        }
        this.setConfiguration(configuration);
    }

    /**
     * the result of get will be converted to a
     *
     * @param result get result
     * @return result
     */
    protected Map<String, Object> mapperRowToMap(Result result) {
        final String rowKey = Bytes.toString(result.getRow());
        if (rowKey == null) {
            return new HashMap<>();
        }
        List<Cell> cs = result.listCells();
        Map<String, Object> resultMap = new HashMap<>(cs.size());
        for (Cell cell : cs) {
            String fieldName = Bytes.toString(CellUtil.cloneFamily(cell)) + ":" + Bytes.toString(CellUtil.cloneQualifier(cell));
            byte[] value = CellUtil.cloneValue(cell);
            resultMap.put(fieldName, HBytesUtil.toObject(value, Object.class));
        }
        return resultMap;
    }

    /**
     * Using reflection mapping result to clazz.
     *
     * @param result result data set
     * @param clazz  return class type
     * @param <T>    class type
     * @return java bean
     * @throws Exception exception
     */
    protected <T> T mapperRowToT(Result result, Class<T> clazz) throws Exception {
        final String rowKey = Bytes.toString(result.getRow());
        if (rowKey == null) {
            return null;
        }
        List<Cell> cs = result.listCells();
        Map<String, byte[]> resultMap = new HashMap<>(cs.size());
        for (Cell cell : cs) {
            String fieldName = Bytes.toString(CellUtil.cloneFamily(cell)) + ":" + Bytes.toString(CellUtil.cloneQualifier(cell));
            byte[] value = CellUtil.cloneValue(cell);
            resultMap.put(fieldName, value);
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
     * create random row key name.
     *
     * @return put obj
     */
    protected String createRandomRowKeyName() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return "row_key_" + uuid;
    }

    @Override
    public Connection getConnection() {
        if (null == this.connection) {
            synchronized (this) {
                if (null == this.connection) {
                    try {
                        this.connection = ConnectionFactory.createConnection(configuration);
                        LOGGER.info("the connection pool of HBase is created successfully.>>>>>>>>>>>>>>>>>>");
                    } catch (IOException e) {
                        LOGGER.error("the connection pool of HBase is created failed.>>>>>>>>>>>>>>>>>");
                        LOGGER.error(e.getMessage());
                    }
                }
            }
        }
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration(String zkHost, String zkPort) {
        this.configuration = HBaseConfiguration.create();
        configuration.set(HConstants.ZOOKEEPER_QUORUM, zkHost);
        configuration.set(HConstants.ZOOKEEPER_CLIENT_PORT, zkPort);
        return configuration;
    }

    public Configuration getConfiguration(Properties properties) {
        this.configuration = HBaseConfiguration.create();
        final List<String> keys = properties.keySet().stream().map(Object::toString).collect(Collectors.toList());
        keys.forEach(key -> configuration.set(key, properties.getProperty(key)));
        return configuration;
    }

    /**
     * save batch
     *
     * @param tableName the name of table.
     * @param mutations list of mutation,example: put list
     */
    public abstract void saveBatch(String tableName, List<Mutation> mutations);

    /**
     * save data to HBase by mutation
     *
     * @param tableName the name of table.
     * @param mutation  mutation,example: put
     */
    public abstract void save(String tableName, Mutation mutation);


    /**
     * get by rowKey family qualifier.
     *
     * @param tableName the name of table.
     * @param rowName   rowKey
     * @param rowMapper your rowMapper
     * @param <T>       mapping class type
     * @return get result
     */
    public abstract <T> T get(String tableName, String rowName, RowMapper<T> rowMapper);

    /**
     * get by rowKey family qualifier.
     *
     * @param tableName  the name of table.
     * @param rowName    rowKey
     * @param familyName familyName
     * @param rowMapper  your rowMapper
     * @param <T>        mapping class type
     * @return get result
     */
    public abstract <T> T get(String tableName, String rowName, String familyName, RowMapper<T> rowMapper);

    /**
     * get by rowKey family qualifier.
     *
     * @param tableName  the name of table.
     * @param rowName    rowKey
     * @param familyName familyName
     * @param qualifiers list of qualifier name
     * @param rowMapper  your rowMapper
     * @param <T>        mapping class type
     * @return get result
     */
    public abstract <T> T get(String tableName, String rowName, String familyName, List<String> qualifiers, RowMapper<T> rowMapper);


    /**
     * scan HBase
     *
     * @param tableName the name of table.
     * @param scan      scan condition
     * @param limit     result limit
     * @param clazz     mapping class obj
     * @param <T>       mapping class type
     * @return result
     */
    public abstract <T> List<T> find(String tableName, Scan scan, int limit, Class<T> clazz);

    /**
     * scan all
     *
     * @param tableName tableName
     * @param limit     limit
     * @param rowMapper rowMapper
     * @param <T>       mapping class type
     * @return result
     */
    public abstract <T> List<T> findAll(String tableName, int limit, RowMapper<T> rowMapper);

    /**
     * scan by family
     *
     * @param tableName tableName
     * @param family    family
     * @param limit     limit
     * @param rowMapper rowMapper
     * @param <T>       mapping class type
     * @return result
     */
    public abstract <T> List<T> findByFamily(String tableName, String family, int limit, RowMapper<T> rowMapper);

    /**
     * scan by family and qualifier
     *
     * @param tableName  tableName
     * @param family     family
     * @param qualifiers qualifiers
     * @param limit      limit
     * @param rowMapper  rowMapper
     * @param <T>        mapping class type
     * @return result
     */
    public abstract <T> List<T> findByFamilyAndQualifiers(String tableName, String family, List<String> qualifiers, int limit, RowMapper<T> rowMapper);

    /**
     * scan HBase
     *
     * @param tableName the name of table.
     * @param scan      scan condition
     * @param limit     result limit
     * @param rowMapper your row mapper
     * @param <T>       mapping class type
     * @return result
     */
    public abstract <T> List<T> find(String tableName, Scan scan, int limit, RowMapper<T> rowMapper);


    /**
     * scan table by prefix.
     *
     * @param tableName table name
     * @param prefix    prefix
     * @param limit     limit
     * @param rowMapper your row mapper
     * @param <T>       mapping class type.
     * @return class type
     */
    public abstract <T> List<T> findByPrefix(String tableName, String prefix, int limit, RowMapper<T> rowMapper);

    /**
     * scan table by prefix with family.
     *
     * @param tableName table name
     * @param prefix    prefix
     * @param family    family name
     * @param limit     limit
     * @param rowMapper your row mapper
     * @param <T>       mapping class type.
     * @return class type
     */
    public abstract <T> List<T> findByPrefix(String tableName, String prefix, String family, int limit, RowMapper<T> rowMapper);

    /**
     * scan table by prefix with family and qualifier.
     *
     * @param tableName  table name
     * @param prefix     prefix
     * @param family     family name
     * @param qualifiers qualifiers
     * @param limit      limit
     * @param rowMapper  your row mapper
     * @param <T>        mapping class type.
     * @return class type
     */
    public abstract <T> List<T> findByPrefix(String tableName, String prefix, String family, List<String> qualifiers, int limit, RowMapper<T> rowMapper);


    /**
     * 查询数据
     *
     * @param tableName 表名
     * @param rowKey    row kwy
     * @return 查询结果
     */
    public abstract List<Map<String, Object>> getToListMap(String tableName, String rowKey);

    /**
     * 查询数据
     *
     * @param tableName  表名
     * @param rowKey     row kwy
     * @param familyName 列簇名
     * @return 查询结果
     */
    public abstract List<Map<String, Object>> getToListMap(String tableName, String rowKey, String familyName);

    /**
     * 查询数据
     *
     * @param tableName  表名
     * @param rowKey     row kwy
     * @param familyName 列簇名
     * @param qualifiers 字段名筛选
     * @return 查询结果
     */
    public abstract List<Map<String, Object>> getToListMap(String tableName, String rowKey, String familyName, List<String> qualifiers);

    /**
     * 查询数据，返回Map 类型
     *
     * @param tableName 表名
     * @param limit     扫描行数
     * @return 查询结果
     */
    public abstract List<List<Map<String, Object>>> findToListMap(String tableName, Integer limit);


    /**
     * 查询数据，返回Map 类型
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param limit      扫描行数
     * @return 查询结果
     */
    public abstract List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, Integer limit);

    /**
     * 查询数据，返回Map 类型
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param qualifiers 字段名筛选
     * @param limit      扫描行数
     * @return 查询结果
     */
    public abstract List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, Integer limit);

    /**
     * 查询数据，返回Map 类型
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param startKey   开始key
     * @param limit      扫描行数
     * @return 查询结果
     */
    public abstract List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, String startKey, Integer limit);

    /**
     * 查询数据，返回Map 类型
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param qualifiers 字段名筛选
     * @param startKey   开始key
     * @param limit      扫描行数
     * @return 查询结果
     */
    public abstract List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, String startKey, Integer limit);


    /**
     * 查询数据，返回Map 类型
     *
     * @param tableName  表名
     * @param familyName 列簇名
     * @param qualifiers 字段名筛选
     * @param startKey   开始key
     * @param endKey     结束key
     * @param limit      扫描行数
     * @return 查询结果
     */
    public abstract List<List<Map<String, Object>>> findToListMap(String tableName, String familyName, List<String> qualifiers, String startKey, String endKey, Integer limit);
}

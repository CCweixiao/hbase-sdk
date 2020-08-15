package com.leo.hbase.sdk.core;

import com.leo.hbase.sdk.core.annotation.HBaseRowKey;
import com.leo.hbase.sdk.core.exception.HBaseOperationsException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import com.leo.hbase.sdk.core.util.HBytesUtil;
import com.leo.hbase.sdk.core.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public abstract class AbstractHBaseTemplate implements HBaseOperations {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHBaseTemplate.class);
    protected static final int REPLICATION_SCOPE_0 = 0;
    protected static final int REPLICATION_SCOPE_1 = 1;

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

    @Override
    public <T> T execute(AdminCallback<T> action) {
        Admin admin = null;
        try {
            admin = this.getConnection().getAdmin();
            return action.doInAdmin(admin);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        } finally {
            if (null != admin) {
                try {
                    admin.close();
                } catch (IOException e) {
                    LOGGER.error("the resource of admin released failed.");
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public <T> T execute(String tableName, TableCallback<T> action) {
        Table table = null;
        try {
            table = this.getConnection().getTable(TableName.valueOf(tableName));
            return action.doInTable(table);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        } finally {
            if (null != table) {
                try {
                    table.close();
                } catch (IOException e) {
                    LOGGER.error("the resource of table released failed.");
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }

    @Override
    public void execute(String tableName, MutatorCallback action) {
        BufferedMutator mutator = null;
        try {
            BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
            mutator = this.getConnection().getBufferedMutator(mutatorParams.writeBufferSize(3 * 1024 * 1024));
            action.doInMutator(mutator);
        } catch (Throwable throwable) {
            throw new HBaseOperationsException(throwable);
        } finally {
            if (null != mutator) {
                try {
                    mutator.flush();
                    mutator.close();
                } catch (IOException e) {
                    LOGGER.error("the resource of mutator released failed.");
                    LOGGER.error(e.getMessage());
                }
            }
        }
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
        for (Field field : clazz.getDeclaredFields()) {
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


}

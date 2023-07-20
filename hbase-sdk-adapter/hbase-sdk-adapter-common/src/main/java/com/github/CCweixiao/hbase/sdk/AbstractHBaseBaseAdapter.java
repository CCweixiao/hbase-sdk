package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsExistsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsNotDisabledException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsNotExistsException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.yetus.audience.InterfaceAudience;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2020/11/13 11:52 下午
 */
@InterfaceAudience.Private
public abstract class AbstractHBaseBaseAdapter implements IHBaseBaseAdapter{
    private static final String HBASE_CLIENT_HEDGED_READ_SWITCH = "hbase.client.hedged.read.open";
    private static final String HBASE_CLIENT_HEDGED_READ_SWITCH_DEFAULT = "false";
    private static final String HBASE_CLIENT_HEDGED_READ_TIME_OUT = "hbase.client.hedged.read.timeout";
    private static final String HBASE_CLIENT_HEDGED_READ_TIME_OUT_DEFAULT_MS = "100";

    private static final String HBASE_CLIENT_HEDGED_READ_POOL_SIZE = "hbase.client.hedged.thread.pool.size";
    private static final String HBASE_CLIENT_HEDGED_READ_POOL_DEFAULT_SIZE = "10";

    private static final String HEDGED_READ_CONF_SUFFIX = ".hedged.read";
    private Properties properties;
    private Properties hedgedClusterProp;

    public AbstractHBaseBaseAdapter(Properties properties) {
        MyAssert.checkNotNull(properties, "The properties is not empty!");
        this.properties = properties;
        this.hedgedClusterProp = createHedgedReadClusterProp();
    }

    public AbstractHBaseBaseAdapter(Configuration configuration) {
        Iterator<Map.Entry<String, String>> propIterator = configuration.iterator();
        Properties properties = new Properties();
        while (propIterator.hasNext()) {
            Map.Entry<String, String> next = propIterator.next();
            properties.put(next.getKey(), next.getValue());
        }
        this.properties = properties;
        this.hedgedClusterProp = createHedgedReadClusterProp();
    }

    public AbstractHBaseBaseAdapter(String zkHost, String zkPort) {
        Properties properties = new Properties();
        properties.put(HConstants.ZOOKEEPER_QUORUM, zkHost);
        properties.put(HConstants.ZOOKEEPER_CLIENT_PORT, zkPort);
        this.properties = properties;
    }

    @Override
    public Connection getConnection() {
        return HBaseConnectionManager.getConnection(this.properties);
    }

    @Override
    public BufferedMutator getBufferedMutator(String tableName) {
        return HBaseConnectionManager.getBufferedMutator(tableName, this.getConnection());
    }

    @Override
    public Connection getHedgedReadClusterConnection() {
        return HBaseConnectionManager.getConnection(this.hedgedClusterProp);
    }

    @Override
    public BufferedMutator getHedgedReadClusterBufferedMutator(String tableName) {
        return HBaseConnectionManager.getBufferedMutator(tableName, this.getHedgedReadClusterConnection());
    }

    public Configuration getConfiguration() {
        return HBaseConnectionManager.getConfiguration(this.properties);
    }

    public Configuration getHedgedReadClusterConfiguration() {
        return HBaseConnectionManager.getConfiguration(this.hedgedClusterProp);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    protected void tableIsNotExistsThrowError(boolean tableIsNotExists, String msg) {
        if (tableIsNotExists) {
            throw new HBaseSdkTableIsNotExistsException(msg);
        }
    }

    protected void tableIsExistsThrowError(boolean tableIsExists, String msg) {
        if (tableIsExists) {
            throw new HBaseSdkTableIsExistsException(msg);
        }
    }

    protected void tableIsNotDisableThrowError(boolean tableIsDisabled, String msg) {
        if (!tableIsDisabled) {
            throw new HBaseSdkTableIsNotDisabledException(msg);
        }
    }

    @Override
    public boolean hedgedReadIsOpen() {
        return "true".equalsIgnoreCase(this.properties.getProperty(HBASE_CLIENT_HEDGED_READ_SWITCH,
                HBASE_CLIENT_HEDGED_READ_SWITCH_DEFAULT));
    }

    @Override
    public long hedgedReadTimeout() {
        return Long.parseLong(this.properties.getProperty(HBASE_CLIENT_HEDGED_READ_TIME_OUT,
                HBASE_CLIENT_HEDGED_READ_TIME_OUT_DEFAULT_MS));
    }

    @Override
    public int initHedgedReadPoolSize() {
        return Integer.parseInt(this.properties.getProperty(HBASE_CLIENT_HEDGED_READ_POOL_SIZE,
                HBASE_CLIENT_HEDGED_READ_POOL_DEFAULT_SIZE));
    }

    private Properties createHedgedReadClusterProp() {
        Properties prop = this.properties;
        for (Object o : this.properties.keySet()) {
            String key = o.toString();
            if (key.endsWith(HEDGED_READ_CONF_SUFFIX)) {
                String value = prop.getProperty(key);
                prop.setProperty(key.substring(0, key.lastIndexOf(HEDGED_READ_CONF_SUFFIX)), value);
            }
        }
        return prop;
    }
}

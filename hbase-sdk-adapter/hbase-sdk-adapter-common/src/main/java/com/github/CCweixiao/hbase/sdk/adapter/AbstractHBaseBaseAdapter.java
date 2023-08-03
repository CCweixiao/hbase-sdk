package com.github.CCweixiao.hbase.sdk.adapter;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsExistsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsNotDisabledException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsNotExistsException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionManager;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionManagerTest;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.yetus.audience.InterfaceAudience;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import static com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys.*;

/**
 * @author leojie 2020/11/13 11:52 下午
 */
@InterfaceAudience.Private
public abstract class AbstractHBaseBaseAdapter implements IHBaseBaseAdapter {
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
        return HBaseConnectionManager.getInstance().getConnection(this.getProperties());
    }

    @Override
    public BufferedMutator getBufferedMutator(String tableName) {
        return HBaseConnectionManager.getInstance().getBufferedMutator(tableName, this.getProperties());
    }

    @Override
    public Connection getHedgedReadClusterConnection() {
        return HBaseConnectionManager.getInstance().getConnection(this.getHedgedClusterProp());
    }

    @Override
    public BufferedMutator getHedgedReadClusterBufferedMutator(String tableName) {
        return HBaseConnectionManager.getInstance().getBufferedMutator(tableName, this.getHedgedClusterProp());
    }

    public Properties getProperties() {
        return properties;
    }

    public Configuration getConfiguration() {
        return HBaseConnectionManager.getInstance().getConfiguration(this.getProperties());
    }

    public Properties getHedgedClusterProp() {
        return hedgedClusterProp;
    }

    public Configuration getHedgedReadClusterConfiguration() {
        return HBaseConnectionManager.getInstance().getConfiguration(this.getHedgedClusterProp());
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
        return "true".equalsIgnoreCase(this.getProperties().getProperty(HBASE_CLIENT_HEDGED_READ_SWITCH,
                HBASE_CLIENT_HEDGED_READ_SWITCH_DEFAULT));
    }

    @Override
    public boolean hedgedReadWriteDisable() {
        return "true".equalsIgnoreCase(this.getProperties().getProperty(HEDGED_READ_WRITE_DISABLE,
                HBASE_CLIENT_HEDGED_READ_WRITE_DISABLE));
    }

    @Override
    public long hedgedReadThresholdMillis() {
        return Long.parseLong(this.getProperties().getProperty(HBASE_CLIENT_HEDGED_READ_TIME_OUT,
                HBASE_CLIENT_HEDGED_READ_TIME_OUT_DEFAULT_MS));
    }

    @Override
    public int initHedgedReadPoolSize() {
        return Integer.parseInt(this.getProperties().getProperty(HBASE_CLIENT_HEDGED_READ_POOL_SIZE,
                HBASE_CLIENT_HEDGED_READ_POOL_DEFAULT_SIZE));
    }

    private Properties createHedgedReadClusterProp() {
        Properties prop = new Properties();
        for (Object o : this.getProperties().keySet()) {
            String key = o.toString();
            if (key.endsWith(HEDGED_READ_CONF_SUFFIX)) {
                continue;
            }
            prop.setProperty(key, this.getProperties().getProperty(key));
        }

        for (Object o : this.getProperties().keySet()) {
            String key = o.toString();
            if (key.endsWith(HEDGED_READ_CONF_SUFFIX)) {
                String value = this.getProperties().getProperty(key);
                key = key.substring(0, key.lastIndexOf(HEDGED_READ_CONF_SUFFIX));
                prop.setProperty(key, value);
            }
        }
        return prop;
    }

    protected int getClientScannerCaching() {
        Configuration configuration = this.getConfiguration();
        if (configuration == null) {
            return HBASE_CLIENT_DEFAULT_SCANNER_CACHING;
        }
        return configuration.getInt(HBASE_CLIENT_SCANNER_CACHING, HBASE_CLIENT_DEFAULT_SCANNER_CACHING);
    }
}

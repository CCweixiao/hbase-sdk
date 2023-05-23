package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsExistsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsNotDisabledException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkTableIsNotExistsException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.connection.ConnectionFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.yetus.audience.InterfaceAudience;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2020/11/13 11:52 下午
 */
@InterfaceAudience.Private
public abstract class AbstractHBaseBaseAdapter implements IHBaseBaseAdapter {
    private Properties properties;

    public AbstractHBaseBaseAdapter(Properties properties) {
        MyAssert.checkNotNull(properties, "The properties is not empty!");
        this.properties = properties;
    }

    public AbstractHBaseBaseAdapter(Configuration configuration) {
        Iterator<Map.Entry<String, String>> propIterator = configuration.iterator();
        Properties properties = new Properties();
        while (propIterator.hasNext()) {
            Map.Entry<String, String> next = propIterator.next();
            properties.put(next.getKey(), next.getValue());
        }
        this.properties = properties;
    }

    public AbstractHBaseBaseAdapter(String zkHost, String zkPort) {
        Properties properties = new Properties();
        properties.put(HConstants.ZOOKEEPER_QUORUM, zkHost);
        properties.put(HConstants.ZOOKEEPER_CLIENT_PORT, zkPort);
        this.properties = properties;
    }

    @Override
    public Connection getConnection() {
        return ConnectionFactory.getConnection(this.properties);
    }

    public Configuration getConfiguration() {
        return ConnectionFactory.getConfiguration(this.properties);
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
}

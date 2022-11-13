package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.connection.ConnectionFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2020/11/13 11:52 下午
 */
public abstract class AbstractHBaseOperations implements IHBaseOperations {
    private Properties properties;

    public AbstractHBaseOperations(Properties properties) {
        this.properties = properties;
        if (this.properties == null) {
            throw new HBaseOperationsException("a valid configuration is provided.");
        }
    }

    public AbstractHBaseOperations(String zkHost, String zkPort) {
        Properties properties = new Properties();
        properties.put(HConstants.ZOOKEEPER_QUORUM, zkHost);
        properties.put(HConstants.ZOOKEEPER_CLIENT_PORT, zkPort);
        this.properties = properties;
    }

    public AbstractHBaseOperations(Configuration configuration) {
        Iterator<Map.Entry<String, String>> propIterator = configuration.iterator();
        Properties properties = new Properties();
        while (propIterator.hasNext()) {
            Map.Entry<String, String> next = propIterator.next();
            properties.put(next.getKey(), next.getValue());
        }
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
}

package com.github.CCweixiao.hbase.sdk.adapter_14;

import com.github.CCweixiao.hbase.sdk.adapter_14.connection.ConnectionFactory;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2020/11/13 11:52 下午
 */
public abstract class AbstractHBaseConfig implements HBaseOperations {
    private Properties properties;

    public AbstractHBaseConfig(Properties properties) {
        this.properties = properties;
        if (this.properties == null) {
            throw new HBaseOperationsException("a valid configuration is provided.");
        }
    }

    public AbstractHBaseConfig(String zkHost, String zkPort) {
        Properties properties = new Properties();
        properties.put(HConstants.ZOOKEEPER_QUORUM, zkHost);
        properties.put(HConstants.ZOOKEEPER_CLIENT_PORT, zkPort);
        this.properties = properties;
    }

    public AbstractHBaseConfig(Configuration configuration) {
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
        return ConnectionFactory.getConfiguration(properties);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}

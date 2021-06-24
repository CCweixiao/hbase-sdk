package com.github.CCweixiao;

import com.github.CCweixiao.connection.ConfigurationFactory;
import com.github.CCweixiao.connection.MultipleConnectionFactory;
import com.github.CCweixiao.exception.HBaseOperationsException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;

import java.util.Properties;

/**
 * @author leojie 2020/11/13 11:52 下午
 */
public abstract class AbstractHBaseConfig implements HBaseOperations {
    private Configuration configuration;

    public AbstractHBaseConfig(Configuration configuration) {
        this.configuration = configuration;
        if (this.configuration == null) {
            throw new HBaseOperationsException("a valid configuration is provided.");
        }
    }

    public AbstractHBaseConfig(String zkHost, String zkPort) {
        this.configuration = ConfigurationFactory.getConfiguration(zkHost, zkPort);
    }

    public AbstractHBaseConfig(Properties properties) {
        this.configuration = ConfigurationFactory.getConfiguration(properties);
    }


    @Override
    public Connection getConnection() {
        return MultipleConnectionFactory.getConnection(this.configuration);
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}

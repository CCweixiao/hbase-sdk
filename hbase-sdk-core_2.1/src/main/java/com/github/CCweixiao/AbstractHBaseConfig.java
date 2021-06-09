package com.github.CCweixiao;

import com.github.CCweixiao.connection.ConfigurationFactory;
import com.github.CCweixiao.connection.SingleConnectionFactory;
import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.exception.HBaseSdkConnectionException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
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
        return SingleConnectionFactory.getConnection(this.configuration);
//        try {
//            return ConnectionFactory.createConnection(configuration);
//        } catch (IOException e) {
//            throw new HBaseSdkConnectionException(e);
//        }
        // LOGGER.info("the connection of HBase is created successfully.>>>>>>>>>>>>>>>>>>");
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}

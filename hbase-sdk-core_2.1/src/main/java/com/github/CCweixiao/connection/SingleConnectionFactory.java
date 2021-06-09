package com.github.CCweixiao.connection;

import com.github.CCweixiao.exception.HBaseSdkConnectionException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author leojie 2021/2/9 11:15 下午
 */
public class SingleConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleConnectionFactory.class);

    private volatile static Connection connection;

    private SingleConnectionFactory() {

    }

    public static Connection getConnection(Configuration configuration) {
        if (connection == null) {
            synchronized (SingleConnectionFactory.class) {
                if (connection == null) {
                    try {
                        connection = ConnectionFactory.createConnection(configuration);
                        LOGGER.info("the connection of HBase is created successfully.>>>>>>>>>>>>>>>>>>");
                    } catch (IOException e) {
                        LOGGER.error("the connection of HBase is created failed.>>>>>>>>>>>>>>>>>");
                        throw new HBaseSdkConnectionException(e);
                    }
                }
            }
        }
        return connection;
    }
}

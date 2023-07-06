package com.github.weixiao.hbase.sdk.connection;

import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionManager;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import java.util.Properties;

/**
 * @author leojie 2021/2/9 11:15 下午
 */
public class HBaseConnectionManagerRuby {
    public static Connection getConnection(Properties prop) {
        return HBaseConnectionManager.getConnection(prop);
    }

    public static Configuration getConfiguration(Properties prop) {
        return HBaseConnectionManager.getConfiguration(prop);
    }
}


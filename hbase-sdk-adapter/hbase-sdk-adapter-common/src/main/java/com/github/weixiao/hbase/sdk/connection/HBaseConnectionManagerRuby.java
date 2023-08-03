package com.github.weixiao.hbase.sdk.connection;

import com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkConnectionException;
import com.github.CCweixiao.hbase.sdk.common.security.AuthType;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionManager;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author leojie 2021/2/9 11:15 下午
 */
public class HBaseConnectionManagerRuby {
    public static Connection getConnection(Properties prop) {
        Configuration configuration = getConfiguration(prop);
        return HBaseConnectionManager.getInstance().getConnection(configuration);
    }

    public static Configuration getConfiguration(Properties properties) {
        AuthType auth = getAuthType(properties.getProperty(HBaseConfigKeys.HBASE_SECURITY_AUTH, ""));
        Configuration configuration = HBaseConfiguration.create();
        final List<String> keys = properties.keySet().stream().map(Object::toString).collect(Collectors.toList());
        switch (auth) {
            case SIMPLE:
                keys.forEach(key -> configuration.set(key, properties.getProperty(key)));
                break;
            case KERBEROS:
                keys.forEach(key -> {
                    if (key.startsWith(HBaseConfigKeys.JAVA_SECURITY_PREFIX)) {
                        System.setProperty(key, properties.getProperty(key));
                    } else {
                        configuration.set(key, properties.getProperty(key));
                    }
                });
                break;
            default:
                break;
        }
        return configuration;
    }

    public static String uniqueShellSessionConnectionId(Properties properties) {
        Configuration configuration = getConfiguration(properties);
        return HBaseConnectionUtil.generateUniqueConnectionKey(configuration);
    }

    private static AuthType getAuthType(String auth) {
        if (StringUtil.isBlank(auth)) {
            return AuthType.SIMPLE;
        }
        for (AuthType value : AuthType.values()) {
            if (auth.equals(value.getAuthType())) {
                return value;
            }
        }
        throw new HBaseSdkConnectionException("Auth type " + auth + " is not supported.");
    }
}


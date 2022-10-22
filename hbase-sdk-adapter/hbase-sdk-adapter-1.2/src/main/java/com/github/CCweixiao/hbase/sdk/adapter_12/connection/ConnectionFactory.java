package com.github.CCweixiao.hbase.sdk.adapter_12.connection;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkConnectionException;
import com.github.CCweixiao.hbase.sdk.common.security.AuthType;
import com.github.CCweixiao.hbase.sdk.common.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author leojie 2021/2/9 11:15 下午
 */
public class ConnectionFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);

    private volatile static Map<String, Connection> connectionMap;
    private volatile static boolean kerberosEnvInit = false;
    private static final int KERBEROS_KINIT_MAX_RETRY = 5;
    private static final long KERBEROS_RE_LOGIN_INTERVAL = 30 * 60 * 1000L;

    private ConnectionFactory() {
    }

    public static Connection getConnection(Properties prop) {
        String cluster = prop.getProperty(HConstants.ZOOKEEPER_QUORUM);

        if (connectionMap == null || !connectionMap.containsKey(cluster)) {
            synchronized (ConnectionFactory.class) {
                if (connectionMap == null || !connectionMap.containsKey(cluster)) {
                    try {
                        if (connectionMap == null) {
                            connectionMap = new HashMap<>(2);
                        }
                        if (!connectionMap.containsKey(cluster)) {
                            Configuration configuration = getConfiguration(prop);
                            if (!kerberosEnvInit) {
                                doKerberosLogin(configuration, prop);
                                kerberosEnvInit = true;
                            } else {
                                LOGGER.info("Kerberos login has prepared.");
                            }
                            Connection connection = org.apache.hadoop.hbase.client.ConnectionFactory.createConnection(configuration);
                            LOGGER.info("the connection of HBase cluster [{}] is created successfully.>>>>>>>>>>>>>>>>>>", cluster);
                            connectionMap.put(cluster, connection);
                        }
                    } catch (IOException e) {
                        LOGGER.error("the connection of HBase is created failed.>>>>>>>>>>>>>>>>>");
                        throw new HBaseSdkConnectionException(e);
                    }
                }
            }
        }
        return connectionMap.get(cluster);
    }

    public static Configuration getConfiguration(Properties properties) {
        AuthType auth = getAuthType(properties.getProperty("hbase.security.authentication", ""));
        Configuration configuration = HBaseConfiguration.create();
        final List<String> keys = properties.keySet().stream().map(Object::toString).collect(Collectors.toList());
        switch (auth) {
            case SIMPLE:
                keys.forEach(key -> configuration.set(key, properties.getProperty(key)));
                break;
            case KERBEROS:
                configuration.set("hadoop.security.authentication", "kerberos");
                configuration.set("hbase.security.authentication", "kerberos");
                keys.forEach(key -> {
                    if (key.startsWith("java.security")) {
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

    private static void doKerberosLogin(Configuration configuration, Properties properties) {
        if (!isKerberosAuthType(configuration)) {
            return;
        }
        String principal = properties.getProperty("kerberos.principal");
        if (StrUtil.isBlank(principal)) {
            throw new HBaseSdkConnectionException("Kerberos principal is not empty.");
        }
        String keytabFilePath = properties.getProperty("keytab.file");
        if (StrUtil.isBlank(keytabFilePath)) {
            throw new HBaseSdkConnectionException("keytab file path is not empty.");
        }
        try {
            UserGroupInformation.setConfiguration(configuration);
            UserGroupInformation.loginUserFromKeytabAndReturnUGI(principal, keytabFilePath);
            LOGGER.info("HBase kerberos auth successfully!");
            doKerberosReLogin();
            LOGGER.info("HBase kerberos re login start working ......");
        } catch (IOException e) {
            throw new HBaseOperationsException(e);
        }
    }

    private static void doKerberosReLogin() {
        if (!UserGroupInformation.isSecurityEnabled()) {
            return;
        }
        Thread reLoginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(KERBEROS_RE_LOGIN_INTERVAL);
                    } catch (InterruptedException e) {
                        LOGGER.warn("", e);
                    }
                    int times = 0;

                    while (times < KERBEROS_KINIT_MAX_RETRY) {
                        try {
                            UserGroupInformation.getLoginUser().checkTGTAndReloginFromKeytab();
                            LOGGER.info("Kerberos re login successfully!");
                            break;
                        } catch (IOException e) {
                            LOGGER.warn("Kerberos re login {} times.", times, e);
                        }
                        times++;
                    }
                }
            }
        });
        reLoginThread.setDaemon(true);
        reLoginThread.start();
    }

    private static AuthType getAuthType(String auth) {
        if (StrUtil.isBlank(auth)) {
            return AuthType.SIMPLE;
        }
        for (AuthType value : AuthType.values()) {
            if (auth.equals(value.getAuthType())) {
                return value;
            }
        }
        throw new HBaseSdkConnectionException("Auth type " + auth + " is not supported.");
    }

    private static boolean isKerberosAuthType(Configuration configuration) {
        String authType = configuration.get("hbase.security.authentication", "");
        if (StrUtil.isBlank(authType)) {
            return false;
        }
        return "kerberos".equalsIgnoreCase(authType);
    }
}

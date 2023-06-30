package com.github.CCweixiao.hbase.sdk.connection;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkConnectionException;
import com.github.CCweixiao.hbase.sdk.common.security.AuthType;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.security.UserGroupInformation;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author leojie 2021/2/9 11:15 下午
 */
public class HBaseConnectionManager {
    private volatile static Map<String, Connection> connectionMap;
    private volatile static boolean kerberosEnvInit = false;
    private static final int KERBEROS_KINIT_MAX_RETRY = 5;
    private static final long KERBEROS_RE_LOGIN_INTERVAL = 30 * 60 * 1000L;

    private HBaseConnectionManager() {
    }

    public static Connection getConnection(Properties prop) {
        String cluster = prop.getProperty(HConstants.ZOOKEEPER_QUORUM);

        if (connectionMap == null || !connectionMap.containsKey(cluster)) {
            synchronized (HBaseConnectionManager.class) {
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
                            }
                            Connection connection = org.apache.hadoop.hbase.client.ConnectionFactory.createConnection(configuration);
                            connectionMap.put(cluster, connection);
                        }
                    } catch (IOException e) {
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
        if (StringUtil.isBlank(principal)) {
            throw new HBaseSdkConnectionException("Kerberos principal is not empty.");
        }
        String keytabFilePath = properties.getProperty("keytab.file");
        if (StringUtil.isBlank(keytabFilePath)) {
            throw new HBaseSdkConnectionException("keytab file path is not empty.");
        }
        try {
            UserGroupInformation.setConfiguration(configuration);
            UserGroupInformation.loginUserFromKeytab(principal, keytabFilePath);
            doKerberosReLogin();
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
                    } catch (InterruptedException e) {}
                    int times = 0;

                    while (times < KERBEROS_KINIT_MAX_RETRY) {
                        try {
                            UserGroupInformation.getLoginUser().checkTGTAndReloginFromKeytab();
                            break;
                        } catch (IOException e) {}
                        times++;
                    }
                }
            }
        });
        reLoginThread.setDaemon(true);
        reLoginThread.start();
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

    private static boolean isKerberosAuthType(Configuration configuration) {
        String authType = configuration.get("hbase.security.authentication", "");
        if (StringUtil.isBlank(authType)) {
            return false;
        }
        return "kerberos".equalsIgnoreCase(authType);
    }
}


package com.github.CCweixiao.hbase.sdk.connection;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkConnectionException;
import com.github.CCweixiao.hbase.sdk.common.security.AuthType;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leojie 2021/2/9 11:15 下午
 */
public class HBaseConnectionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseConnectionManager.class);
    private volatile static Map<String, Connection> connectionMap;
    private volatile static boolean kerberosEnvInit = false;
    private static final int KERBEROS_RE_LOGIN_MAX_RETRY = 5;
    private static final long KERBEROS_RE_LOGIN_INTERVAL = 30 * 60 * 1000L;

    private static final String KERBEROS = "kerberos";
    private static final String KERBEROS_PRINCIPAL = "kerberos.principal";
    private static final String KERBEROS_KEYTAB_FILE = "keytab.file";
    private static final String JAVA_SECURITY_PREFIX = "java.security";

    private HBaseConnectionManager() {
    }

    public static Connection getConnection(Properties prop) {
        String clusterConnUniqueKey = HBaseConnectionUtil.generateUniqueConnectionKey(prop);

        if (connectionMap == null || !connectionMap.containsKey(clusterConnUniqueKey)) {
            synchronized (HBaseConnectionManager.class) {
                if (connectionMap == null || !connectionMap.containsKey(clusterConnUniqueKey)) {
                    try {
                        if (connectionMap == null) {
                            connectionMap = new HashMap<>(2);
                        }
                        if (!connectionMap.containsKey(clusterConnUniqueKey)) {
                            Configuration configuration = getConfiguration(prop);
                            if (!kerberosEnvInit) {
                                doKerberosLogin(configuration, prop);
                            }
                            Connection connection;
                            if (HBaseConnectionUtil.isProxyUserEnabled(prop)) {
                                String proxyUser = HBaseConnectionUtil.proxyUser(prop);
                                UserGroupInformation ugi =
                                        UserGroupInformation.createProxyUser(proxyUser, UserGroupInformation.getLoginUser());
                                connection = ugi.doAs((PrivilegedAction<Connection>) () -> {
                                    try {
                                        return ConnectionFactory.createConnection(configuration);
                                    } catch (IOException e) {
                                        throw new HBaseSdkConnectionException(e);
                                    }
                                });
                            } else {
                                connection = ConnectionFactory.createConnection(configuration);
                            }
                            connectionMap.put(clusterConnUniqueKey, connection);
                        }
                    } catch (IOException e) {
                        throw new HBaseSdkConnectionException(e);
                    }
                }
            }
        }
        return connectionMap.get(clusterConnUniqueKey);
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
                    if (key.startsWith(JAVA_SECURITY_PREFIX)) {
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
        String principal = properties.getProperty(KERBEROS_PRINCIPAL);
        if (StringUtil.isBlank(principal)) {
            throw new HBaseSdkConnectionException("The kerberos principal is not empty.");
        }
        String keytab = properties.getProperty(KERBEROS_KEYTAB_FILE);
        if (StringUtil.isBlank(keytab)) {
            throw new HBaseSdkConnectionException("The keytab file path is not empty.");
        }
        File file = new File(keytab);
        if (!file.exists()) {
            throw new HBaseSdkConnectionException("The keytab file is not exists.");
        }
        if (!file.isFile()) {
            throw new HBaseSdkConnectionException("The keytab file is not a file.");
        }
        try {
            UserGroupInformation.setConfiguration(configuration);
            UserGroupInformation.loginUserFromKeytab(principal, keytab);
            LOGGER.info("Login successfully via keytab: {} and principal: {}", keytab, principal);
            kerberosEnvInit = true;
            doKerberosReLogin();
        } catch (IOException e) {
            throw new HBaseSdkConnectionException(e);
        }
    }

    private static boolean runKerberosLogin() {
        Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("hadoop.security.authentication", KERBEROS);
        UserGroupInformation.setConfiguration(conf);
        try {
            if (UserGroupInformation.isLoginKeytabBased()) {
                LOGGER.info("Trying re login from keytab.");
                UserGroupInformation.getLoginUser().reloginFromKeytab();
                return true;
            } else if (UserGroupInformation.isLoginTicketBased()) {
                LOGGER.info("Trying re login from ticket cache");
                UserGroupInformation.getLoginUser().reloginFromTicketCache();
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("Unable to run kinit.", e);
        }
        return false;
    }

    private static void doKerberosReLogin() {
        if (!UserGroupInformation.isSecurityEnabled()) {
            return;
        }

        Thread reLoginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int times = 0;

                    while (times < KERBEROS_RE_LOGIN_MAX_RETRY) {
                        if (runKerberosLogin()) {
                            LOGGER.info("Ran kerberos re login command successfully.");
                            break;
                        } else {
                            times++;
                            LOGGER.info("Run kerberos re login failed for {} time(s).", times);
                        }
                    }
                    try {
                        Thread.sleep(KERBEROS_RE_LOGIN_INTERVAL);
                    } catch (InterruptedException e) {
                        LOGGER.warn("Ignore error", e);
                    }
                }
            }
        });
        reLoginThread.setName("KerberosReLoginThread");
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
        return KERBEROS.equalsIgnoreCase(authType);
    }


}


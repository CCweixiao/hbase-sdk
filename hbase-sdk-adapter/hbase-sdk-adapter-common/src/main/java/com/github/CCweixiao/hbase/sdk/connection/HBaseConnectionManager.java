package com.github.CCweixiao.hbase.sdk.connection;

import com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkConnectionException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.BufferedMutatorParams;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author leojie 2021/2/9 11:15 下午
 */
public class HBaseConnectionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseConnectionManager.class);
    private final Map<String, Connection> connectionMap;
    private final Map<String, BufferedMutator> bufferedMutatorMap;
    private final ReentrantLock lock = new ReentrantLock();
    private static final AtomicBoolean kerberosEnvInit = new AtomicBoolean(false);
    private static final int KERBEROS_RE_LOGIN_MAX_RETRY = 5;
    private static final long KERBEROS_RE_LOGIN_INTERVAL = 30 * 60 * 1000L;

    public static final String KERBEROS = "kerberos";

    private static volatile HBaseConnectionManager instance = null;


    private HBaseConnectionManager() {
        connectionMap = new ConcurrentHashMap<>();
        bufferedMutatorMap = new ConcurrentHashMap<>();
    }

    public static HBaseConnectionManager getInstance() {
        if (instance == null) {
            synchronized (HBaseConnectionManager.class) {
                if (instance == null) {
                    instance = new HBaseConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection(Configuration configuration) {
        String clusterConnUniqueKey = HBaseConnectionUtil.generateUniqueConnectionKey(configuration);
        LOGGER.info("Start to get connection for cluster {}:{}:{}.",
                configuration.get(HBaseConfigKeys.ZOOKEEPER_QUORUM),
                configuration.get(HBaseConfigKeys.ZOOKEEPER_CLIENT_PORT),
                clusterConnUniqueKey);

        try {
            lock.lock();
            if (!connectionMap.containsKey(clusterConnUniqueKey)) {
                if (isKerberosAuthType(configuration) && kerberosEnvInit.compareAndSet(false, true)) {
                    doKerberosLogin(configuration);
                }

                Connection connection;
                if (HBaseConnectionUtil.isProxyUserEnabled(configuration)) {
                    String proxyUser = HBaseConnectionUtil.proxyUser(configuration);
                    UserGroupInformation ugi =
                            UserGroupInformation.createProxyUser(proxyUser, UserGroupInformation.getLoginUser());
                    connection = ugi.doAs((PrivilegedAction<Connection>) () -> {
                        try {
                            return ConnectionFactory.createConnection(configuration);
                        } catch (IOException e) {
                            throw new HBaseSdkConnectionException(e);
                        }
                    });
                    LOGGER.info("Successfully create a connection {} and proxy user {}" , connection, proxyUser);
                } else {
                    connection = ConnectionFactory.createConnection(configuration);
                    LOGGER.info("Successfully create a connection {}.", connection);
                }
                connectionMap.putIfAbsent(clusterConnUniqueKey, connection);
                return connection;
            }
        } catch (IOException e) {
            throw new HBaseSdkConnectionException(e);
        } finally {
            lock.unlock();
        }
        return connectionMap.get(clusterConnUniqueKey);
    }

    public BufferedMutator getBufferedMutator(String tableName, Configuration configuration) {
        if (StringUtil.isBlank(tableName)) {
            throw new IllegalArgumentException("The table name cannot be empty.");
        }
        String uniqueConnectionKey = HBaseConnectionUtil.generateUniqueConnectionKey(configuration, tableName);

        try {
            lock.lock();
            if (!bufferedMutatorMap.containsKey(uniqueConnectionKey)) {
                BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
                BufferedMutator mutator = this.getConnection(configuration).getBufferedMutator(mutatorParams);
                bufferedMutatorMap.putIfAbsent(tableName, mutator);
                return mutator;
            }
        } catch (IOException e) {
            throw new HBaseSdkConnectionException(e);
        } finally {
            lock.unlock();
        }
        return bufferedMutatorMap.get(uniqueConnectionKey);
    }

    private void doKerberosLogin(Configuration configuration) {
        String principal = configuration.get(HBaseConfigKeys.KERBEROS_PRINCIPAL);
        if (StringUtil.isBlank(principal)) {
            kerberosEnvInit.set(false);
            throw new HBaseSdkConnectionException("The kerberos principal is not empty.");
        }
        String keytab = configuration.get(HBaseConfigKeys.KERBEROS_KEYTAB_FILE);
        if (StringUtil.isBlank(keytab)) {
            kerberosEnvInit.set(false);
            throw new HBaseSdkConnectionException("The keytab file path is not empty.");
        }
        File file = new File(keytab);
        if (!file.exists()) {
            kerberosEnvInit.set(false);
            throw new HBaseSdkConnectionException("The keytab file is not exists.");
        }
        if (!file.isFile()) {
            kerberosEnvInit.set(false);
            throw new HBaseSdkConnectionException("The keytab file is not a file.");
        }
        try {
            configuration.set(HBaseConfigKeys.HADOOP_SECURITY_AUTH, KERBEROS);
            UserGroupInformation.setConfiguration(configuration);
            UserGroupInformation.loginUserFromKeytab(principal, keytab);
            LOGGER.info("Login successfully via keytab: {} and principal: {}", keytab, principal);
            doKerberosReLogin();
        } catch (IOException e) {
            kerberosEnvInit.set(false);
            throw new HBaseSdkConnectionException(e);
        }
    }

    private boolean runKerberosLogin() {
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

    private void doKerberosReLogin() {
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

    private boolean isKerberosAuthType(Configuration configuration) {
        String authType = configuration.get(HBaseConfigKeys.HBASE_SECURITY_AUTH, "");
        if (StringUtil.isBlank(authType)) {
            return false;
        }
        return KERBEROS.equalsIgnoreCase(authType);
    }

    public void destroy() {
        try {
            for (BufferedMutator mutator : bufferedMutatorMap.values()) {
                mutator.close();
            }
            bufferedMutatorMap.clear();
            for (Connection connection : connectionMap.values()) {
                connection.close();
            }
            connectionMap.clear();
        } catch (IOException e) {
            LOGGER.warn("An exception occurred while destroy resources.", e);
        }

    }

}


package com.github.CCweixiao.connection;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.exception.HBaseSdkUnsupportedAuthTypeException;
import com.github.CCweixiao.util.StrUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.github.CCweixiao.constant.HMHBaseConstant.KERBEROS_AUTH;

/**
 * @author leojie 2020/12/11 11:30 下午
 */
public class ConfigurationFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationFactory.class);

    private ConfigurationFactory() {
    }

    public static Configuration getConfiguration(Properties properties) {
        String auth = properties.getProperty("hbase.security.authentication", "");
        Configuration configuration = HBaseConfiguration.create();

        if (StrUtil.isBlank(auth)) {
            final List<String> keys = properties.keySet().stream().map(Object::toString).collect(Collectors.toList());
            keys.forEach(key -> configuration.set(key, properties.getProperty(key)));
        } else {
            if (KERBEROS_AUTH.equalsIgnoreCase(auth)) {
                System.setProperty("java.security.krb5.conf", properties.getProperty("java.security.krb5.conf"));
                final List<String> keys = properties.keySet().stream().map(Object::toString)
                        .filter(prop -> !prop.equals("java.security.krb5.conf"))
                        .collect(Collectors.toList());
                keys.forEach(key -> configuration.set(key, properties.getProperty(key)));
                UserGroupInformation.setConfiguration(configuration);

                try {
                    UserGroupInformation ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(properties.getProperty("kerberos.principal"),
                            properties.getProperty("keytab.file"));
                    UserGroupInformation.setLoginUser(ugi);
                    LOG.info("hbase kerberos auth successfully!");
                } catch (IOException e) {
                    throw new HBaseOperationsException(e);
                }
            } else {
                throw new HBaseSdkUnsupportedAuthTypeException("this type of hbase authentication " + auth + " is unsupported.");
            }
        }

        return configuration;
    }

    public static Configuration getConfiguration(String zkHost, String zkPort) {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set(HConstants.ZOOKEEPER_QUORUM, zkHost);
        configuration.set(HConstants.ZOOKEEPER_CLIENT_PORT, zkPort);
        return configuration;
    }

}

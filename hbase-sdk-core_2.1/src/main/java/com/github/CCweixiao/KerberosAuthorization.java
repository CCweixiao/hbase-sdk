package com.github.CCweixiao;

import com.github.CCweixiao.exception.HBaseOperationsException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author leojie 2020/12/11 11:30 下午
 */
public enum KerberosAuthorization {
    INSTANCE;
    private Configuration instance;

    KerberosAuthorization() {
    }

    public Configuration getInstance(Properties properties) {
        instance = HBaseConfiguration.create();
        System.setProperty("java.security.krb5.conf", properties.getProperty("java.security.krb5.conf"));
        final List<String> keys = properties.keySet().stream().map(Object::toString)
                .filter(prop -> !prop.equals("java.security.krb5.conf"))
                .collect(Collectors.toList());
        keys.forEach(key -> instance.set(key, properties.getProperty(key)));
        UserGroupInformation.setConfiguration(instance);

        try {
            UserGroupInformation ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(properties.getProperty("kerberos.principal"),
                    properties.getProperty("keytab.file"));
            UserGroupInformation.setLoginUser(ugi);
        } catch (IOException e) {
            throw new HBaseOperationsException(e);
        }
        return instance;
    }

    public Configuration getInstance(Configuration configuration) {
        instance = configuration;
        System.setProperty("java.security.krb5.conf", configuration.get("java.security.krb5.conf"));
        UserGroupInformation.setConfiguration(configuration);

        try {
            UserGroupInformation.loginUserFromKeytab(configuration.get("kerberos.principal"), configuration.get("keytab.file"));
        } catch (IOException e) {
            throw new HBaseOperationsException(e);
        }
        return instance;
    }


}

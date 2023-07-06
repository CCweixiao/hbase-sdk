package com.github.CCweixiao.hbase.sdk.connection;

import cn.hutool.crypto.digest.MD5;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkConnectionException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.hbase.HConstants;

import java.util.Properties;

/**
 * @author leojie 2023/7/6 08:18
 */
public class HBaseConnectionUtil {
    private static final String KERBEROS_PROXY_USER = "kerberos.proxy.user";

    public static String generateUniqueConnectionKey(Properties properties) {
        String zkQuorum = properties.getProperty(HConstants.ZOOKEEPER_QUORUM);
        String zkClientPort = properties.getProperty(HConstants.ZOOKEEPER_CLIENT_PORT);
        if (StringUtil.isBlank(zkQuorum)) {
            throw new HBaseSdkConnectionException("The zkQuorum must be specified.");
        }
        if (StringUtil.isBlank(zkClientPort)) {
            throw new HBaseSdkConnectionException("The zkClientPort must be specified.");
        }
        zkQuorum = MD5.create().digestHex(zkQuorum.concat(zkClientPort));
        if (isProxyUserEnabled(properties)) {
            String proxyUser = proxyUser(properties);
            zkQuorum = zkQuorum + "#" + proxyUser;
        }
        return zkQuorum;
    }

    public static boolean isProxyUserEnabled(Properties properties) {
        String val = proxyUser(properties);
        return StringUtil.isNotBlank(val);
    }

    public static String proxyUser(Properties properties) {
        return properties.getProperty(KERBEROS_PROXY_USER);
    }
}

package com.github.CCweixiao.hbase.sdk.connection;

import com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys;
import com.github.CCweixiao.hbase.sdk.common.util.DigestUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.hbase.HConstants;

import java.util.Properties;

/**
 * @author leojie 2023/7/6 08:18
 */
public class HBaseConnectionUtil {

    public static String generateUniqueConnectionKey(Properties properties, String tableName) {
        return generateUniqueConnectionKey(properties).concat("#").concat(tableName);
    }

    public static String generateUniqueConnectionKey(Properties properties) {
        String zkQuorum = properties.getProperty(HConstants.ZOOKEEPER_QUORUM);
        String zkClientPort = properties.getProperty(HConstants.ZOOKEEPER_CLIENT_PORT);
        String proxyUser = "";
        if (isProxyUserEnabled(properties)) {
            proxyUser = proxyUser(properties);
        }
        return generateUniqueConnectionKey(zkQuorum, zkClientPort, proxyUser);
    }

    private static String generateUniqueConnectionKey(String zkQuorum, String zkClientPort, String proxyUser) {
        if (StringUtil.isBlank(zkQuorum)) {
            throw new IllegalArgumentException("The zkQuorum must be specified.");
        }
        if (StringUtil.isBlank(zkClientPort)) {
            throw new IllegalArgumentException("The zkClientPort must be specified.");
        }
        String uniqueKey = DigestUtil.md5Hex(zkQuorum.concat(zkClientPort));
        if (StringUtil.isNotBlank(proxyUser)) {
            uniqueKey = uniqueKey + "#" + proxyUser;
        }
        return uniqueKey;
    }

    public static boolean isProxyUserEnabled(Properties properties) {
        String val = proxyUser(properties);
        return StringUtil.isNotBlank(val);
    }

    public static String proxyUser(Properties properties) {
        return properties.getProperty(HBaseConfigKeys.KERBEROS_PROXY_USER);
    }
}

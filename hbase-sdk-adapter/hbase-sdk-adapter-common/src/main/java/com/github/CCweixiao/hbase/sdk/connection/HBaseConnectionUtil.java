package com.github.CCweixiao.hbase.sdk.connection;

import com.github.CCweixiao.hbase.sdk.common.constants.HBaseConfigKeys;
import com.github.CCweixiao.hbase.sdk.common.util.DigestUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;

import java.util.Properties;

/**
 * @author leojie 2023/7/6 08:18
 */
public class HBaseConnectionUtil {

    public static String generateUniqueConnectionKey(Configuration configuration, String tableName) {
        return generateUniqueConnectionKey(configuration).concat("#").concat(tableName);
    }

    public static String generateUniqueConnectionKey(Configuration configuration) {
        String zkQuorum = configuration.get(HConstants.ZOOKEEPER_QUORUM);
        String zkClientPort = configuration.get(HConstants.ZOOKEEPER_CLIENT_PORT);
        String proxyUser = "";
        if (isProxyUserEnabled(configuration)) {
            proxyUser = proxyUser(configuration);
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

    public static boolean isProxyUserEnabled(Configuration configuration) {
        String val = proxyUser(configuration);
        return StringUtil.isNotBlank(val);
    }

    public static String proxyUser(Configuration configuration) {
        return configuration.get(HBaseConfigKeys.KERBEROS_PROXY_USER);
    }
}

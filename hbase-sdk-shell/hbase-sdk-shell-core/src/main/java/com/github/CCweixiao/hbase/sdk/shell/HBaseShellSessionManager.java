package com.github.CCweixiao.hbase.sdk.shell;

import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCweixiao.hbase.sdk.connection.HBaseConnectionUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author leojie 2023/7/5 18:01
 */
public class HBaseShellSessionManager {
    private volatile static Map<String, HBaseShellSession> shellSessionMap;

    public static HBaseShellSession getHBaseShellSession(Properties prop) {
        String sessionId = generateUniqueSessionId(prop);

        if (shellSessionMap == null || !shellSessionMap.containsKey(sessionId)) {
            synchronized (HBaseShellSessionManager.class) {
                if (shellSessionMap == null || !shellSessionMap.containsKey(sessionId)) {
                    if (shellSessionMap == null) {
                        shellSessionMap = new HashMap<>(2);
                    }
                    if (!shellSessionMap.containsKey(sessionId)) {
                        HBaseShellSession shellSession = HBaseShellSession.sessionBuilder()
                                .sessionId(sessionId)
                                .sessionInitMaxTimes(HBaseShellSessionConfig.shellSessionConnectionRetryTimes(prop))
                                .sessionInitRetryInterval(HBaseShellSessionConfig.shellSessionConnectionRetryInterval(prop))
                                .sessionInitTimeout(HBaseShellSessionConfig.shellSessionConnectionTimeout(prop))
                                .sessionIdle(HBaseShellSessionConfig.shellSessionIdle(prop))
                                .sessionDebugLog(HBaseShellSessionConfig.shellSessionDebugLog(prop))
                                .properties(prop)
                                .build();
                        shellSession.open();
                        shellSessionMap.put(sessionId, shellSession);
                        return shellSession;
                    }
                }
            }
        }
       return shellSessionMap.get(sessionId);
    }

    private static String generateUniqueSessionId(Properties properties) {
        String connectionUniqueKey = HBaseConnectionUtil.generateUniqueConnectionKey(properties);
        String clusterId = properties.getProperty("hbase.shell.session.cluster");
        String sessionId = "";
        if (StringUtil.isNotBlank(clusterId)) {
            sessionId = clusterId + "#";
        }
        sessionId = sessionId + connectionUniqueKey;
        return sessionId;
    }

}

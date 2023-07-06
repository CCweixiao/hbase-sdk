package com.github.CCweixiao.hbase.sdk.shell;

import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

import java.util.Properties;

/**
 * @author leojie 2023/7/6 22:17
 */
public class HBaseShellSessionConfig {
    private static final String HBASE_SHELL_SESSION_INIT_TIMEOUT_MS = "hbase.shell.session.init.timeout.ms";
    private static final long DEFAULT_SHELL_SESSION_INIT_TIMEOUT_MS = 2 * 60 * 1000L;

    private static final String HBASE_SHELL_SESSION_INIT_MAX_TIMES = "hbase.shell.session.init.max.times";
    private static final int DEFAULT_SHELL_SESSION_INIT_MAX_TIMES = 10;

    private static final String HBASE_SHELL_SESSION_INIT_RETRY_INTERVAL_MS = "hbase.shell.session.init.retry.interval";
    private static final long DEFAULT_SHELL_SESSION_INIT_RETRY_INTERVAL_MS = 500L;

    private static final String HBASE_SHELL_SESSION_IDLE_MS = "hbase.shell.session.idle";
    private static final long DEFAULT_SHELL_SESSION_IDLE_MS = 2 * 60 * 60 * 1000L;

    private static final String HBASE_SHELL_DEBUG_LOG = "hbase.shell.session.debug.log";
    private static final boolean DEFAULT_SHELL_DEBUG_LOG = false;

    public static long shellSessionConnectionTimeout(Properties properties) {
        String val = properties.getProperty(HBASE_SHELL_SESSION_INIT_TIMEOUT_MS);
        if (StringUtil.isBlank(val)) {
            return DEFAULT_SHELL_SESSION_INIT_TIMEOUT_MS;
        }
        return Long.parseLong(val);
    }

    public static int shellSessionConnectionRetryTimes(Properties properties) {
        String val = properties.getProperty(HBASE_SHELL_SESSION_INIT_MAX_TIMES);
        if (StringUtil.isBlank(val)) {
            return DEFAULT_SHELL_SESSION_INIT_MAX_TIMES;
        }
        return Integer.parseInt(val);
    }

    public static long shellSessionConnectionRetryInterval(Properties properties) {
        String val = properties.getProperty(HBASE_SHELL_SESSION_INIT_RETRY_INTERVAL_MS);
        if (StringUtil.isBlank(val)) {
            return DEFAULT_SHELL_SESSION_INIT_RETRY_INTERVAL_MS;
        }
        return Long.parseLong(val);
    }

    public static long shellSessionIdle(Properties properties) {
        String val = properties.getProperty(HBASE_SHELL_SESSION_IDLE_MS);
        if (StringUtil.isBlank(val)) {
            return DEFAULT_SHELL_SESSION_IDLE_MS;
        }
        return Long.parseLong(val);
    }

    public static boolean shellSessionDebugLog(Properties properties) {
        String val = properties.getProperty(HBASE_SHELL_DEBUG_LOG);
        if (StringUtil.isBlank(val)) {
            return DEFAULT_SHELL_DEBUG_LOG;
        }
        return "true".equalsIgnoreCase(val);
    }
}

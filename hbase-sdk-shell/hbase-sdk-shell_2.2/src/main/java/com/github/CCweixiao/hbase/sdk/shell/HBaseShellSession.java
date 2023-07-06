package com.github.CCweixiao.hbase.sdk.shell;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseShellSessionEnvInitException;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author leojie 2023/7/4 19:59
 */
public class HBaseShellSession implements ShellSession {
    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseShellSession.class);
    private static final String HBASE_SHELL_LOG_LEVEL = "hbase.shell.log.level";
    private static final int DEFAULT_RETRY_TIMES = 10;
    private static final long DEFAULT_SLEEP_TIME_MS = 1000L;
    private static final long DEFAULT_SHELL_CONNECTION_TIMEOUT_MS = 2 * 60 * 1000L;

    private final String sessionId;
    private final int sessionInitMaxTimes;
    private final long sessionInitRetryInterval;
    private final long sessionConnectionTimeout;
    private final Properties properties;

    private ScriptingContainer scriptingContainer;
    private StringWriter writer;
    private boolean isConnected;

    public HBaseShellSession(Builder builder) {
        this.properties = builder.properties;
        this.sessionId = builder.sessionId;
        this.sessionInitMaxTimes = builder.sessionInitMaxTimes;
        this.sessionInitRetryInterval = builder.sessionInitRetryInterval;
        this.sessionConnectionTimeout = builder.sessionConnectionTimeout;
    }

    static class Builder {
        private String sessionId;
        private Properties properties;
        private int sessionInitMaxTimes;
        private long sessionInitRetryInterval;
        private long sessionConnectionTimeout;

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder sessionInitMaxTimes(int sessionInitMaxTimes) {
            this.sessionInitMaxTimes = sessionInitMaxTimes;
            return this;
        }

        public Builder sessionInitRetryInterval(long sessionInitRetryInterval) {
            this.sessionInitRetryInterval = sessionInitRetryInterval;
            return this;
        }

        public Builder sessionConnectionTimeout(long sessionConnectionTimeout) {
            this.sessionConnectionTimeout = sessionConnectionTimeout;
            return this;
        }

        public Builder properties(String key, String value) {
            if (this.properties == null) {
                this.properties = new Properties();
            }
            this.properties.setProperty(key, value);
            return this;
        }

        public HBaseShellSession build() {
            return new HBaseShellSession(this);
        }
    }

    public static HBaseShellSession.Builder sessionBuilder() {
        return new HBaseShellSession.Builder();
    }

    @Override
    public void open() {
        Thread t = new Thread(() -> {
            int initMaxTimes = this.getSessionInitMaxTimes();
            if (initMaxTimes <= 0) {
                initMaxTimes = DEFAULT_RETRY_TIMES;
            }
            long initRetryInterval = this.getSessionInitRetryInterval();
            if (initRetryInterval <= 0) {
                initRetryInterval = DEFAULT_SLEEP_TIME_MS;
            }
            try {
                createShellRunningEnv();
            } catch (Exception e) {
                for (int i = 0; i < initMaxTimes; i++) {
                    try {
                        createShellRunningEnv();
                    } catch (Exception ex) {
                        if (i == (initMaxTimes - 1)) {
                            LOGGER.error("After {} retries, HBase shell session initialization failed.", initMaxTimes, ex);
                            throw new HBaseShellSessionEnvInitException(ex);
                        }
                        shortSpin(initRetryInterval);
                    }
                }
            }
        });
        t.setName("HBaseShellRunningEnvInitThread");
        t.setDaemon(true);
        t.start();

        long connectedTimeout = this.getSessionConnectionTimeout();
        if (connectedTimeout <= 0) {
            connectedTimeout = DEFAULT_SHELL_CONNECTION_TIMEOUT_MS;
        }
        CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(this::waitShellSessionConnected);
        try {
            this.isConnected = future.get(connectedTimeout, TimeUnit.MILLISECONDS);
            LOGGER.info("Initialize hbase shell session successfully.");
        } catch (InterruptedException | ExecutionException e) {
            this.isConnected = false;
            future.cancel(true);
            LOGGER.error("Initialize hbase shell session failed.", e);
            this.destroy();
        } catch (TimeoutException e) {
            LOGGER.error("Initialize hbase shell session timeout.", e);
            this.isConnected = false;
            future.cancel(true);
            this.destroy();
        }
    }

    private void shortSpin(long interval) {
        if (interval <= 0) {
            return;
        }
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            LOGGER.warn("Ignore error.", e);
        }
    }

    private void createShellRunningEnv() throws IOException {
        this.scriptingContainer = new ScriptingContainer(LocalContextScope.SINGLETHREAD);
        this.writer = new StringWriter();
        scriptingContainer.setOutput(this.writer);
        Properties sysProps = System.getProperties();
        String prop = "";
        if (hbaseShellDebugOpen()) {
            prop = "-d,";
        }
        if (properties != null && !properties.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String key : properties.stringPropertyNames()) {
                sb.append("-D");
                sb.append(key);
                sb.append("=");
                sb.append(properties.getProperty(key));
                sb.append(",");
            }
            prop = prop + sb.substring(0, sb.length() - 1);
        }
        if (StringUtil.isNotBlank(prop)) {
            sysProps.setProperty("hbase.ruby.args", prop);
        }
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("hbase-ruby/hirb.rb")) {
            this.scriptingContainer.runScriptlet(in, "hirb.rb");
        }
    }

    private boolean waitShellSessionConnected() {
        while (true) {
            Result result = executeCmd("list_namespace");
            if (result.isSuccess()) {
                return true;
            }
            shortSpin(200L);
        }
    }

    @Override
    public Result execute(String cmd) {
        if (!this.isConnected()) {
            return Result.failed(String.format("The current session [%s] is not connected successfully," +
                    " please try again.", this));
        }
        return executeCmd(cmd);
    }

    @Override
    public void destroy() {
        if (this.scriptingContainer != null) {
            this.scriptingContainer.terminate();
        }
    }

    private boolean hbaseShellDebugOpen() {
        String logLevel = this.properties.getProperty(HBASE_SHELL_LOG_LEVEL);
        return StringUtil.isNotBlank(logLevel) && "debug".equalsIgnoreCase(logLevel);
    }

    private Result executeCmd(String cmd) {
        try {
            this.writer.getBuffer().setLength(0);
            this.scriptingContainer.runScriptlet(cmd);
            this.writer.flush();
            return Result.ok(writer.toString());
        } catch (Exception e) {
            return Result.failed(getStackTrace(e));
        }
    }

    public String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public String getSessionId() {
        return sessionId;
    }

    public Properties getProperties() {
        return properties;
    }

    public int getSessionInitMaxTimes() {
        return sessionInitMaxTimes;
    }

    public long getSessionInitRetryInterval() {
        return sessionInitRetryInterval;
    }

    public long getSessionConnectionTimeout() {
        return sessionConnectionTimeout;
    }

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public String toString() {
        return this.getSessionId();
    }
}

package com.github.CCweixiao.hbase.sdk.shell;

import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

/**
 * @author leojie 2023/7/4 19:59
 */
public class HBaseShellSession {
    private Properties properties;
    private ScriptingContainer scriptingContainer;
    private StringWriter writer;

    public HBaseShellSession(Properties properties) {
        this.properties = properties;
    }

    public HBaseShellSession() {
    }

    public void open() {
        Thread t = new Thread(this::init);
        t.setName("HBaseShellSessionInit");
        t.setDaemon(true);
        t.start();
    }

    private void init() {
        this.scriptingContainer = new ScriptingContainer(LocalContextScope.SINGLETON);
        this.writer = new StringWriter();
        scriptingContainer.setOutput(this.writer);
        Properties sysProps = System.getProperties();
        if (properties != null && !properties.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String key : properties.stringPropertyNames()) {
                sb.append("-D");
                sb.append(key);
                sb.append("=");
                sb.append(properties.getProperty(key));
                sb.append(",");
            }
            String prop = sb.substring(0, sb.length() - 1);
            sysProps.setProperty("hbase.ruby.args", prop);
        }
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("hbase-ruby/hirb.rb")) {
            this.scriptingContainer.runScriptlet(in, "hirb.rb");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Result execute(String cmd) {
        return executeCmd(cmd);
    }

    public boolean waitShellSessionConnected() {
        while (true) {
            Result result = executeCmd("list_namespace");
            if (result.isSuccess()) {
                return true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
    }

    public void destroy() {
        if (this.scriptingContainer != null) {
            this.scriptingContainer.terminate();
        }
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

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}

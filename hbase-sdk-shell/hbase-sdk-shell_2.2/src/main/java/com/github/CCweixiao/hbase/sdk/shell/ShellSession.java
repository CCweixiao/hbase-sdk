package com.github.CCweixiao.hbase.sdk.shell;

/**
 * @author leojie 2023/7/6 08:32
 */
public interface ShellSession {
    void open();

    Result execute(String cmd);

    void destroy();
}

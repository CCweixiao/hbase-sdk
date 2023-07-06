package com.github.CCweixiao.hbase.sdk.shell.example;

import com.github.CCweixiao.hbase.sdk.shell.HBaseShellSession;
import com.github.CCweixiao.hbase.sdk.shell.HBaseShellSessionManager;
import com.github.CCweixiao.hbase.sdk.shell.Result;

import java.util.Properties;

/**
 * @author leojie 2023/7/4 19:55
 */
public class HBaseShellExample {
    public static void main(String[] args) throws InterruptedException {
        Properties p = new Properties();
        p.setProperty("hbase.shell.log.level", "debug");
        p.setProperty("hbase.zookeeper.quorum", "localhost");
        p.setProperty("hbase.zookeeper.property.clientPort", "2181");

        HBaseShellSession shellSession = HBaseShellSessionManager.getHBaseShellSession(p);
        Result res = shellSession.execute("list");
        System.out.println(res);
    }
}

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
        p.setProperty("hbase.shell.session.debug.log", "true");
        p.setProperty("hbase.zookeeper.quorum", "myhbase");
        p.setProperty("hbase.zookeeper.property.clientPort", "2181");

        HBaseShellSession shellSession = HBaseShellSessionManager.getHBaseShellSession(p);
//        Result res = shellSession.execute("list_namespace");
//        System.out.println(res);
//
//        Result res2 = shellSession.execute("list");
//        System.out.println(res2);
//
//        Result res3 = shellSession.execute("put 't1', '1001', 'f', 'leo'");
//        System.out.println(res3);

        Result res4 = shellSession.execute("puts \"Hello World\"");
        Result res5 = shellSession.execute("a = 1 + 1");
       Result res6 = shellSession.execute("puts a");
        System.out.println(res4);



    }
}

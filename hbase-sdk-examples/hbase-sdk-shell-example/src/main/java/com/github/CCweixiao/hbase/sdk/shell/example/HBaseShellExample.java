package com.github.CCweixiao.hbase.sdk.shell.example;

import com.github.CCweixiao.hbase.sdk.shell.HBaseShellSession;
import com.github.CCweixiao.hbase.sdk.shell.Result;

/**
 * @author leojie 2023/7/4 19:55
 */
public class HBaseShellExample {
    public static void main(String[] args) throws InterruptedException {
        HBaseShellSession shellSession = new HBaseShellSession();
        shellSession.open();

        boolean res = shellSession.waitShellSessionConnected();
        System.out.println(res);
        if (res) {
            Result result = shellSession.execute("list");
            System.out.println(result);
        }

        Result result1 = shellSession.execute("puts 1+1");
        System.out.println(result1);

        shellSession.destroy();

        Thread.sleep(10000);

        Result result = shellSession.execute("puts 1+1");
        System.out.println(result);
    }
}

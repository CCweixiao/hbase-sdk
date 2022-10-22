package com.github.CCweixiao.hbase.sdk.adapter_14.sample;

import com.github.CCweixiao.hbase.sdk.adapter_14.HBaseAdminTemplate;
import org.apache.hadoop.conf.Configuration;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author leojie 2021/2/9 8:49 下午
 */
public class HBaseAdminTemplateThreadSample {

    static class Work implements Runnable {
        public Work() {
        }

        @Override
        public void run() {
            Random random = new Random();

            while (true) {
                try {
                    Properties properties = new Properties();
                    properties.setProperty("java.security.krb5.conf", "/Users/mac/leo_project/hbase-sdk/hbase-sdk-core_1.4/src/test/resources/conf/krb5.conf");
                    properties.setProperty("hadoop.security.authentication", "kerberos");
                    properties.setProperty("hbase.security.authentication", "kerberos");

                    properties.setProperty("keytab.file", "/Users/mac/leo_project/hbase-sdk/hbase-sdk-core_1.4/src/test/resources/conf/hadoop.keytab");
                    properties.setProperty("kerberos.principal", "hadoop@LEO.COM");

                    properties.setProperty("hbase.master.kerberos.principal", "hbase/_HOST@LEO.COM");
                    properties.setProperty("hbase.regionserver.kerberos.principal", "hbase/_HOST@LEO.COM");

                    properties.setProperty("hbase.zookeeper.quorum", "node2.bigdata.leo.com,node1.bigdata.leo.com,node3.bigdata.leo.com");
                    properties.setProperty("hbase.zookeeper.property.clientPort", "2181");

                    HBaseAdminTemplate hBaseAdminTemplate = new HBaseAdminTemplate(properties);
                    int r = random.nextInt(10) + 1;
                    System.out.println(hBaseAdminTemplate.listNamespaceNames());
                    System.out.println("Thread-" + Thread.currentThread().getName() + "即将等待：" + r + "秒钟");
                    Thread.sleep((long) r * 6 * 1000);
                    System.out.println("Thread-" + Thread.currentThread().getName() + "等待时间：" + r + "秒钟");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("################################################################################################");
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Work());
        }
    }
}

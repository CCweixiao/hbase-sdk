package com.github.CCweixiao.example;

import com.alipay.sofa.ark.support.startup.SofaArkBootstrap;
import com.github.CCweixiao.hbase.sdk.common.HBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.service.HBaseTemplateFactory;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author leojie 2022/10/22 18:29
 */
public class HBaseServiceExample {
    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hbase.zookeeper.quorum", "myhbase");
        properties.setProperty("hbase.zookeeper.property.clientPort", "2181");
        return properties;
    }
    static class Work implements Runnable {
        public Work() {
        }

        @Override
        public void run() {
            Random random = new Random();

            while (true) {
                try {
                    Properties properties = getProperties();
                    HBaseTemplateFactory factory = new HBaseTemplateFactory();
                    HBaseAdminOperations adminOperations = factory.getHBaseAdminOperations("1.4.x", properties);
                    int r = random.nextInt(10) + 1;
                    System.out.println(adminOperations.listTableNames());
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
        SofaArkBootstrap.launch(args);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new HBaseServiceExample.Work());
        }
    }
}

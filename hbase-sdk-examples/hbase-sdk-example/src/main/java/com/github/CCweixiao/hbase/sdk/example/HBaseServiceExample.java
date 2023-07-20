package com.github.CCweixiao.hbase.sdk.example;

import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import com.github.CCweixiao.hbase.sdk.template.BaseHBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.template.HBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseAdminTemplateImpl;

import java.util.*;
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
                    IHBaseAdminTemplate adminTemplate = new HBaseAdminTemplateImpl.Builder().properties(getProperties()).build();
                    int r = random.nextInt(10) + 1;
                    System.out.println(adminTemplate.listTableNames());
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
        BaseHBaseTableTemplate hBaseTemplate = HBaseTableTemplate.of(getProperties());
        ScanParams scanQueryParamsBuilder = ScanParams.builder()
                .familyName("info")
                .columnNames(Arrays.asList("city_name", "city_address", "cityTagList"))
                .startRow("a10001")
                .stopRow("a10002")
                .build();
        System.out.println(hBaseTemplate.scan("t2", scanQueryParamsBuilder));
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new HBaseServiceExample.Work());
        }
    }
}

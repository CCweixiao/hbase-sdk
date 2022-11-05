package com.github.CCweixiao.example;

import com.alipay.sofa.ark.support.startup.SofaArkBootstrap;
import com.github.CCweixiao.hbase.sdk.adapter_12.VersionInfoImpl;
import com.github.CCweixiao.hbase.sdk.common.HBaseAdminOperations;
import com.github.CCweixiao.hbase.sdk.common.HBaseTableOperations;
import com.github.CCweixiao.hbase.sdk.common.query.ScanQueryParamsBuilder;
//import com.github.CCweixiao.hbase.sdk.service.HBaseTemplateFactory;
//import com.github.CCweixiao.hbase.sdk.service.IHBaseAdminTemplate;
//import com.github.CCweixiao.hbase.sdk.service.IHBaseTableTemplate;
//import com.github.CCweixiao.hbase.sdk.service.impl.HBaseAdminTemplateBuilder;
//import com.github.CCweixiao.hbase.sdk.service.impl.HBaseTableTemplateBuilder;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author leojie 2022/10/22 18:29
 */
public class HBaseServiceExample {
    public static void main(String[] args) {
        SofaArkBootstrap.launch(args);
        System.out.println("HBaseServiceExample:" + HBaseServiceExample.class.getClassLoader());
        new com.github.CCweixiao.hbase.sdk.adapter_12.VersionInfoImpl().version();
        new com.github.CCweixiao.hbase.sdk.adapter_14.VersionInfoImpl().version();
        new com.github.CCweixiao.hbase.sdk.adapter_22.VersionInfoImpl().version();
        com.github.CCweixiao.hbase.sdk.adapter_14.HBaseTemplate tableOperations = new com.github.CCweixiao.hbase.sdk.adapter_14.HBaseTemplate(getProperties());
        ScanQueryParamsBuilder scanQueryParamsBuilder = new ScanQueryParamsBuilder.Builder()
                .familyName("info")
                .columnNames(Arrays.asList("city_name", "city_address", "cityTagList"))
                .startRow("a10001")
                .stopRow("a10002")
                .build();

    }
    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hbase.zookeeper.quorum", "myhbase");
        properties.setProperty("hbase.zookeeper.property.clientPort", "2181");
        return properties;
    }
//    static class Work implements Runnable {
//        public Work() {
//        }
//
//        @Override
//        public void run() {
//            Random random = new Random();
//
//            while (true) {
//                try {
//                    Properties properties = getProperties();
//                    HBaseAdminOperations adminOperations = HBaseTemplateFactory.getHBaseAdminOperations("1.4.x", properties);
//                    int r = random.nextInt(10) + 1;
//                    System.out.println(adminOperations.listTableNames());
//                    System.out.println("Thread-" + Thread.currentThread().getName() + "即将等待：" + r + "秒钟");
//                    Thread.sleep((long) r * 6 * 1000);
//                    System.out.println("Thread-" + Thread.currentThread().getName() + "等待时间：" + r + "秒钟");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("################################################################################################");
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        SofaArkBootstrap.launch(args);
////        ExecutorService executorService = Executors.newFixedThreadPool(10);
////        for (int i = 0; i < 10; i++) {
////            executorService.execute(new HBaseServiceExample.Work());
////        }
//        IHBaseAdminTemplate adminTemplate = new HBaseAdminTemplateBuilder.Builder().sdkAdapterVersion("1.4.x")
//                .properties(getProperties()).build();
//        System.out.println(adminTemplate.listTableNames());
//        System.out.println(adminTemplate.tableExists("t1"));
//        IHBaseTableTemplate tableTemplate = new HBaseTableTemplateBuilder.Builder().sdkAdapterVersion("1.4.x")
//                .properties(getProperties()).build();
//        ScanQueryParamsBuilder queryParamsBuilder = new ScanQueryParamsBuilder.Builder()
//                .startRow("a10001")
//                .stopRow("a10002")
//                .build();
//        List<Map<String, Map<String, String>>> maps = tableTemplate.scan("t1", queryParamsBuilder);
//        System.out.println(maps);
//    }
}

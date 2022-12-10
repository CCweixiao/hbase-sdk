package com.github.CCweixiao.hbase.sdk.thrift.example;

import com.github.CCweixiao.hbase.sdk.thrift.HBaseThriftTemplate;
import com.github.CCweixiao.hbase.sdk.thrift.HBaseThriftTemplateFactory;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author leojie 2020/12/27 11:59 下午
 */
public class HBaseThriftPoolThreadTest {

    static class Work implements Runnable {
        private final HBaseThriftTemplate thriftTemplate = HBaseThriftTemplateFactory.getInstance("localhost", 9090, 1);

        @Override
        public void run() {
            Random random = new Random();
            System.out.println(thriftTemplate.getRowToMap("LEO_USER", "a10001", false));
            while (true) {
                try {
                    int r = random.nextInt(10) + 1;
                    System.out.println("Thread-" + Thread.currentThread().getName() + "即将等待：" + r + "分钟");
                    Thread.sleep(r * 60 * 1000L);
                    System.out.println(thriftTemplate.getRowToMap("LEO_USER", "a10001", false));
                    System.out.println("Thread-" + Thread.currentThread().getName() + "等待时间：" + r + "分钟");
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

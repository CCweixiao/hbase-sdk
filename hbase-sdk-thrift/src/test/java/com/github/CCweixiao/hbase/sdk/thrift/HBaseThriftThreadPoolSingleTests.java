package com.github.CCweixiao.hbase.sdk.thrift;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程下的单例模式测试
 *
 * @author leojie 2020/12/27 2:59 下午
 */
public class HBaseThriftThreadPoolSingleTests {


    static class Work implements Runnable {
        private final HBaseThriftTemplate thriftService = HBaseThriftTemplateFactory.getInstance("localhost", 9090, 1);

        @Override
        public void run() {
            Random random = new Random();
            System.out.println(thriftService.getToRowData("LEO_USER", "a10001"));
            while (true) {
                try {
                    int r = random.nextInt(10) + 1;
                    System.out.println("Thread-" + Thread.currentThread().getName() + "即将等待：" + r + "分钟");
                    Thread.sleep(r * 60 * 1000);
                    System.out.println(thriftService.getToRowData("LEO_USER", "a10001"));
                    System.out.println("Thread-" + Thread.currentThread().getName() + "等待时间：" + r + "分钟");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("################################################################################################");
            }
        }
    }


    @Test
    public void testThriftPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new HBaseThriftThreadPoolSingleTests.Work());
        }

        try {
            Thread.sleep(200000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

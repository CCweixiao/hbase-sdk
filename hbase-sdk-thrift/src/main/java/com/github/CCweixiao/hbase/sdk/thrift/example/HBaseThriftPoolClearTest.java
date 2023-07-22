package com.github.CCweixiao.hbase.sdk.thrift.example;

import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import com.github.CCweixiao.hbase.sdk.thrift.HBaseThriftTemplate;
import com.github.CCweixiao.hbase.sdk.thrift.HBaseThriftTemplateFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author leojie 2021/12/2 3:06 下午
 */
public class HBaseThriftPoolClearTest {
    static class Work implements Runnable {
        private final HBaseThriftTemplate thriftTemplate = HBaseThriftTemplateFactory.getInstance("internal_dev", 9091, 2);

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(thriftTemplate.getRow("LEO_USER", GetRowParam.of("a10001").build()));

                    Thread.sleep( 10 * 1000L);
                    // thriftService.clearThriftPool();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("################################################################################################");
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 20; i++) {
            executorService.execute(new Work());
        }
    }
}

package com.github.CCweixiao.hbase.sdk.adapter_22.example;

import com.github.CCweixiao.hbase.sdk.adapter_22.HBaseAdminTemplate;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author leojie 2021/6/12 12:02 上午
 */
public class HBaseAdminTemplateThreadTest {
    static class Work implements Runnable {
        private final HBaseAdminTemplate hBaseAdminTemplate = new HBaseAdminTemplate("docker-hbase", "2181");

        @Override
        public void run() {
            Random random = new Random();
            System.out.println(hBaseAdminTemplate.getConnection());
            System.out.println(hBaseAdminTemplate.listNamespaceNames());
           /* while (true) {
                int r = random.nextInt(10) + 1;
                System.out.println("Thread-" + Thread.currentThread().getName() + "即将等待：" + r + "分钟");

                System.out.println("################################################################################################");
            }*/
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Work());
        }
    }
}

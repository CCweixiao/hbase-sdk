package com.github.CCweixiao.hbase.sdk.thrift.example;

import com.github.CCweixiao.hbase.sdk.thrift.HBaseThriftService;
import com.github.CCweixiao.hbase.sdk.thrift.HBaseThriftServiceHolder;

import java.util.Random;

/**
 * @author leojie 2020/12/27 11:59 下午
 */
public class HBaseThriftPoolTest {
    public static void main(String[] args) {
        // HBaseThriftService hBaseThriftService = HBaseThriftServiceHolder.getInstance("localhost", 9090);
        Random random = new Random();

        while (true) {


            try {
                int r = random.nextInt(10) + 1;
                //int r = 4;
                System.out.println("即将等待：" + r + "分钟");
                Thread.sleep(r * 60 * 1000L);
                //System.out.println("getNumIdle=" + hBaseThriftPool.getNumIdle());
                //System.out.println("getNumActive=" + hBaseThriftPool.getNumActive());
                System.out.println("等待时间：" + r + "分钟");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("################################################################################################");
        }

    }
}

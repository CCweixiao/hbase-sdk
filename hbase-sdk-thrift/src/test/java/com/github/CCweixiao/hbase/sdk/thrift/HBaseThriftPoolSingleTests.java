package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author leojie 2020/12/27 2:59 下午
 */
public class HBaseThriftPoolSingleTests {

    private HBaseThriftTemplate hBaseThriftService;

    @Before
    public void init() {
        HBaseThriftPoolConfig config = new HBaseThriftPoolConfig();
        config.setMaxTotal(2);
        config.setMaxIdle(2);
        config.setTimeBetweenEvictionRunsMillis(600 * 1000);
        config.setMinEvictableIdleTimeMillis(600 * 1000);
        hBaseThriftService = HBaseThriftTemplateFactory.getInstance("internal_dev", 9091, config);
    }

    @Test
    public void testThriftPool() {
        Random random = new Random();

        while (true) {

            System.out.println(hBaseThriftService.getRow("LEO_USER", GetRowParam.of("a10001").build()));
            try {
                int r = random.nextInt(10) + 1;
                //int r = 4;
                System.out.println("即将等待：" + r + "分钟");
                Thread.sleep(r * 60 * 1000);
                System.out.println(hBaseThriftService.getRow("LEO_USER", GetRowParam.of("a10001").build()));
                System.out.println("等待时间：" + r + "分钟");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("################################################################################################");
        }
    }

    @Test
    public void testThriftClient() {
//        final List<String> tableNames = hBaseThriftService.getTableNames();
//        System.out.println(tableNames);
        final HBaseRowData rowData = hBaseThriftService.getRow("LEO_USER", GetRowParam.of("a10001").build());
        System.out.println(rowData);

        try {
            hBaseThriftService.clearThriftPool();
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------------2--------------");
        final HBaseRowData rowData2 = hBaseThriftService.getRow("LEO_USER", GetRowParam.of("a10001").build());
        System.out.println(rowData2);
        try {
            Thread.sleep(600 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBatchPut() {
        Map<String, Object> data1 = new HashMap<>();
        data1.put("g:name", "leo");
        data1.put("g:age", 18);
        data1.put("g:address", "上海");
        data1.put("g:tag", null);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("f:name", "leo2");
        data2.put("f:age", 18);
        data2.put("f:address", "上海2");
        data2.put("f:tag", "tag2");

        Map<String, Map<String, Object>> data = new HashMap<>(2);
        data.put("a10001", data1);
        data.put("a10002", data2);

        hBaseThriftService.saveBatch("LEO_USER", data);

    }

    @Test
    public void testPut() {
        Map<String, Object> data3 = new HashMap<>();
        data3.put("g:name", "leo2g");
        data3.put("g:age", 18);
        data3.put("g:address", "上海2g");
        data3.put("g:tag", "tag2g");

        hBaseThriftService.save("LEO_USER", "a10002", data3);
    }

    @Test
    public void testGet() {

//        System.out.println(hBaseThriftService.getToRowData("LEO_USER",
//                "a10001", "g", Collections.singletonList("age")));
//
//        System.out.println(hBaseThriftService.getToRowData("LEO_USER",
//                "a10001", "g", Arrays.asList("name", "age", "sds")));
//
//        System.out.println(hBaseThriftService.getToRowData("LEO_USER",
//                "a10002", "f"));
//
//        System.out.println(hBaseThriftService.getToRowData("LEO_USER",
//                "a10002", "g"));
//
//        System.out.println(hBaseThriftService.getToRowData("LEO_USER", "a1002"));
    }

    @Test
    public void testDeleteRow() {
        hBaseThriftService.delete("LEO_USER", "a1002");
    }

    @Test
    public void testDeleteFamily() {
        hBaseThriftService.delete("LEO_USER", "a10001", "g", new ArrayList<>());
    }

    @Test
    public void testDeleteSomeColumns() {
        hBaseThriftService.delete("LEO_USER", "a10001", "g", "address");
    }

    @Test
    public void testDeleteBatch() {
        hBaseThriftService.deleteBatch("LEO_USER", Arrays.asList("a10001", "a10002"));
    }

    @Test
    public void testDeleteBatchWithFamily() {
        hBaseThriftService.deleteBatch("LEO_USER", Arrays.asList("a10001", "a10002"), "g");
    }

    @Test
    public void testDeleteBatchWithFamilyAndCol() {
        hBaseThriftService.deleteBatch("LEO_USER", Arrays.asList("a10001", "a10002"), "f", "address");
        hBaseThriftService.deleteBatch("LEO_USER", Arrays.asList("a10001", "a10002"), "f", Arrays.asList("tag", "age"));

    }

}

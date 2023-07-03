package com.github.CCweixiao.hbase.sdk;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2023/7/3 11:00
 */
public class TestHBaseTableAdapter extends TestHBaseAdminAdapter{
    private static final Logger LOG = LoggerFactory.getLogger(TestHBaseTableAdapter.class);

    public static final String TEST_TABLE = "test_table";

    @Test
    public void testPut() {
        testCreateTestTable();
        Map<String, Object> data = new HashMap<>();
        data.put("f:name", "leo");
        data.put("f:age", 18);
        tableAdapter.save(TEST_TABLE, "1001", data);
        Map<String, String> result = tableAdapter.getRowToMap(TEST_TABLE, "1001", false);
        Assert.assertEquals(2, result.size());
        System.out.println("");
    }

    @Test
    public void testSaveBatch() {
        testCreateTestTable();
        Map<String, Object> data1 = new HashMap<>();
        data1.put("f:name", "leo1");
        data1.put("f:age", 17);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("f:name", "leo2");
        data2.put("f:age", 18);

        Map<String, Map<String, Object>> data = new HashMap<>(2);
        data.put("1001", data1);
        data.put("1002", data2);

        tableAdapter.saveBatch(TEST_TABLE, data);
        Map<String, Map<String, String>> result =
                tableAdapter.getRowsToMap(TEST_TABLE, Arrays.asList("1001", "1002"), true);
        Assert.assertEquals(2, result.size());
    }
}

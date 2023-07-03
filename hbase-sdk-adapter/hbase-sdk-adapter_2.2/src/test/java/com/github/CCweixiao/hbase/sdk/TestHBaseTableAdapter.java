package com.github.CCweixiao.hbase.sdk;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
}

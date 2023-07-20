package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2023/7/3 11:00
 */
public class TestHBaseTableAdapter extends BaseTestAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(TestHBaseTableAdapter.class);

    @Before
    public void beforeMethod() throws IOException {
        createTestTable();
    }

    @Test
    public void testSave() {
        createTestTable();
        Map<String, Object> data = new HashMap<>(2);
        data.put("f:name", "leo");
        data.put("f:age", 18);
        tableAdapter.save(TEST_TABLE, "1001", data);
        HBaseRowData result = tableAdapter.getToRowData(TEST_TABLE, "1001");
        Assert.assertEquals(2, result.getColDataContainer().size());
    }

    @Test
    public void testSaveBatch() {
        createTestTable();
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
    }

    @After
    public void afterMethod() throws IOException {
        deleteTestTable();
    }
}

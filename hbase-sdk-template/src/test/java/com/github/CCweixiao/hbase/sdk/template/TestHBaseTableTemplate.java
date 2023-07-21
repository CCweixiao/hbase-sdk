package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2023/7/3 11:00
 */
public class TestHBaseTableTemplate extends BaseTestAdapter {

    @Test
    public void testSaveMap() {
        Map<String, Object> data = new HashMap<>(2);
        data.put("info:name", "leo");
        data.put("info:age", 18);
        tableTemplate.save(TEST_TABLE, "1001", data);
        HBaseRowData result = tableTemplate.getToRowData(TEST_TABLE, "1001");
        Assert.assertEquals(2, result.getColDataContainer().size());
    }

    @Test
    public void testSaveBatch() {
        Map<String, Object> data1 = new HashMap<>();
        data1.put("info:name", "leo1");
        data1.put("info:age", 17);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("info:name", "leo2");
        data2.put("info:age", 18);

        Map<String, Map<String, Object>> data = new HashMap<>(2);
        data.put("1001", data1);
        data.put("1002", data2);

        tableTemplate.saveBatch(TEST_TABLE, data);
        List<HBaseRowData> rowDataList = tableTemplate.getToRowsData(TEST_TABLE, Arrays.asList("1001", "1002"));
        Assert.assertEquals(2, rowDataList.size());
    }
}

package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * @author leojie 2023/8/3 09:24
 */
public class TestHBaseHedgedReadTemplate {
    public static final String TEST_TABLE = "t1";
    protected static BaseHBaseTableTemplate tableTemplate;

    @BeforeClass
    public static void setUp() throws Exception {
        Properties c1 = new Properties();
        c1.setProperty("hbase.zookeeper.quorum", "myhbase1");
        c1.setProperty("hbase.zookeeper.property.clientPort", "2181");
        // 开启hedged read
        c1.setProperty("hbase.zookeeper.quorum.hedged.read", "myhbase");
        c1.setProperty("hbase.zookeeper.property.clientPort.hedged.read", "2181");
        c1.setProperty("hbase.client.hedged.read.open", "true");
        c1.setProperty("hbase.client.hedged.read.timeout", "60");
        c1.setProperty("hbase.client.hedged.thread.pool.size", "10");
        tableTemplate = HBaseTableTemplate.of(c1);
    }

    @Test
    public void testGet() {
        List<HBaseRowData> rowDataList = tableTemplate.scan(TEST_TABLE, ScanParams.of().build());
        System.out.println(rowDataList);
    }


}

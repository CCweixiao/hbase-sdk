package com.github.CCweixiao.hbase.sdk.example;

import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import com.github.CCweixiao.hbase.sdk.template.BaseHBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.template.HBaseTableTemplate;

import java.util.List;
import java.util.Properties;

/**
 * @author leojie 2023/8/3 09:38
 */
public class HBaseHedgedReadTemplate {
    public static final String TEST_TABLE = "t1";
    private final BaseHBaseTableTemplate tableTemplate;

    public HBaseHedgedReadTemplate() {
        Properties c1 = new Properties();
        c1.setProperty("hbase.zookeeper.quorum", "localhost");
        c1.setProperty("hbase.zookeeper.property.clientPort", "2181");
        // 开启hedged read
        c1.setProperty("hbase.client.hedged.read.open", "true");
        c1.setProperty("hbase.zookeeper.quorum.hedged.read", "myhbase");
        c1.setProperty("hbase.zookeeper.property.clientPort.hedged.read", "2181");
        c1.setProperty("hbase.client.hedged.read.timeout", "50");
        c1.setProperty("hbase.client.hedged.thread.pool.size", "10");
        tableTemplate = HBaseTableTemplate.of(c1);
    }

    public void testGet() {
        List<HBaseRowData> rowDataList = tableTemplate.scan(TEST_TABLE, ScanParams.of().build());
        System.out.println(rowDataList);
    }

    public static void main(String[] args) {
        HBaseHedgedReadTemplate template = new HBaseHedgedReadTemplate();
        template.testGet();
    }
}

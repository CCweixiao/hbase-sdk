package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.IHBaseTableOpAdapter;
import com.github.CCweixiao.hbase.sdk.schema.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.schema.HTableDesc;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author leojie 2023/7/3 11:01
 */
public class BaseTestAdapter {
    public static final String TEST_TABLE = "test_table";
    protected final static int NUM_SLAVES_BASE = 4;
    protected static HBaseTestingUtility TEST_UTIL;
    protected static IHBaseAdminAdapter adminAdapter;
    protected static IHBaseTableOpAdapter tableAdapter;

    @BeforeClass
    public static void setUp() throws Exception {
        TEST_UTIL = new HBaseTestingUtility();
        TEST_UTIL.startMiniCluster(NUM_SLAVES_BASE);
        adminAdapter = new HBaseAdminAdapter(TEST_UTIL.getConfiguration());
        Configuration configuration = TEST_UTIL.getConfiguration();
        String zkHost = configuration.get("hbase.zookeeper.quorum");
        String zkPort = configuration.get("hbase.zookeeper.property.clientPort");
        configuration.set("hbase.zookeeper.quorum", "localhost1");
        configuration.set("hbase.zookeeper.property.clientPort", "2182");
        // 开启hedged read
        configuration.set("hbase.zookeeper.quorum.hedged.read", zkHost);
        configuration.set("hbase.zookeeper.property.clientPort.hedged.read", zkPort);
        configuration.set("hbase.client.hedged.read.open", "true");
        configuration.set("hbase.client.hedged.read.timeout", "50");
        configuration.set("hbase.client.hedged.thread.pool.size", "10");
        tableAdapter = new HBaseTableAdapter(configuration);
    }

    protected String getNamespaceName() {
        return "TestNamespace";
    }

    protected boolean createTestTable() {
        ColumnFamilyDesc cf = ColumnFamilyDesc.newBuilder()
                .name("f")
                .maxVersions(3)
                .timeToLive(120000)
                .build();
        HTableDesc tableDesc = HTableDesc.newBuilder()
                .name(TEST_TABLE)
                .addFamilyDesc(cf)
                .build();
      return adminAdapter.createTable(tableDesc);
    }

    protected void deleteTestTable() {
        adminAdapter.disableTable(TEST_TABLE, false);
        adminAdapter.deleteTable(TEST_TABLE, false);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        TEST_UTIL.shutdownMiniCluster();
    }


}

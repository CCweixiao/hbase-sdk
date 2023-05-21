package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import com.github.CCweixiao.hbase.sdk.schema.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.schema.HTableDesc;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author leojie 2023/5/20 23:31
 */
public class TestHBaseAdminAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(TestHBaseAdminAdapter.class);
    protected final static int NUM_SLAVES_BASE = 4;
    private static HBaseTestingUtility TEST_UTIL;
    protected static IHBaseAdminAdapter adminAdapter;

    private String namespaceName = "TestNamespace";

    @BeforeClass
    public static void setUp() throws Exception {
        TEST_UTIL = new HBaseTestingUtility();
        TEST_UTIL.startMiniCluster(NUM_SLAVES_BASE);
        adminAdapter = new HBaseAdminAdapterImpl(TEST_UTIL.getConfiguration());
        LOG.info("Done initializing adminAdapter");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        TEST_UTIL.shutdownMiniCluster();
    }

    @Before
    public void beforeMethod() throws IOException {
        NamespaceDesc namespaceDesc = new NamespaceDesc();
        namespaceDesc.setNamespaceName("test");
        adminAdapter.createNamespaceAsync(namespaceDesc);
    }

    @Test
    public void testCreateNamespace() {
        NamespaceDesc namespaceDesc = new NamespaceDesc();
        namespaceDesc.setNamespaceName(namespaceName);
        boolean res = adminAdapter.createNamespaceAsync(namespaceDesc);
        Assert.assertTrue(res);
    }

    @Test
    public void testListNamespaceName() {
        List<String> namespaceNames = adminAdapter.listNamespaceNames();
        Assert.assertTrue(namespaceNames.contains("test"));
    }

    @Test
    public void testDeleteNamespaceName() {
        boolean res = adminAdapter.deleteNamespaceAsync("test");
        Assert.assertTrue(res);
    }

    @Test
    public void testCreateTable() {
        ColumnFamilyDesc cf = new ColumnFamilyDesc.Builder()
                .familyName("f1")
                .maxVersions(3)
                .timeToLive(120000)
                .build();
        HTableDesc tableDesc = new HTableDesc.Builder().tableName("test_table")
                .addFamilyDesc(cf)
                .build();
        boolean res = adminAdapter.createTable(tableDesc);
        Assert.assertTrue(res);
        List<HTableDesc> tableDescList = adminAdapter.listTableDesc();
        Assert.assertFalse(tableDescList.isEmpty());
    }

    @Test
    public void testListTableDesc() {
        System.out.println((Pattern) null);
    }
}

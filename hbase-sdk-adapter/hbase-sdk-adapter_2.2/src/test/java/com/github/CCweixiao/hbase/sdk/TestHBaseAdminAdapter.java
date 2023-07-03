package com.github.CCweixiao.hbase.sdk;

import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import com.github.CCweixiao.hbase.sdk.schema.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.schema.HTableDesc;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author leojie 2023/5/20 23:31
 */
public class TestHBaseAdminAdapter extends BaseTestAdapter {

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
        namespaceDesc.setNamespaceName(getNamespaceName());
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
        boolean res = testCreateTestTable();
        Assert.assertTrue(res);
        List<HTableDesc> tableDescList = adminAdapter.listTableDesc();
        Assert.assertFalse(tableDescList.isEmpty());
    }

    @Test
    public void testListTableDesc() {
        System.out.println((Pattern) null);
    }
}

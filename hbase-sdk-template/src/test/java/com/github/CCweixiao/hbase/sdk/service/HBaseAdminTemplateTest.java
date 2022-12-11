package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.common.model.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.common.model.HTableDesc;
import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import org.junit.Before;
import org.junit.Test;

/**
 * @author leojie 2022/11/4 20:52
 */
public class HBaseAdminTemplateTest extends AbstractHBaseTemplateTest {

    @Before
    public void init() {
        super.initIHBaseAdminTemplate();
    }

    @Test
    public void testListTableNames() {
        System.out.println(adminTemplate.listTableNames());
    }

    @Test
    public void testListTableDescList() {
        System.out.println(adminTemplate.listTableDesc());
    }

    @Test
    public void createNameSpace() {
        NamespaceDesc namespaceDesc = new NamespaceDesc();
        namespaceDesc.setNamespaceName("test_nn");
        namespaceDesc.addNamespaceProp("createdTime", String.valueOf(System.currentTimeMillis()));
        namespaceDesc.addNamespaceProp("createdBy", "leo_jie");
        adminTemplate.createNamespaceAsync(namespaceDesc);
    }

    @Test
    public void testCreateTable() {
        ColumnFamilyDesc f1 = new ColumnFamilyDesc.Builder()
                .familyName("f1")
                .build();
        ColumnFamilyDesc f2 = new ColumnFamilyDesc.Builder()
                .familyName("f2")
                .timeToLive(3600)
                .versions(3)
                .build();
        HTableDesc tableDesc = new HTableDesc.Builder()
                .defaultTableDesc("test_nn:test_table")
                .maxFileSize(51400000L)
                .addTableProp("hbase.hstore.block.storage.policy", "HOT")
                .addColumnFamilyDesc(f1)
                .addColumnFamilyDesc(f2)
                .build();
        adminTemplate.createTable(tableDesc);
    }

}

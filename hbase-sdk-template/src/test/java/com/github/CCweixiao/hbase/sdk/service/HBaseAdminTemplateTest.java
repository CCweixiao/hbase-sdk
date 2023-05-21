package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import com.github.CCweixiao.hbase.sdk.schema.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.schema.HTableDesc;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        List<String> tableNames = adminTemplate.listTableNames();
        System.out.println(tableNames);
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
        ColumnFamilyDesc f1 = ColumnFamilyDesc.newBuilder()
                .name("f1")
                .build();
        ColumnFamilyDesc f2 = ColumnFamilyDesc.newBuilder()
                .name("f2")
                .timeToLive(3600)
                .maxVersions(3)
                .build();
        HTableDesc tableDesc = HTableDesc.newBuilder()
                .name("leo_test_22222")
                .addFamilyDesc(f1)
                .addFamilyDesc(f2)
                .build();
        adminTemplate.createTable(tableDesc);
    }

}

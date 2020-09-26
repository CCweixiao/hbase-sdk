package com.github.CCweixiao;

import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.NamespaceDesc;
import com.github.CCweixiao.model.TableDesc;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HBase admin template test
 *
 * @author leojie 2020/8/15 10:28 上午
 */
public class HBaseAdminTemplateTest {
    private HBaseAdminTemplate hBaseTemplate;

    @Before
    public void initHBaseTemplate() {
        hBaseTemplate = new HBaseAdminTemplate("node1", "2181");
    }

    @Test
    public void testListNamespace() {
        List<String> namespaces = hBaseTemplate.listNamespaces();
        System.out.println(namespaces);
    }

    @Test
    public void testCreateNamespace() {
        String namespaceName = "LEO_NS2";
        if (hBaseTemplate.namespaceIsExists(namespaceName)) {
            hBaseTemplate.deleteNamespace(namespaceName);
        }

        NamespaceDesc namespaceDesc = new NamespaceDesc();
        namespaceDesc.setNamespaceName(namespaceName);

        namespaceDesc = namespaceDesc.addNamespaceProp("tag", "测试命名空间")
                .addNamespaceProp("createBy", "leo").addNamespaceProp("updateBy", "");

        hBaseTemplate.createNamespace(namespaceDesc);
    }

    @Test
    public void testGetNamespace() {
        String namespaceName = "LEO_NS2";
        final NamespaceDesc namespaceDesc = hBaseTemplate.getNamespaceDesc(namespaceName);
        System.out.println(namespaceDesc.getNamespaceName());
        System.out.println(namespaceDesc.getNamespaceProps());
    }

    @Test
    public void testDeleteNamespace() {
        String namespaceName = "LEO_NS2";
        hBaseTemplate.deleteNamespace(namespaceName);
    }

    @Test
    public void testCreateTable() {
        String tableName = "LEO_NS2:USER";

        TableDesc tableDesc = new TableDesc();
        tableDesc.setTableName(tableName);

        tableDesc = tableDesc.addProp("tag", "测试用户表").addProp("createUser", "leo");

        FamilyDesc familyDesc1 = new FamilyDesc.Builder()
                .familyName("INFO")
                .replicationScope(1)
                .compressionType("NONE")
                .timeToLive(2147483647)
                .maxVersions(3).build();

        FamilyDesc familyDesc2 = new FamilyDesc.Builder()
                .familyName("INFO2")
                .replicationScope(0)
                .compressionType("NONE")
                .timeToLive(864000)
                .maxVersions(3).build();

        tableDesc = tableDesc.addFamilyDesc(familyDesc1)
                .addFamilyDesc(familyDesc2);

        tableDesc.setStartKey("1");
        tableDesc.setEndKey("100");
        tableDesc.setPreSplitRegions(10);

        hBaseTemplate.createTable(tableDesc, false);
    }

    @Test
    public void getTableDesc() {
        String tableName = "LEO_NS2:USER";
        final TableDesc tableDesc = hBaseTemplate.getTableDesc(tableName);
        System.out.println(tableDesc.getProp("createUser"));
        System.out.println(tableDesc.getProp("tag"));
        System.out.println(tableDesc.getTableDesc());
    }

    @Test
    public void deleteTable() {
        String tableName = "LEO_NS2:USER";
        boolean disabled = hBaseTemplate.isTableDisabled(tableName);
        if (!disabled) {
            hBaseTemplate.disableTable(tableName, false);
        }
        boolean res = hBaseTemplate.deleteTable(tableName);
        System.out.println(res);
    }
}

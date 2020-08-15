package com.leo.hbase.sdk.core;

import com.leo.hbase.sdk.core.model.HFamilyBuilder;
import com.leo.hbase.sdk.core.model.HTableModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        boolean res = hBaseTemplate.createNamespace("TEST");

        System.out.println(res);

    }

    @Test
    public void testListTableName() {
        List<String> tableNames = hBaseTemplate.listTableNames();

        System.out.println(tableNames);
    }

    @Test
    public void testCreateTable() {
        String tableName = "TEST:leo_test";
        HTableModel tableModel = new HTableModel();
        tableModel.setTableName(tableName);
        HFamilyBuilder familyBuilder = new HFamilyBuilder.Builder().familyName("INFO").build();
        tableModel.sethFamilies(familyBuilder);

        boolean res = hBaseTemplate.createTable(tableModel);
        System.out.println(res);
    }

    @Test
    public void testDisableTable() {
        String tableName = "TEST:leo_test2";
        boolean res = hBaseTemplate.disableTable(tableName);

        System.out.println(res);
    }

    @Test
    public void testDeleteTable() {
        String tableName = "TEST:leo_test2";
        boolean res = hBaseTemplate.deleteTable(tableName);

        System.out.println(res);
    }

    @Test
    public void testCreatePreSplitTable() {
        String tableName = "TEST:leo_test3";
        HTableModel tableModel = new HTableModel();
        tableModel.setTableName(tableName);
        HFamilyBuilder familyBuilder = new HFamilyBuilder.Builder().familyName("INFO").build();
        tableModel.sethFamilies(familyBuilder);

        boolean res = hBaseTemplate.createTable(tableModel, "a", "c", 10);
        System.out.println(res);
    }

    @Test
    public void testCreatePreSplitKeysTable() {
        String tableName = "TEST:leo_test5";
        HTableModel tableModel = new HTableModel();
        tableModel.setTableName(tableName);
        HFamilyBuilder familyBuilder = new HFamilyBuilder.Builder().familyName("INFO")
                .minVersions(3).maxVersions(5).build();
        tableModel.sethFamilies(familyBuilder);

        String[] keys = new String[]{
                "A",
                "D",
                "G",
                "K",
                "O",
                "T"
        };
        boolean res = hBaseTemplate.createTable(tableModel, keys);
        System.out.println(res);
    }

    @Test
    public void testAddFamily() {
        String tableName = "TEST:leo_test5";
        HFamilyBuilder familyBuilder = new HFamilyBuilder.Builder().familyName("INFO6")
                .minVersions(0).maxVersions(1).build();
        boolean res = hBaseTemplate.addFamily(tableName, familyBuilder);
        System.out.println(res);
    }

    @Test
    public void testGetTableDesc() {
        String tableName = "TEST:leo_test5";
        String desc = hBaseTemplate.getTableDescriptor(tableName);

        System.out.println(desc);
    }

    @Test
    public void enableReplicationScope() {
        String tableName = "TEST:leo_test5";
        boolean desc = hBaseTemplate.enableReplicationScope(tableName, "INFO6", "INFO3");

        System.out.println(desc);
    }

    @Test
    public void disableReplicationScope() {
        String tableName = "TEST:leo_test5";
        boolean desc = hBaseTemplate.disableReplicationScope(tableName, "INFO6", "INFO3");

        System.out.println(desc);
    }

    @Test
    public void renameTable() {
        String oriTableName = "TEST:leo_test5";
        String newTableName = "TEST:leo_test5_new2";

        boolean res = hBaseTemplate.renameTable(oriTableName, newTableName, true);

        System.out.println(res);
    }

    @Test
    public void testAddSnapshot(){
        String tableName = "TEST:leo_test2";
        boolean res = hBaseTemplate.createSnapshot(tableName,"leo_test2_snapshot1");
        boolean res2 = hBaseTemplate.createSnapshot(tableName,"leo_test2_snapshot2");

        System.out.println(res);

        System.out.println(res2);
    }

    @Test
    public void deleteSnapshots(){
        System.out.println(hBaseTemplate.deleteSnapshot("leo_test2_snapshot2"));
    }

    @Test
    public void  listSnapshots(){
        System.out.println(hBaseTemplate.listSnapshots());
    }

    @Test
    public void listSnapshotsOfOneTable(){
        System.out.println(hBaseTemplate.listSnapshots("TEST:leo_test3"));
    }


}

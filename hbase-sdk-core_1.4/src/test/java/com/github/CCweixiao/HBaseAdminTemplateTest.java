package com.github.CCweixiao;

import com.github.CCweixiao.hbtop.Record;
import com.github.CCweixiao.hbtop.RecordFilter;
import com.github.CCweixiao.hbtop.Summary;
import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.field.FieldValue;
import com.github.CCweixiao.hbtop.field.FieldValueType;
import com.github.CCweixiao.hbtop.mode.Mode;
import com.github.CCweixiao.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * HBase admin template test
 *
 * @author leojie 2020/8/15 10:28 上午
 */
public class HBaseAdminTemplateTest {
    private HBaseAdminTemplate hBaseTemplate;

    @Before
    public void initHBaseTemplate1() {
        Properties properties = new Properties();

        properties.setProperty("hbase.zookeeper.quorum", "localhost");
        properties.setProperty("hbase.zookeeper.property.clientPort", "2181");

        hBaseTemplate = new HBaseAdminTemplate(properties);
    }

    @Test
    public void testListNamespace() {
        List<String> namespaces = hBaseTemplate.listNamespaceNames();
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
        String tableName = "USER6";
        ColumnFamilyDesc familyDesc1 = new ColumnFamilyDesc.Builder()
                .defaultColumnFamilyDesc("INFO").build();

        ColumnFamilyDesc familyDesc2 = new ColumnFamilyDesc.Builder()
                .defaultColumnFamilyDesc("INFO2").build();

        HTableDesc tableDesc = new HTableDesc.Builder()
                .defaultTableDescWithNs("default", tableName)
                .addTableProp("createUser", "leo")
                .addColumnFamilyDesc(familyDesc1)
                .addColumnFamilyDesc(familyDesc2).build();



        hBaseTemplate.createTable(tableDesc);
    }

    @Test
    public void getTableDesc() {
        String tableName = "TEST:LEO_USER";
        final HTableDesc tableDesc = hBaseTemplate.getTableDesc(tableName);
    }

    @Test
    public void deleteTable() {
        String tableName = "leo_test";
       /* boolean disabled = hBaseTemplate.isTableDisabled(tableName);
        if (!disabled) {
            hBaseTemplate.disableTable(tableName, false);
        }*/
        boolean res = hBaseTemplate.deleteTable(tableName);
        System.out.println(res);
    }

    @Test
    public void testGetSummary() {
        final Summary summary = hBaseTemplate.refreshSummary();
        System.out.println(summary);
    }

    @Test
    public void testGetRecord() {
        List<RecordFilter> recordFilters = new ArrayList<>();
        RecordFilter recordFilter =RecordFilter.newBuilder(Field.TABLE, false)
                .equal(new FieldValue("USER_NEW", FieldValueType.STRING));
        RecordFilter recordFilter2 =RecordFilter.newBuilder(Field.NAMESPACE, false)
                .equal(new FieldValue("TEST", FieldValueType.STRING));
        recordFilters.add(recordFilter);
        recordFilters.add(recordFilter2);

        final List<Record> records = hBaseTemplate.refreshRecords(Mode.REGION, recordFilters, Field.LAST_MAJOR_COMPACTION_TIME, false);
        System.out.println(records);
    }

    @Test
    public void testHBaseRegionRecords(){
        final List<HBaseRegionRecord> hBaseRegionRecords = hBaseTemplate.refreshRegionRecords("LLL:LEOLEO", null, false);
        System.out.println(hBaseRegionRecords);
    }

    @Test
    public void testHBaseTableRecords() {
        final List<HBaseTableRecord> hBaseTableRecords = hBaseTemplate.refreshTableRecords(Field.REGION_COUNT, false);
        System.out.println(hBaseTableRecords);
    }

    @Test
    public void testGetFamilyCom() {
        String tableName = "TEST:USER3";
        final HTableDesc leo_test2 = hBaseTemplate.getTableDesc(tableName);
        System.out.println(leo_test2);
    }

    @Test
    public void testGetTableDesc() {
        String tableName = "TEST:USER";
        final HTableDesc tableDesc = hBaseTemplate.getTableDesc(tableName);

        System.out.println(tableDesc);
    }

    @Test
    public void testListTableDesc() {
        final List<HTableDesc> tableDescList = hBaseTemplate.listTableDesc(true);
        System.out.println(tableDescList);
    }

    @Test
    public void testReNameTable() {
        hBaseTemplate.renameTable("TEST:USER", "TEST:USER_NEW", false, true);
        hBaseTemplate.renameTable("TEST:USER3", "TEST:USER3_NEW", false, true);
    }

    @Test
    public void testTruncateTable() {
        hBaseTemplate.truncateTable("leo_test", true);
    }

    @Test
    public void testAddFamily(){
        ColumnFamilyDesc familyDesc = new ColumnFamilyDesc.Builder().defaultColumnFamilyDesc("INFO2").build();

        hBaseTemplate.addFamily("leo_test22",familyDesc);
    }

    @Test
    public void changeFamily(){
        ColumnFamilyDesc familyDesc = new ColumnFamilyDesc.Builder()
                .defaultColumnFamilyDesc("info").build();

        hBaseTemplate.modifyFamily("leo_test", familyDesc);
    }

    @Test
    public void testDeleteFamily(){
        hBaseTemplate.deleteFamily("leo_test","INFO2");
    }

    @Test
    public void testGetFamily(){
        final HTableDesc tableDesc = hBaseTemplate.getTableDesc("leo_test");
        final List<ColumnFamilyDesc> familyDescList = tableDesc.getColumnFamilyDescList();
        System.out.println(tableDesc);
    }

    @Test
    public void testTableIsExists(){
        hBaseTemplate.tableIsNotExistsError("TEST:USER");
    }

    @Test
    public void testMergeHBaseRegions(){
        hBaseTemplate.mergeTableSmallRegions("TEST:LEO_TEST", 100, 1024);
    }

}

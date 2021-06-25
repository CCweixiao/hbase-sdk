package com.github.CCweixiao;

import com.github.CCweixiao.hbtop.Record;
import com.github.CCweixiao.hbtop.RecordFilter;
import com.github.CCweixiao.hbtop.Summary;
import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.field.FieldValue;
import com.github.CCweixiao.hbtop.field.FieldValueType;
import com.github.CCweixiao.hbtop.mode.Mode;
import com.github.CCweixiao.model.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
        hBaseTemplate = new HBaseAdminTemplate("localhost", "2181");
    }

    @Test
    public void testListNamespace() {
        List<String> namespaces = hBaseTemplate.listNamespaceNames();
        System.out.println(namespaces);
    }

    @Test
    public void testCreateNamespace() {
    }

    @Test
    public void testListTableDesc() {
        final List<HTableDesc> tableDescList = hBaseTemplate.listTableDesc(true);
        System.out.println(tableDescList);
    }

    @Test
    public void testGetTableDesc(){
       HTableDesc tableDesc = hBaseTemplate.getTableDesc("hbase:meta");

        System.out.println(tableDesc);
    }

    @Test
    public void testCreateTable() {
        String tableName = "USER19";
        ColumnFamilyDesc familyDesc1 = new ColumnFamilyDesc.Builder()
                .defaultColumnFamilyDesc("INFO").build();

        ColumnFamilyDesc familyDesc2 = new ColumnFamilyDesc.Builder()
                .defaultColumnFamilyDesc("INFO2").build();

        HTableDesc tableDesc = new HTableDesc.Builder()
                .defaultTableDesc(tableName)
                .addTableProp("createUser", "leo")
                .addColumnFamilyDesc(familyDesc1)
                .addColumnFamilyDesc(familyDesc2)
                .build();
        hBaseTemplate.createTable(tableDesc);
    }

    @Test
    public void testModifyTable(){
//        HTableDesc tableDesc = new TableDesc();
//        tableDesc.setTableName("USER6");
//        Map<String,String> prop = new HashMap<>();
//        prop.put("name","leo");
//        tableDesc.setTableProps(prop);
//        hBaseTemplate.modifyTableProps(tableDesc);
    }

    @Test
    public void testHBaseSummary() {
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
    public void testHBaseRecords() {
        List<RecordFilter> recordFilters = new ArrayList<>();
        RecordFilter recordFilter =RecordFilter.newBuilder(Field.NAMESPACE, false)
                .notEqual(new FieldValue("hbase", FieldValueType.STRING));
        recordFilters.add(recordFilter);


        final List<Record> records = hBaseTemplate.refreshRecords(Mode.TABLE, recordFilters, Field.REGION_COUNT, false);
        Record record = records.get(0);
        double memSize = record.get(Field.STORE_FILE_SIZE).asSize().get();
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
    public void testHBaseTableRecord() {
        HBaseTableRecord tableRecord = hBaseTemplate.refreshTableRecord("LLL:LEOLEO");
        System.out.println(tableRecord);
    }

    @Test
    public void testMergeHBaseRegions(){
        hBaseTemplate.mergeTableSmallRegions("TEST:LEO_TEST", 100, 1024);
    }


    @Test
    public void testMergeMultipleRegions(){
        String[] regions = {"844f9ab408a55a2e85c8f50a219e9ad7", "f18f1587819735e7ee09898d7a0bae5d",
        "106b95cec9830be239677cad8ef737d0", "44d4d52e438d4ea0e79b5312c9e0e020", "aec6d775711c92ae61ed5e32c22a8c13"};

        byte[][] byteRegions = new byte[regions.length][];
        for (int i = 0; i < regions.length; i++) {
            byteRegions[i] = Bytes.toBytes(regions[i]);
        }

        hBaseTemplate.mergeMultipleRegions(byteRegions, false);
    }
}

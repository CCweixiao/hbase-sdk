package com.github.CCweixiao;

import com.github.CCweixiao.hbtop.Record;
import com.github.CCweixiao.hbtop.RecordFilter;
import com.github.CCweixiao.hbtop.Summary;
import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.field.FieldValue;
import com.github.CCweixiao.hbtop.field.FieldValueType;
import com.github.CCweixiao.hbtop.mode.Mode;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.TableDesc;
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
        final List<TableDesc> tableDescList = hBaseTemplate.listTableDesc();
        System.out.println(tableDescList);
    }

    @Test
    public void testCreateTable() {
        String tableName = "USER5";
        TableDesc tableDesc = new TableDesc();
        tableDesc.setNamespaceName("");
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

        tableDesc = tableDesc.addFamilyDesc(familyDesc1).addFamilyDesc(familyDesc2);


        hBaseTemplate.createTable(tableDesc);
    }

    @Test
    public void testHBaseSummary() {
        final Summary summary = hBaseTemplate.refreshSummary();
        System.out.println(summary);
    }

    @Test
    public void testHBaseRecords() {
        List<RecordFilter> recordFilters = new ArrayList<>();
        RecordFilter recordFilter =RecordFilter.newBuilder(Field.TABLE, true)
                .equal(new FieldValue("USER_NEW", FieldValueType.STRING));
        recordFilters.add(recordFilter);

        final List<Record> records = hBaseTemplate.refreshRecords(Mode.REGION, recordFilters, Field.LAST_MAJOR_COMPACTION_TIME, false);
        System.out.println(records);
    }


}

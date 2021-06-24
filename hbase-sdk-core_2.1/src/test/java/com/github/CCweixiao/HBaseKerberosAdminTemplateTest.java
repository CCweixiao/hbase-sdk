package com.github.CCweixiao;

import com.github.CCweixiao.hbtop.Record;
import com.github.CCweixiao.hbtop.RecordFilter;
import com.github.CCweixiao.hbtop.Summary;
import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.field.FieldValue;
import com.github.CCweixiao.hbtop.field.FieldValueType;
import com.github.CCweixiao.hbtop.mode.Mode;
import com.github.CCweixiao.model.FamilyDesc;
import com.github.CCweixiao.model.HTableDesc;
import com.github.CCweixiao.model.TableDesc;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * HBase admin template test
 *
 * @author leojie 2020/8/15 10:28 上午
 */
public class HBaseKerberosAdminTemplateTest {
    private HBaseAdminTemplate hBaseTemplate;

    @Before
    public void initHBaseTemplate() {
        Properties properties = new Properties();
        properties.setProperty("java.security.krb5.conf", "/Users/mac/leo_project/hbase-sdk/hbase-sdk-core_2.1/src/test/resources/conf/krb5.conf");
        properties.setProperty("hadoop.security.authentication", "kerberos");
        properties.setProperty("hbase.security.authentication", "kerberos");

        properties.setProperty("keytab.file", "/Users/mac/leo_project/hbase-sdk/hbase-sdk-core_2.1/src/test/resources/conf/hadoop.keytab");
        properties.setProperty("kerberos.principal", "hadoop@LEO.COM");

        properties.setProperty("hbase.master.kerberos.principal", "hbase/_HOST@LEO.COM");
        properties.setProperty("hbase.regionserver.kerberos.principal", "hbase/_HOST@LEO.COM");

        properties.setProperty("hbase.zookeeper.quorum", "node2.bigdata.leo.com,node1.bigdata.leo.com,node3.bigdata.leo.com");
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

    }

    @Test
    public void testListTableDesc() {
        final List<HTableDesc> tableDescList = hBaseTemplate.listTableDesc();
        System.out.println(tableDescList);
    }

    @Test
    public void testCreateTable() {
        String tableName = "USER6";
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


        // hBaseTemplate.createTable(tableDesc);
    }

    @Test
    public void testModifyTable(){
//        TableDesc tableDesc = new TableDesc();
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
    public void testHBaseRecords() {
        List<RecordFilter> recordFilters = new ArrayList<>();
        RecordFilter recordFilter =RecordFilter.newBuilder(Field.TABLE, true)
                .equal(new FieldValue("USER_NEW", FieldValueType.STRING));
        recordFilters.add(recordFilter);

        final List<Record> records = hBaseTemplate.refreshRecords(Mode.REGION, recordFilters, Field.LAST_MAJOR_COMPACTION_TIME, false);
        System.out.println(records);
    }


}

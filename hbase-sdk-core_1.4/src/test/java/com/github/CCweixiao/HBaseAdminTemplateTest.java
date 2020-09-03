package com.github.CCweixiao;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;
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
        Map<String, String> para = new HashMap<>();
        para.put("tag", "测试命名空间");
        para.put("createBy", "leo");
        para.put("updateBy", "");
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespaceName)
                .addConfiguration(para)
                .build();
        hBaseTemplate.createNamespace(namespaceDescriptor);
    }

    @Test
    public void testGetNamespace() {
        String namespaceName = "LEO_NS2";
        final NamespaceDescriptor namespaceDescriptor = hBaseTemplate.getNamespaceDescriptor(namespaceName);
        System.out.println(namespaceDescriptor.getName());
        System.out.println(namespaceDescriptor.getConfiguration());
    }

    @Test
    public void testDeleteNamespace() {
        String namespaceName = "LEO_NS2";
        hBaseTemplate.deleteNamespace(namespaceName);
    }

    @Test
    public void testCreateTable() {
        String tableName = "LEO_NS:USER";

        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));

        //tableDescriptor.setConfiguration("tag", "测试用户表");
        //tableDescriptor.setConfiguration("createUser", "leo");

        tableDescriptor.setValue("tag", "测试用户表");
        tableDescriptor.setValue("createUser", "leo");


        HColumnDescriptor columnDescriptor = new HColumnDescriptor("INFO");
        columnDescriptor.setScope(1);
        //columnDescriptor.setCompressionType(Compression.Algorithm.SNAPPY);
        columnDescriptor.setTimeToLive(2147483647);
        columnDescriptor.setMaxVersions(3);

        HColumnDescriptor columnDescriptor2 = new HColumnDescriptor("INFO2");
        columnDescriptor2.setScope(0);
        columnDescriptor2.setTimeToLive(864000);
        columnDescriptor2.setMaxVersions(3);

        tableDescriptor.addFamily(columnDescriptor).addFamily(columnDescriptor2);

        hBaseTemplate.createTable(tableDescriptor, Bytes.toBytes(0), Bytes.toBytes(100), 10);
    }

    @Test
    public void getTableDesc() {
        String tableName = "LEO_NS:USER";
        final HTableDescriptor tableDescriptor = hBaseTemplate.getTableDescriptor(tableName);

        System.out.println(tableDescriptor.getValue("createUser"));
        System.out.println(tableDescriptor.getValue("tag"));

        System.out.println(tableDescriptor.toString());
    }

    @Test
    public void deleteTable() {
        String tableName = "LEO_NS:USER";
        boolean disabled = hBaseTemplate.isTableDisabled(tableName);
        if (!disabled) {
            hBaseTemplate.disableTable(tableName);
        }
        boolean res = hBaseTemplate.deleteTable(tableName);
        System.out.println(res);
    }
}

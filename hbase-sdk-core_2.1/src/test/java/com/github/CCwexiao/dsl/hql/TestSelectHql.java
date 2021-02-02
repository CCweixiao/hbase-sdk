package com.github.CCwexiao.dsl.hql;

import com.github.CCweixiao.HBaseSqlTemplate;
import com.github.CCweixiao.hql.config.DefaultHBaseTableConfig;
import com.github.CCwexiao.dsl.client.HBaseCellResult;
import com.github.CCwexiao.dsl.config.HBaseColumnSchema;
import com.github.CCwexiao.dsl.config.HBaseTableConfig;
import com.github.CCwexiao.dsl.config.HBaseTableSchema;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2020/11/28 10:30 下午
 */
public class TestSelectHql {
    private HBaseSqlTemplate hBaseSqlTemplate;

    @Before
    public void testInitHBaseSqlTemplate() {
        hBaseSqlTemplate = new HBaseSqlTemplate("localhost", "2181");

        List<HBaseColumnSchema> hBaseColumnSchemas = createHBaseColumnSchemaList();
        HBaseTableSchema hBaseTableSchema = new HBaseTableSchema();
        hBaseTableSchema.setTableName("LEO_USER");
        hBaseTableSchema.setDefaultFamily("g");

        HBaseTableConfig hBaseTableConfig = new DefaultHBaseTableConfig(hBaseTableSchema, hBaseColumnSchemas);

        hBaseSqlTemplate.setHBaseTableConfig(hBaseTableConfig);
    }

    @Test
    public void testSelectInSql(){
        String hql = "select * from LEO_USER where rowKey is stringkey in  ( '1001' , '1002' ) limit 10";
        final List<List<HBaseCellResult>> listList = hBaseSqlTemplate.select(hql);

        listList.forEach(dataList -> {
            dataList.forEach(data -> {
                System.out.println(data.getRowKey());
                System.out.println(data.getFamilyStr());
                System.out.println(data.getQualifierStr());
                System.out.println(data.getValue());
                System.out.println(data.getTsDate());
                System.out.println("########################################");

            });
        });
    }

    @Test
    public void testSelectSql() {
        String sql = "select ( g:id , g:name , g:age , g:address ) from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10002' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) )  maxversion is 2  startTS is '1604160000000' , endTS is '1604160000001' limit 10 ";
        sql = "select * from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10002' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) )  maxversion is 2  startTS is '1604160000000' , endTS is '1604160000001' limit 10 ";

        final List<List<HBaseCellResult>> listList = hBaseSqlTemplate.select(sql);

        listList.forEach(dataList -> {
            dataList.forEach(data -> {
                System.out.println(data.getRowKey());
                System.out.println(data.getFamilyStr());
                System.out.println(data.getQualifierStr());
                System.out.println(data.getValue());
                System.out.println(data.getTsDate());
                System.out.println("########################################");

            });
        });
    }

    @Test
    public void testInsertSql() {
        String sql = "insert into LEO_USER ( g:id , g:name , g:age , g:address ) values ( '10001', 'leo' , '18', 'shanghai' ) where rowKey is stringkey ( 'a10002' ) ts is '1604160000000'";
        hBaseSqlTemplate.insert(sql);
        System.out.println("insert successfully!");
    }

    @Test
    public void testDeleteSql(){
        String sql = "delete ( id , name ) from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10003' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) ) ts is '1604160000000'";
        sql = "delete * from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10003' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) ) ts is '1604160000000'";
        sql = "delete * from LEO_USER where rowKey is stringkey ( 'a10002' ) ( name equal 'leo' or age less '21' ) ts is '1604160000000'";

        hBaseSqlTemplate.delete(sql);
    }

    public List<HBaseColumnSchema> createHBaseColumnSchemaList() {
        List<HBaseColumnSchema> hBaseColumnSchemas = new ArrayList<>();

        HBaseColumnSchema hBaseColumnSchema1 = new HBaseColumnSchema();
        hBaseColumnSchema1.setFamily("g");
        hBaseColumnSchema1.setQualifier("id");
        hBaseColumnSchema1.setTypeName("string");

        HBaseColumnSchema hBaseColumnSchema2 = new HBaseColumnSchema();
        hBaseColumnSchema2.setFamily("g");
        hBaseColumnSchema2.setQualifier("name");
        hBaseColumnSchema2.setTypeName("string");

        HBaseColumnSchema hBaseColumnSchema3 = new HBaseColumnSchema();
        hBaseColumnSchema3.setFamily("g");
        hBaseColumnSchema3.setQualifier("age");
        hBaseColumnSchema3.setTypeName("int");

        HBaseColumnSchema hBaseColumnSchema4 = new HBaseColumnSchema();
        hBaseColumnSchema4.setFamily("g");
        hBaseColumnSchema4.setQualifier("address");
        hBaseColumnSchema4.setTypeName("string");

        hBaseColumnSchemas.add(hBaseColumnSchema1);
        hBaseColumnSchemas.add(hBaseColumnSchema2);
        hBaseColumnSchemas.add(hBaseColumnSchema3);
        hBaseColumnSchemas.add(hBaseColumnSchema4);

        return hBaseColumnSchemas;
    }
}

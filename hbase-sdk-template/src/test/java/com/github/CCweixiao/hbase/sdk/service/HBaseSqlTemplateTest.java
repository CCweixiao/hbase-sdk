package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

/**
 * @author leojie 2022/11/27 17:44
 */
public class HBaseSqlTemplateTest extends AbstractHBaseTemplateTest {
    @Before
    public void init() {
        initIHBaseSqlTemplate();
    }

    @Test
    public void testInsert() {
        List<String> hqlList = mockHql();
        for (String hql : hqlList) {
            sqlTemplate.insert(hql);
        }
    }

    @Test
    public void testInsertByRowFunction() {
        String hsql1 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '11111' , 'a_leo' , 15 , 'bj' ) where rowKey = md5 ( 'a1111' )";
        String hsql2 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '11111' , 'a_leo' , 15 , 'bj' ) where rowKey = md5_prefix ( 'a1111' )";
        sqlTemplate.insert(hsql1);
        sqlTemplate.insert(hsql2);

        String sql1 = "select * from test:test_sql where rowKey = md5 ( 'a1111' )";
        HBaseDataSet dataSet1 = sqlTemplate.select(sql1);
        dataSet1.show();

        System.out.println("============================================================");

        String sql2 = "select * from test:test_sql where rowKey = md5_prefix ( 'a1111' )";
        HBaseDataSet dataSet2 = sqlTemplate.select(sql2);
        dataSet2.show();

    }

    @Test
    public void testSelectRow() {
        String hql1 = "create virtual table test:test_sql (\n" +
                " row_key string isrowkey,\n" +
                " f1:id string nullable,\n" +
                " f1:name string nullable,\n" +
                " f1:age int nullable,\n" +
                " f1:job string nullable,\n" +
                " f1:pay double nullable,\n" +
                " f2:address string nullable,\n" +
                " f2:commuter string nullable\n" +
                " );";
        sqlTemplate.createVirtualTable(hql1);
         // String hql2 = "drop virtual table test:test_sql;";
        // sqlTemplate.dropVirtualTable(hql2);
//        sqlTemplate.createVirtualTable(hql1);
        String sql1 = "select * from test:test_sql where rowKey='c1005'";
        HBaseDataSet dataSet1 = sqlTemplate.select(sql1);
        dataSet1.show();
    }

    @Test
    public void testSelectByStartAndEndRow() {
        String sql2 = "select * from test:test_sql where startKey > 'a1000' and endKey < 'a1002'";
        HBaseDataSet dataSet2 = sqlTemplate.select(sql2);
        dataSet2.show();
    }

    @Test
    public void testSelectInRows() {
        String sql3 = "select * from\n" +
                " test:test_sql where \n" +
                "rowKey \n" +
                "in('a1000','a1002')";
        HBaseDataSet dataSet3 = sqlTemplate.select(sql3);
        dataSet3.show();
    }

    @Test
    public void testSelectTimeRange() {
        String sql1 = "select * from test:test_sql where \n" +
                "-- rowKey = 222\n" +
                "startkey >= 'ewew', endKey < 'dsdsd'\n" +
                "and (f1:name = 'ds' or f1:age < 12 or (f1:pay between 10 and 20))\n" +
                "and ( startTs > 1212 , endTs <= 23 )\n" +
                "-- and startTs >= 1212 \n" +
                "limit 10";
        HBaseDataSet dataSet1 = sqlTemplate.select(sql1);
    }

    @Test
    public void testSelectRowFunction() {
        String sql1 = "select * from test:test_sql where \n" +
                "rowKey=md5('a1001') and  maxVersion = 3 and ts = 3231 limit 10e";
        HBaseDataSet dataSet1 = sqlTemplate.select(sql1);

//        String sql2 = "select * from test:test_sql where \n" +
//                "startKey=md5('a1001') and endKey = '123' ";
//        HBaseDataSet dataSet2 = sqlTemplate.select(sql2);
//
//        String sql3 = "select * from test:test_sql where \n" +
//                "rowKey in (md5('a1001'), md5('a1002'), 'ewe') ";
//        HBaseDataSet dataSet3 = sqlTemplate.select(sql3);
    }

    @Test
    public void testSelectFilter() {
        String sql = "select * from test:test_sql where startkey > 'a1000' and endkey <= 'g1005' and " +
                "f1:age > 18 and ( (f1:pay > 10000 and f1:job is not null) and (f2:commuter is null " +
                "or f2:commuter != 'subway' ))";

        HBaseDataSet dataSet = sqlTemplate.select(sql);
        dataSet.show();
    }

    @Test
    public void testInsertMaxVersionData() {
        String hsql1 = "insert into test:test_sql (row_key, f1:id , f1:name , f1:age )\n" +
                " values ('r1', 'id1_v1' , 'leo1_v1' , 11 ),\n" +
                " ('r2', 'id2_v1' , 'leo2_v1' , 21 ),\n" +
                " ('r3', 'id3_v1' , 'leo3_v1' , 31 )";
        sqlTemplate.insert(hsql1);
        String hsql2 = "insert into test:test_sql (row_key, f1:id , f1:name , f1:age )\n" +
                " values ('r1', 'id2' , 'leo1_v2' , 12 ),\n" +
                " ('r2', 'id2' , 'leo2_v2' , 22 ),\n" +
                " ('r3', 'id2' , 'leo2_v2' , 32 )";
        sqlTemplate.insert(hsql2);
        String hsql3 = "insert into test:test_sql (row_key, f1:id , f1:name , f1:age )\n" +
                " values ('r1', 'id3' , 'leo1_v3' , 13 ),\n" +
                " ('r2', 'id3' , 'leo2_v3' , 23 ),\n" +
                " ('r3', 'id3' , 'leo3_v3' , 33 )";
        sqlTemplate.insert(hsql3);
    }

    @Test
    public void testDelete() {
        String hql = "delete * from test:test_sql where rowkey in ('r1', 'r2', 'r3')";
        sqlTemplate.delete(hql);
    }
    @Test
    public void testSelectMaxVersion() {
        String hql = "select f1:id,f1:name,f1:age from test:test_sql where rowKey in ('r1','r2','r3') " +
                "and maxversion = 3";
        HBaseDataSet dataSet = sqlTemplate.select(hql);
        dataSet.show(true);
    }



    @Test
    public void testSelect() {


        System.out.println("============================================================");



        System.out.println("============================================================");




        String sql4 = "select * from test:test_sql where ( startKey = 'a1000' , endKey = 'j1009' ) and ( f1:age >= 25 and ( f1:job IS NOT NULL ) ) ";
        HBaseDataSet dataSet4 = sqlTemplate.select(sql4);
        dataSet4.show();
        System.out.println("============================================================");


        String sql5 = "select * from test:test_sql where ( startKey = 'a1000' , endKey = 'j1009' ) and ( f1:age >= 25 and ( f1:job IS NULL or f1:pay > 15000 ) )";
        HBaseDataSet dataSet5 = sqlTemplate.select(sql5);
        dataSet5.show();
        System.out.println("============================================================");


//
//        System.out.println("============================================================");
//        String sql32 = "select f1:name , f1:age from test:test_sql where rowKey = 'row_1000' and maxVersion = 3";
//        HBaseDataSet dataSet32 = sqlTemplate.select(sql32);
//        dataSet32.show();
    }

    @Test
    public void testSelectMaxVersion2() {
        String hsql1 = "insert into test:test_sql ( f1:id ) values ( '11111_v1' ) where rowKey = 'row_10002'";
        String hsql2 = "insert into test:test_sql ( f1:id , f1:age ) values ( '11111_v2' , 14 ) where rowKey = 'row_10002'";
        sqlTemplate.insert(hsql1);
        sqlTemplate.insert(hsql2);

        String sql32 = "select * from test:test_sql where rowKey = 'row_10002' and maxVersion = 2";
        HBaseDataSet dataSet32 = sqlTemplate.select(sql32);
        dataSet32.show();
    }

    @Test
    public void testSelectByFilter() {

        String sql3 = "select * from test:test_sql where rowKey in ( 'a10001' , 'a10002' , 'a10003' )";
        HBaseDataSet dataSet2 = sqlTemplate.select(sql3);
        dataSet2.show();

        System.out.println("============================================================");

        String sql = "select * from test:test_sql where ( startKey = 'a10001' , endKey = 'a10006' ) and f1:age <= 18";
        HBaseDataSet dataSet = sqlTemplate.select(sql);
        dataSet.show();

        System.out.println("============================================================");

        String sql2 = "select * from test:test_sql where ( startKey = 'a10001' , endKey = 'a10006' ) and f1:age <= 18 limit 2";
        HBaseDataSet dataSet1 = sqlTemplate.select(sql2);
        dataSet1.show();


    }

    @Test
    public void testSelectOne() {
        String sql = "select * from test:test_sql where rowKey = 'row_10002'";
        HBaseDataSet dataSet = sqlTemplate.select(sql);
        dataSet.show();
    }

    @Test
    public void testDeleteOne() {
        // String sql = "delete f1:age from test:test_sql where rowKey = 'b20004'";
        // sqlTemplate.delete(sql);

        String sql2 = "delete f1:age from test:test_sql where rowKey = 'row_10001' and ts = 1670579504803";
        sqlTemplate.delete(sql2);
    }

    @Test
    public void printDefaultTableSchema() {
        System.out.println(defaultTableSchemaJsonFormat());
    }
    public String defaultTableSchemaJsonFormat() {
        return "{\n" +
                "\t\"tableName\": \"test_table\",\n" +
                "\t\"defaultFamily\": \"cf\",\n" +
                "\t\"columnList\": [{\n" +
                "\t    \"familyName\": \"\",\n" +
                "\t    \"columnName\": \"rowKey\",\n" +
                "\t    \"columnType\": \"string\",\n" +
                "\t    \"columnIsRow\": \"true\"\n" +
                "\t},{\n" +
                "\t    \"familyName\": \"cf\",\n" +
                "\t    \"columnName\": \"name\",\n" +
                "\t    \"columnType\": \"string\",\n" +
                "\t    \"columnIsRow\": \"true\"\n" +
                "\t},]\n" +
                "}";
    }

}

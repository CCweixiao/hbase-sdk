package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.common.model.row.HBaseDataSet;
import org.junit.Before;
import org.junit.Test;
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
        String hql1 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10001' , 'a_leo' , 15 , 'bj' ) where rowKey = 'a10001'";
        String hql2 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10002' , 'b_leo' , 16 , 'sh' ) where rowKey = 'a10002'";
        String hql3 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10003' , 'c_leo' , 17 , 'gz' ) where rowKey = 'a10003'";
        String hql4 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10004' , 'd_leo' , 18 , 'tj' ) where rowKey = 'a10004'";
        String hql5 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10005' , 'e_leo' , 19 , 'sz' ) where rowKey = 'a10005'";

        String hql11 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10001' , 'a_leo' , 15 , 'zz' ) where rowKey = 'b10001'";
        String hql21 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10002' , 'b_leo' , 16 , 'cq' ) where rowKey = 'b10002'";
        String hql31 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10003' , 'c_leo' , 17 , 'yc' ) where rowKey = 'b10003'";
        String hql41 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10004' , 'd_leo' , 18 , 'sx' ) where rowKey = 'b20004'";
        String hql51 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10005' , 'e_leo' , 19 , 'gs' ) where rowKey = 'b20005'";
        sqlTemplate.insert(hql1);
        sqlTemplate.insert(hql2);
        sqlTemplate.insert(hql3);
        sqlTemplate.insert(hql4);
        sqlTemplate.insert(hql5);
        sqlTemplate.insert(hql11);
        sqlTemplate.insert(hql21);
        sqlTemplate.insert(hql31);
        sqlTemplate.insert(hql41);
        sqlTemplate.insert(hql51);
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
    public void testSelect() {
        String sql1 = "select * from test:test_sql where rowKey = 'a10001'";
        HBaseDataSet dataSet1 = sqlTemplate.select(sql1);
        dataSet1.show();

        System.out.println("============================================================");

        String sql = "select * from test:test_sql where ( startKey = 'a10001' , endKey = 'b20006' )";
        HBaseDataSet dataSet = sqlTemplate.select(sql);
        dataSet.show();

        System.out.println("============================================================");
        String sql32 = "select f1:name , f1:age from test:test_sql where rowKey = 'row_1000' and maxVersion = 3";
        HBaseDataSet dataSet32 = sqlTemplate.select(sql32);
        dataSet32.show();
    }

    @Test
    public void testSelectMaxVersion() {
        String hsql1 = "insert into test:test_sql ( f1:id , f1:name , f1:age ) values ( '11111_v1' , 'a_leo_v1' , 13 ) where rowKey = 'row_10001'";
        String hsql2 = "insert into test:test_sql ( f1:id , f1:name , f1:age ) values ( '11111_v2' , 'a_leo_v2' , 14 ) where rowKey = 'row_10001'";
        String hsql3 = "insert into test:test_sql ( f1:id , f1:name , f1:age ) values ( '11111_v3' , 'a_leo_v3' , 15 ) where rowKey = 'row_10001'";
        sqlTemplate.insert(hsql1);
        sqlTemplate.insert(hsql2);
        sqlTemplate.insert(hsql3);

        String sql32 = "select * from test:test_sql where rowKey = 'row_10001' and maxVersion = 3";
        HBaseDataSet dataSet32 = sqlTemplate.select(sql32);
        dataSet32.show();
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

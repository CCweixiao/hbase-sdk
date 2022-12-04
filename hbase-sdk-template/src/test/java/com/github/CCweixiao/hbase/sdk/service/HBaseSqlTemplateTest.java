package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.common.model.HBaseCellResult;
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
        String hql1 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10001' , 'a_leo' , 15 , 'beijing' ) where rowKey = 'a10001'";
        String hql2 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10002' , 'b_leo' , 16 , 'shanghai' ) where rowKey = 'a10002'";
        String hql3 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10003' , 'c_leo' , 17 , 'guangzhou' ) where rowKey = 'a10003'";
        String hql4 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10004' , 'd_leo' , 18 , 'tianjin' ) where rowKey = 'a10004'";
        String hql5 = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10005' , 'e_leo' , 19 , 'shenzhen' ) where rowKey = 'a10005'";
        sqlTemplate.insert(hql1);
        sqlTemplate.insert(hql2);
        sqlTemplate.insert(hql3);
        sqlTemplate.insert(hql4);
        sqlTemplate.insert(hql5);
    }

    @Test
    public void testSelect() {
        String sql = "select * from test:test_sql where ( startKey = 'a10001' , endKey = 'a10003' )";
        List<List<HBaseCellResult>> select = sqlTemplate.select(sql);
        System.out.println(select);

        String sql1 = "select * from test:test_sql where rowKey = 'a10001'";
        List<List<HBaseCellResult>> select1 = sqlTemplate.select(sql1);
        System.out.println(select1);
    }

    @Test
    public void testSelectByFilter() {
        String sql = "select * from test:test_sql where ( startKey = 'a10001' , endKey = 'a10006' ) and f1:age <= 18";
        List<List<HBaseCellResult>> select = sqlTemplate.select(sql);
        System.out.println(select);
    }

    @Test
    public void testSelectOne() {
        String sql = "select * from test:test_sql where rowKey = md5 ( 'a10003' )";
        List<List<HBaseCellResult>> select = sqlTemplate.select(sql);
        System.out.println(select);
    }

    @Test
    public void testDeleteOne() {
        String sql = "delete * from test:test_sql where rowKey = md5 ( 'a10003' )";
        sqlTemplate.delete(sql);
    }

}

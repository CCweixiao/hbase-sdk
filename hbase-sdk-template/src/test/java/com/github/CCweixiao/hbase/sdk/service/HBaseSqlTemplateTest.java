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
        String hql = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10001', 'leo2' , '17', 'beijing' ) where rowKey is stringkey ( 'a10001' )";
        sqlTemplate.insert(hql);
    }

    @Test
    public void testSelect() {
        String sql = "select ( f1:id , f1:name , f1:age , f2:address ) from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10002' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) )  maxversion is 2 startTS is '1604160000000' , endTS is '1604160000001' limit 10 ";
        List<List<HBaseCellResult>> select = sqlTemplate.select(sql);
        System.out.println(select);
    }
}

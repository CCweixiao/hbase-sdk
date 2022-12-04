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
        String hql = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10002', 'leo2' , 17 , 'beijing' ) where rowKey = 'a10002'";
        sqlTemplate.insert(hql);
    }

    @Test
    public void testSelect() {
        String sql = "select * from test:test_sql where ( startKey = 'a10001' , endKey = 'a10003' )";
        List<List<HBaseCellResult>> select = sqlTemplate.select(sql);
        System.out.println(select);
    }
}

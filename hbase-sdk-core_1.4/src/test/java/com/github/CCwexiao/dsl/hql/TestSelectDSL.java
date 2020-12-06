package com.github.CCwexiao.dsl.hql;

import com.github.CCweixiao.client.config.DefaultHBaseSQLRuntimeSetting;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser;
import com.github.CCwexiao.dsl.client.QueryExtInfo;
import com.github.CCwexiao.dsl.config.HBaseSQLRuntimeSetting;
import com.github.CCwexiao.dsl.manual.HBaseSQLContextUtil;
import com.github.CCwexiao.dsl.manual.RowKeyRange;
import com.github.CCwexiao.dsl.util.TreeUtil;
import org.junit.Test;

/**
 * @author leojie 2020/11/28 12:32 下午
 */
public class TestSelectDSL {
    String sql = "select ( info:id , g:name , f:age , info:address ) from test:user where rowKey is intkey ( '123' ) ( id less '12' and name equal 'leo' or age less '12' )  maxversion is 2  startTS is '123' , endTS is '321' limit 10 , 100 ";

    @Test
    public void testParseHBaseRowKeyRange(){
        String sql = "select info:id , g:name , f:age , info:address from test:user where startKey is intkey ( '123' ) , endKey is intkey ( '1234' ) ( id less '12' and name equal 'leo' or age less '12' )  maxversion is 2  startTS is '123' , endTS is '321' limit 10 ";

        final HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(sql);
        HBaseSQLParser.SelecthqlcContext context = HBaseSQLContextUtil.parseSelecthqlcContext(progContext);
        HBaseSQLRuntimeSetting runtimeSetting = new DefaultHBaseSQLRuntimeSetting();
        final RowKeyRange rowKeyRange = HBaseSQLContextUtil.parseRowKeyRange(context.rowKeyRange(), runtimeSetting);

        System.out.println(rowKeyRange);
    }

    @Test
    public void testGetQueryExtInfo(){
        final HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(sql);
        HBaseSQLParser.SelecthqlcContext context = HBaseSQLContextUtil.parseSelecthqlcContext(progContext);
        HBaseSQLRuntimeSetting runtimeSetting = new DefaultHBaseSQLRuntimeSetting();

        QueryExtInfo queryExtInfo = HBaseSQLContextUtil.parseQueryExtInfo(context);

        System.out.println(queryExtInfo);
    }
}

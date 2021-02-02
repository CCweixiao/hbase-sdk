package com.github.CCwexiao.dsl.hql;

import com.github.CCweixiao.hql.config.DefaultHBaseSQLRuntimeSetting;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser;
import com.github.CCwexiao.dsl.client.QueryExtInfo;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.config.HBaseSQLRuntimeSetting;
import com.github.CCwexiao.dsl.manual.HBaseSQLContextUtil;
import com.github.CCwexiao.dsl.manual.RowKeyRange;
import com.github.CCwexiao.dsl.manual.visitor.RowKeyListConstantVisitor;
import com.github.CCwexiao.dsl.util.TreeUtil;
import org.junit.Test;

import java.util.List;

/**
 * @author leojie 2020/11/28 12:32 下午
 */
public class TestSelectDSL {
    @Test
    public void testParseHBaseRowKeyRangeIn(){
        String hql = "select * from LEO_USER where rowKey is stringkey in  ( '1001' , '1002' ) limit 10";
        final HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(hql);
        HBaseSQLParser.SelecthqlcContext context = HBaseSQLContextUtil.parseSelecthqlcContext(progContext);
        String tableName = TreeUtil.parseTableName(progContext);

        HBaseSQLRuntimeSetting runtimeSetting = new DefaultHBaseSQLRuntimeSetting();
        final HBaseSQLParser.RowKeyRangeContext rowKeyRangeContext = context.rowKeyRange();

        RowKeyListConstantVisitor visitor = new RowKeyListConstantVisitor(runtimeSetting);
        final List<RowKey> rowKeyList = rowKeyRangeContext.accept(visitor);

       // final RowKeyRange rowKeyRange = HBaseSQLContextUtil.parseRowKeyRange2(rowKeyRangeContext, runtimeSetting);

        System.out.println(rowKeyList);
    }
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
        String sql = "select info:id , g:name , f:age , info:address from test:user where startKey is intkey ( '123' ) , endKey is intkey ( '1234' ) ( id less '12' and name equal 'leo' or age less '12' )  maxversion is 2  startTS is '123' , endTS is '321' limit 10 ";

        final HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(sql);
        HBaseSQLParser.SelecthqlcContext context = HBaseSQLContextUtil.parseSelecthqlcContext(progContext);
        HBaseSQLRuntimeSetting runtimeSetting = new DefaultHBaseSQLRuntimeSetting();

        QueryExtInfo queryExtInfo = HBaseSQLContextUtil.parseQueryExtInfo(context);

        System.out.println(queryExtInfo);
    }
}

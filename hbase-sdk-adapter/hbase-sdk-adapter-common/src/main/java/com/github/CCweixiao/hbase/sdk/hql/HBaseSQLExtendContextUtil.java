package com.github.CCweixiao.hbase.sdk.hql;

import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;
import com.github.CCweixiao.hbase.sdk.hql.filter.FilterVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.model.TableQuerySetting;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseTableConfig;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.Map;

/**
 * @author leojie 2020/11/28 7:10 下午
 */
public class HBaseSQLExtendContextUtil {

    public static Filter parseFilter(HBaseSQLParser.WherecContext wherecContext,
                                     HBaseTableConfig hBaseTableConfig,
                                     TableQuerySetting runtimeSetting){
        ObjUtil.checkIsNull(hBaseTableConfig);
        ObjUtil.checkIsNull(runtimeSetting);

        return parseFilter(wherecContext, hBaseTableConfig, null, runtimeSetting);
    }

    private static Filter parseFilter(HBaseSQLParser.WherecContext wherecContext,
                                      HBaseTableConfig hBaseTableConfig,
                                      Map<String, Object> para,
                                      TableQuerySetting runtimeSetting) {
        ObjUtil.checkIsNull(hBaseTableConfig);
        ObjUtil.checkIsNull(runtimeSetting);

        if (wherecContext == null) {
            return null;
        }
        if (wherecContext.conditionc() == null) {
            return null;
        }
        FilterVisitor visitor = new FilterVisitor(hBaseTableConfig, para, runtimeSetting);
        return wherecContext.conditionc().accept(visitor);

    }
}

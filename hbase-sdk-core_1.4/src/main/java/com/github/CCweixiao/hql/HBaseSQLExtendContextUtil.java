package com.github.CCweixiao.hql;

import com.github.CCweixiao.hql.filter.FilterVisitor;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser;
import com.github.CCwexiao.dsl.config.HBaseSQLRuntimeSetting;
import com.github.CCwexiao.dsl.config.HBaseTableConfig;
import com.github.CCwexiao.dsl.util.Util;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.Map;

/**
 * @author leojie 2020/11/28 7:10 下午
 */
public class HBaseSQLExtendContextUtil {

    public static Filter parseFilter(HBaseSQLParser.WherecContext wherecContext,
                                     HBaseTableConfig hBaseTableConfig,
                                     HBaseSQLRuntimeSetting runtimeSetting){
        Util.checkNull(hBaseTableConfig);
        Util.checkNull(runtimeSetting);

        return parseFilter(wherecContext, hBaseTableConfig, null, runtimeSetting);
    }

    private static Filter parseFilter(HBaseSQLParser.WherecContext wherecContext,
                                      HBaseTableConfig hBaseTableConfig,
                                      Map<String, Object> para,
                                      HBaseSQLRuntimeSetting runtimeSetting) {
        Util.checkNull(hBaseTableConfig);
        Util.checkNull(runtimeSetting);

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

package com.github.CCweixiao.hbase.sdk.hql;

import com.github.CCweixiao.hbase.sdk.hql.filter.FilterVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import org.apache.hadoop.hbase.filter.Filter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2020/11/28 7:10 下午
 */
public class HBaseSQLExtendContextUtil {


    public static Filter parseFilter(HBaseSQLParser.WherecContext whereContext, HBaseTableSchema tableSchema) {

        if (whereContext == null) {
            return null;
        }
        if (whereContext.conditionc() == null) {
            return null;
        }
        FilterVisitor visitor = new FilterVisitor(tableSchema, new HashMap<>(0));
        return whereContext.conditionc().accept(visitor);
    }

    public static Filter parseFilter(HBaseSQLParser.WherecContext whereContext, HBaseTableSchema tableSchema,
                                      Map<String, Object> queryParams) {

        if (whereContext == null) {
            return null;
        }
        if (whereContext.conditionc() == null) {
            return null;
        }
        FilterVisitor visitor = new FilterVisitor(tableSchema, queryParams);
        return whereContext.conditionc().accept(visitor);

    }
}

package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSqlAnalysisUtil;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;

/**
 * @author leojie 2020/11/28 9:49 下午
 */
public class InsertValueVisitor extends BaseVisitor<Object> {
    public InsertValueVisitor(HBaseColumn column) {
        super(column);
    }

    @Override
    public Object visitInsertValue_Null(HBaseSQLParser.InsertValue_NullContext ctx) {
        return null;
    }

    @Override
    public Object visitInsertValue_NotNull(HBaseSQLParser.InsertValue_NotNullContext ctx) {
        return HBaseSqlAnalysisUtil.extractConstant(column, ctx.constant());
    }

    public Object parseInsertConstantValue(HBaseColumn column, HBaseSQLParser.InsertValueContext insertValueContext) {
        MyAssert.checkNotNull(column);
        return insertValueContext.accept(this);
    }

}

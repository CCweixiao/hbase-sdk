package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLBaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseSQLRuntimeSetting;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLContextUtil;

/**
 * @author leojie 2020/11/28 9:49 下午
 */
public class InsertValueVisitor extends BaseVisitor<Object> {
    private final HBaseColumn hBaseColumnSchema;

    public InsertValueVisitor(HBaseColumn hBaseColumnSchema) {
        this.hBaseColumnSchema = hBaseColumnSchema;
    }

    @Override
    public Object visitInsertValue_Null(HBaseSQLParser.InsertValue_NullContext ctx) {
        return null;
    }

    @Override
    public Object visitInsertValue_NotNull(HBaseSQLParser.InsertValue_NotNullContext ctx) {

        return HBaseSQLContextUtil.parseConstant(hBaseColumnSchema, ctx.constant(), runtimeSetting);
    }

    public Object parseInsertConstantValue(HBaseColumn column, HBaseSQLParser.InsertValueContext insertValueContext) {
        MyAssert.checkNotNull(column);
        return insertValueContext.accept(this);
    }

}

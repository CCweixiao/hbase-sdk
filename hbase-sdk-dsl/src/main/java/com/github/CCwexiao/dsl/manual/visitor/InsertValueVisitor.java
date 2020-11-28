package com.github.CCwexiao.dsl.manual.visitor;

import com.github.CCwexiao.dsl.auto.HBaseSQLBaseVisitor;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser;
import com.github.CCwexiao.dsl.config.HBaseColumnSchema;
import com.github.CCwexiao.dsl.config.HBaseSQLRuntimeSetting;
import com.github.CCwexiao.dsl.manual.HBaseSQLContextUtil;

/**
 * @author leojie 2020/11/28 9:49 下午
 */
public class InsertValueVisitor extends HBaseSQLBaseVisitor<Object> {
    private HBaseColumnSchema hBaseColumnSchema;
    private HBaseSQLRuntimeSetting runtimeSetting;

    public InsertValueVisitor(HBaseColumnSchema hBaseColumnSchema,
                              HBaseSQLRuntimeSetting runtimeSetting) {
        this.hBaseColumnSchema = hBaseColumnSchema;
        this.runtimeSetting = runtimeSetting;
    }

    @Override
    public Object visitInsertValue_Null(HBaseSQLParser.InsertValue_NullContext ctx) {
        return null;
    }

    @Override
    public Object visitInsertValue_NotNull(HBaseSQLParser.InsertValue_NotNullContext ctx) {
        return HBaseSQLContextUtil.parseConstant(hBaseColumnSchema, ctx.constant(), runtimeSetting);
    }

}

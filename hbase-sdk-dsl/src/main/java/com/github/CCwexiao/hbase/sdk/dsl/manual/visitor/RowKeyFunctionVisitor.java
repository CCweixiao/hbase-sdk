package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLBaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunc;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseSQLRuntimeSetting;

/**
 * @author leojie 2020/11/28 11:02 上午
 */
public class RowKeyFunctionVisitor extends HBaseSQLBaseVisitor<RowKeyFunc> {
    private final HBaseSQLRuntimeSetting runtimeSetting;

    public RowKeyFunctionVisitor(HBaseSQLRuntimeSetting runtimeSetting) {
        this.runtimeSetting = runtimeSetting;
    }

    @Override
    public RowKeyFunc visitRowkey_FuncConstant(HBaseSQLParser.Rowkey_FuncConstantContext ctx) {
        String funcName = ctx.funcname().getText();
        return runtimeSetting.findRowKeyTextFunc(funcName);
    }

    @Override
    public RowKeyFunc visitRowkey_Wrapper(HBaseSQLParser.Rowkey_WrapperContext ctx) {
        return ctx.variable().accept(this);
    }
}

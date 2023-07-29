package com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor;

import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunc;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunction;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

/**
 * @author leojie 2020/11/28 11:02 上午
 */
public class RowKeyFunctionVisitor extends BaseVisitor<RowKeyFunc<?>> {

    public RowKeyFunctionVisitor(HBaseTableSchema tableSchema) {
        super(tableSchema);
    }

    @Override
    public RowKeyFunc<?> visitRowkey_FuncConstant(HBaseSQLParser.Rowkey_FuncConstantContext ctx) {
        String funcName = ctx.funcname().getText();
        return RowKeyFunction.findRowKeyFunc(funcName).getRowKeyFunc();
    }

    @Override
    public RowKeyFunc<?> visitRowkey_Wrapper(HBaseSQLParser.Rowkey_WrapperContext ctx) {
        return ctx.rowKeyExp().accept(this);
    }
}

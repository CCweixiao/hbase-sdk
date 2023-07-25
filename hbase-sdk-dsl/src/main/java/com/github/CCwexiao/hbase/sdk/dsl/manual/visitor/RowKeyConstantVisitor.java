package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKeyFactory;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunc;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

/**
 * @author leojie 2020/12/6 8:27 下午
 */
public class RowKeyConstantVisitor extends BaseVisitor<RowKey<?>> {
    public RowKeyConstantVisitor(HBaseTableSchema tableSchema) {
        super(tableSchema);
    }

    @Override
    public RowKey<?> visitRowkey_FuncConstant(HBaseSQLParser.Rowkey_FuncConstantContext ctx) {
        MyAssert.checkNotNull(ctx);
        String text = ctx.constant().STRING().getText();
        String funcName = ctx.funcname().getText();
        return RowKeyFactory.getRowKeyByFuncName(text, funcName);
    }

    @Override
    public RowKey<?> visitRowkey_Constant(HBaseSQLParser.Rowkey_ConstantContext ctx) {
        MyAssert.checkNotNull(ctx);
        String text = ctx.constant().STRING().getText();
        return RowKeyFactory.getRowKeyByTableSchema(text, tableSchema);
    }

    @Override
    public RowKey<?> visitRowkey_Wrapper(HBaseSQLParser.Rowkey_WrapperContext ctx) {
        return ctx.rowKeyExp().accept(this);
    }

    public RowKey<?> parseRowKey(HBaseSQLParser.RowKeyExpContext rowKeyExpContext) {
        MyAssert.checkNotNull(rowKeyExpContext);
        RowKeyFunctionVisitor visitor = new RowKeyFunctionVisitor();
        RowKey<?> rowKey = rowKeyExpContext.accept(this);
        MyAssert.checkNotNull(rowKey);

        final RowKeyFunc<?> rowKeyFunc = rowKeyExpContext.accept(visitor);
        if (rowKeyFunc != null) {
            Object object = rowKeyFunc.evalFuncReturnRowValue(tableSchema.findRow(), rowKey.getOriValue());
            rowKey.setValueBytes(tableSchema.findRow().convertValToBytes(object));
        }
        return rowKey;
    }

}

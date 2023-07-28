package com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKeyFactory;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunc;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

import java.util.List;

/**
 * @author leojie 2020/12/6 8:27 下午
 */
public class RowKeyConstantVisitor extends BaseVisitor<RowKey<?>> {
    public RowKeyConstantVisitor(HBaseTableSchema tableSchema) {
        super(tableSchema);
    }

    @Override
    public RowKey<?> visitRowkey_FuncConstant(HBaseSQLParser.Rowkey_FuncConstantContext ctx) {
        HBaseSQLParser.FuncParamsListContext funcParamsListContext = ctx.funcParamsList();
        List<HBaseSQLParser.FuncColContext> funcColContexts = funcParamsListContext.funcCol();
        String funcName = ctx.funcname().getText();
        if (funcColContexts.isEmpty()) {
            return RowKeyFactory.getRowKeyByFuncName(funcName);
        }
        String[] prams = new String[funcColContexts.size()];
        for (int i = 0; i < funcColContexts.size(); i++) {
            String val = this.extractValueFromValueContext(funcColContexts.get(i).value());
            prams[i] = val;
        }
        return RowKeyFactory.getRowKeyByFuncName(funcName, prams);
    }

    @Override
    public RowKey<?> visitRowkey_Constant(HBaseSQLParser.Rowkey_ConstantContext ctx) {
        String rowKeyOriText = this.extractValueFromValueContext(ctx.value());
        if (StringUtil.isBlank(rowKeyOriText)) {
            throw new HBaseSqlAnalysisException("The value of rowKey could not be resolved.");
        }
        return RowKeyFactory.getRowKeyByTableSchema(rowKeyOriText, this.getTableSchema());
    }

    @Override
    public RowKey<?> visitRowkey_Wrapper(HBaseSQLParser.Rowkey_WrapperContext ctx) {
        return ctx.rowKeyExp().accept(this);
    }

    public RowKey<?> parseRowKey(HBaseSQLParser.RowKeyExpContext rowKeyExpContext) {
        RowKeyFunctionVisitor visitor = new RowKeyFunctionVisitor(this.getTableSchema());
        RowKey<?> rowKey = rowKeyExpContext.accept(this);

        final RowKeyFunc<?> rowKeyFunc = rowKeyExpContext.accept(visitor);
        if (rowKeyFunc != null) {
            Object object = rowKeyFunc.evalFuncReturnRowValue(tableSchema.findRow(), rowKey.getOriValue());
            rowKey.setValueBytes(tableSchema.findRow().convertValToBytes(object));
        }
        return rowKey;
    }

}

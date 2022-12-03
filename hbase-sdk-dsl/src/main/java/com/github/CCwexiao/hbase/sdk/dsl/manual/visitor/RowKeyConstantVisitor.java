package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKeyUtil;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunc;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseSQLRuntimeSetting;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

/**
 * @author leojie 2020/12/6 8:27 下午
 */
public class RowKeyConstantVisitor extends BaseVisitor<RowKey> {
    public RowKeyConstantVisitor(HBaseTableSchema tableSchema) {
        super(tableSchema);
    }

    public RowKeyConstantVisitor(HBaseTableSchema tableSchema, HBaseSQLRuntimeSetting sqlRuntimeSetting) {
        super(tableSchema, sqlRuntimeSetting);
    }

    @Override
    public RowKey visitRowkey_FuncConstant(HBaseSQLParser.Rowkey_FuncConstantContext ctx) {
        String text = ctx.constant().STRING().getText();
        String funcName = ctx.funcname().getText();
        RowKeyFunc rowKeyTextFunc = runtimeSetting.findRowKeyTextFunc(funcName);
        return rowKeyTextFunc.func(text);
    }

    @Override
    public RowKey visitRowkey_hbasestart(HBaseSQLParser.Rowkey_hbasestartContext ctx) {
        return RowKeyUtil.START_ROW;
    }

    @Override
    public RowKey visitRowkey_hbaseend(HBaseSQLParser.Rowkey_hbaseendContext ctx) {
        return RowKeyUtil.END_ROW;
    }

    @Override
    public RowKey visitRowkey_Wrapper(HBaseSQLParser.Rowkey_WrapperContext ctx) {
        return ctx.variable().accept(this);
    }

    public RowKey parseRowKey(HBaseSQLParser.RowKeyExpContext rowKeyExpContext) {
        MyAssert.checkNotNull(rowKeyExpContext);
        RowKey rowKey = rowKeyExpContext.accept(this);
        MyAssert.checkNotNull(rowKey);
        return rowKey;
    }


}

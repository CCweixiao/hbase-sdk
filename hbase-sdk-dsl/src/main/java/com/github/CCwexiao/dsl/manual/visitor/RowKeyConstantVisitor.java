package com.github.CCwexiao.dsl.manual.visitor;

import com.github.CCwexiao.dsl.auto.HBaseSQLBaseVisitor;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkey.RowKeyUtil;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;
import com.github.CCwexiao.dsl.config.HBaseSQLRuntimeSetting;

/**
 * @author leojie 2020/12/6 8:27 下午
 */
public class RowKeyConstantVisitor extends HBaseSQLBaseVisitor<RowKey> {

    private final HBaseSQLRuntimeSetting runtimeSetting;

    public RowKeyConstantVisitor(HBaseSQLRuntimeSetting runtimeSetting) {
        this.runtimeSetting = runtimeSetting;
    }

    @Override
    public RowKey visitRowkey_FuncConstant(HBaseSQLParser.Rowkey_FuncConstantContext ctx) {
        String text = ctx.constant().TEXT().getText();
        String funcName = ctx.funcname().getText();
        RowKeyTextFunc rowKeyTextFunc = runtimeSetting.findRowKeyTextFunc(funcName);
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
        return ctx.rowKeyExp().accept(this);
    }
}

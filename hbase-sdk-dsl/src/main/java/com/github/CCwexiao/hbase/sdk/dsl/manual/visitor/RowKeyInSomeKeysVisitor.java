package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLBaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunc;
import com.github.CCwexiao.hbase.sdk.dsl.config.HBaseSQLRuntimeSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2020/12/10 10:23 下午
 */
public class RowKeyInSomeKeysVisitor extends HBaseSQLBaseVisitor<List<RowKey>> {
    private final HBaseSQLRuntimeSetting runtimeSetting;

    public RowKeyInSomeKeysVisitor(HBaseSQLRuntimeSetting runtimeSetting) {
        this.runtimeSetting = runtimeSetting;
    }

    @Override
    public List<RowKey> visitRowkey_inRangeKey(HBaseSQLParser.Rowkey_inRangeKeyContext inRangeKeyContext) {
        final List<HBaseSQLParser.ConstantContext> constantContextList = inRangeKeyContext.constant();
        List<RowKey> rowKeys = new ArrayList<>();

        if (constantContextList == null || constantContextList.isEmpty()) {
            return new ArrayList<>();
        }
        final String rowKeyFunctionName = inRangeKeyContext.funcname().getText();
        final RowKeyFunc rowKeyTextFunc = runtimeSetting.findRowKeyTextFunc(rowKeyFunctionName);

        for (HBaseSQLParser.ConstantContext constantContext : constantContextList) {
            rowKeys.add(rowKeyTextFunc.func(constantContext.STRING().getText()));
        }
        return rowKeys;
    }
}

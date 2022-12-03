package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2020/12/6 8:27 下午
 */
public class RowKeyListConstantVisitor extends BaseVisitor<List<RowKey<?>>> {

    public RowKeyListConstantVisitor(HBaseTableSchema tableSchema) {
        super(tableSchema);
    }

    @Override
    public List<RowKey<?>> visitRowkeyrange_insomekeys(HBaseSQLParser.Rowkeyrange_insomekeysContext ctx) {
        MyAssert.checkNotNull(ctx);
        List<RowKey<?>> rowKeyList = new ArrayList<>(4);
        RowKeyConstantVisitor rowKeyConstantVisitor = new RowKeyConstantVisitor(tableSchema);
        for (HBaseSQLParser.RowKeyExpContext rowKeyExpContext : ctx.rowKeyExp()) {
            rowKeyList.add(rowKeyConstantVisitor.parseRowKey(rowKeyExpContext));
        }
        return rowKeyList;
    }
}

package com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.data.RowKeyRange;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

import java.util.List;

/**
 * @author leojie 2020/11/28 11:00 上午
 */
public class RowKeyRangeVisitor extends BaseVisitor<RowKeyRange> {
    public RowKeyRangeVisitor(HBaseTableSchema tableSchema) {
        super(tableSchema);
    }

    @Override
    public RowKeyRange visitRowkeyrange_start(HBaseSQLParser.Rowkeyrange_startContext startContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();
        RowKeyConstantVisitor rowKeyConstantVisitor = new RowKeyConstantVisitor(tableSchema);
        RowKey<?> startRowKey = rowKeyConstantVisitor.parseRowKey(startContext.rowKeyExp());
        rowKeyRange.setStart(startRowKey);
        rowKeyRange.setEnd(endRow());
        return rowKeyRange;
    }

    @Override
    public RowKeyRange visitRowkeyrange_end(HBaseSQLParser.Rowkeyrange_endContext endContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();
        RowKeyConstantVisitor rowKeyConstantVisitor = new RowKeyConstantVisitor(tableSchema);
        RowKey<?> endRowKey = rowKeyConstantVisitor.parseRowKey(endContext.rowKeyExp());
        rowKeyRange.setStart(startRow());
        rowKeyRange.setEnd(endRowKey);
        return rowKeyRange;
    }


    @Override
    public RowKeyRange visitRowkeyrange_startAndEnd(HBaseSQLParser.Rowkeyrange_startAndEndContext startAndEndContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();

        RowKeyConstantVisitor rowKeyConstantVisitor = new RowKeyConstantVisitor(tableSchema);
        RowKey<?> startRowKey = rowKeyConstantVisitor.parseRowKey(startAndEndContext.rowKeyExp(0));
        rowKeyRange.setStart(startRowKey);

        RowKey<?> endRowKey = rowKeyConstantVisitor.parseRowKey(startAndEndContext.rowKeyExp(1));
        rowKeyRange.setEnd(endRowKey);

        return rowKeyRange;
    }

    @Override
    public RowKeyRange visitRowkeyrange_onerowkey(HBaseSQLParser.Rowkeyrange_onerowkeyContext onerowkeyContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();
        RowKeyConstantVisitor rowKeyConstantVisitor = new RowKeyConstantVisitor(tableSchema);
        RowKey<?> rowKey = rowKeyConstantVisitor.parseRowKey(onerowkeyContext.rowKeyExp());
        rowKeyRange.setEqRow(rowKey);
        return rowKeyRange;
    }

    @Override
    public RowKeyRange visitRowkeyrange_insomekeys(HBaseSQLParser.Rowkeyrange_insomekeysContext insomekeysContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();
        RowKeyListConstantVisitor rowKeyListConstantVisitor = new RowKeyListConstantVisitor(tableSchema);
        List<RowKey<?>> rowKeyList = insomekeysContext.accept(rowKeyListConstantVisitor);
        rowKeyRange.setInSomeKeys(rowKeyList);
        rowKeyRange.setStart(null);
        rowKeyRange.setEnd(null);
        return rowKeyRange;
    }

    public RowKeyRange extractRowKeyRange(HBaseSQLParser.RowKeyRangeExpContext rowKeyRangeContext) {
        RowKeyRange rowKeyRange = rowKeyRangeContext.accept(this);
        MyAssert.checkNotNull(rowKeyRange);
        return rowKeyRange;
    }
}

package com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor;

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
        if (startContext.gtOper().GREATEREQUAL() != null) {
            rowKeyRange.setIncludeStart(true);
        }
        rowKeyRange.setMatchScanByStart(true);
        return rowKeyRange;
    }

    @Override
    public RowKeyRange visitRowkeyrange_end(HBaseSQLParser.Rowkeyrange_endContext endContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();
        RowKeyConstantVisitor rowKeyConstantVisitor = new RowKeyConstantVisitor(tableSchema);
        RowKey<?> endRowKey = rowKeyConstantVisitor.parseRowKey(endContext.rowKeyExp());
        rowKeyRange.setStart(startRow());

        if (endContext.leOper().LESSEQUAL() != null) {
            rowKeyRange.setIncludeStop(true);
        }
        rowKeyRange.setStop(endRowKey);
        return rowKeyRange;
    }


    @Override
    public RowKeyRange visitRowkeyrange_startAndEnd(HBaseSQLParser.Rowkeyrange_startAndEndContext startAndEndContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();
        RowKeyConstantVisitor rowKeyConstantVisitor = new RowKeyConstantVisitor(tableSchema);
        RowKey<?> startRowKey = rowKeyConstantVisitor.parseRowKey(startAndEndContext.rowKeyExp(0));
        rowKeyRange.setStart(startRowKey);
        if (startAndEndContext.gtOper().GREATEREQUAL() != null) {
            rowKeyRange.setIncludeStart(true);
        }
        RowKey<?> endRowKey = rowKeyConstantVisitor.parseRowKey(startAndEndContext.rowKeyExp(1));
        rowKeyRange.setStop(endRowKey);
        if (startAndEndContext.leOper().LESSEQUAL() != null) {
            rowKeyRange.setIncludeStop(true);
        }
        rowKeyRange.setMatchScanByStartAndEnd(true);
        return rowKeyRange;
    }

    @Override
    public RowKeyRange visitRowkeyrange_onerowkey(HBaseSQLParser.Rowkeyrange_onerowkeyContext onerowkeyContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();
        RowKeyConstantVisitor rowKeyConstantVisitor = new RowKeyConstantVisitor(tableSchema);
        RowKey<?> rowKey = rowKeyConstantVisitor.parseRowKey(onerowkeyContext.rowKeyExp());
        rowKeyRange.setEqRow(rowKey);
        rowKeyRange.setMatchGet(true);
        return rowKeyRange;
    }

    @Override
    public RowKeyRange visitRowkeyrange_insomekeys(HBaseSQLParser.Rowkeyrange_insomekeysContext insomekeysContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();
        RowKeyListConstantVisitor rowKeyListConstantVisitor = new RowKeyListConstantVisitor(tableSchema);
        List<RowKey<?>> rowKeyList = insomekeysContext.accept(rowKeyListConstantVisitor);
        rowKeyRange.setInSomeKeys(rowKeyList);
        rowKeyRange.setMatchGetRows(true);
        return rowKeyRange;
    }

    @Override
    public RowKeyRange visitRowkeyrange_prefix(HBaseSQLParser.Rowkeyrange_prefixContext rowkeyrangePrefixContext) {
        RowKeyRange rowKeyRange = new RowKeyRange();
        RowKeyConstantVisitor rowKeyConstantVisitor = new RowKeyConstantVisitor(tableSchema);
        RowKey<?> rowKey = rowKeyConstantVisitor.parseRowKey(rowkeyrangePrefixContext.rowKeyExp());
        rowKeyRange.setRowPrefix(rowKey);
        rowKeyRange.setMatchScanByRowPrefix(true);
        return rowKeyRange;
    }

    public RowKeyRange extractRowKeyRange(HBaseSQLParser.RowKeyRangeExpContext rowKeyRangeContext) {
        return rowKeyRangeContext.accept(this);
    }
}

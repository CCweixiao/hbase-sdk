package com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor;

import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2020/11/27 11:28 下午
 */
public class SelectColListVisitor extends BaseVisitor<List<HBaseColumn>> {

    public SelectColListVisitor(HBaseTableSchema tableSchema) {
        super(tableSchema);
    }

    @Override
    public List<HBaseColumn> visitColList_ColList(HBaseSQLParser.ColList_ColListContext ctx) {
        return this.extractColumns(ctx.columnList());
    }

    @Override
    public List<HBaseColumn> visitColList_Star(HBaseSQLParser.ColList_StarContext ctx) {
        return new ArrayList<>(this.tableSchema.findAllColumns());
    }

    public List<HBaseColumn> extractColumns(HBaseSQLParser.SelectColListContext selectColListContext) {
        return selectColListContext.accept(this);
    }
}

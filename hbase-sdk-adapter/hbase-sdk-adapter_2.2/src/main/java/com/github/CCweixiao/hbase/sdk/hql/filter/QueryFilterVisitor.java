package com.github.CCweixiao.hbase.sdk.hql.filter;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.dsl.antlr.visitor.BaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2023/7/26 20:44
 */
public class QueryFilterVisitor extends BaseVisitor<Filter> {
    private final Map<String, Object> queryParams;

    public QueryFilterVisitor(HBaseTableSchema tableSchema, Map<String, Object> queryParams) {
        super(tableSchema);
        this.queryParams = queryParams;
    }

    public Map<String, Object> getQueryParams() {
        if (this.queryParams == null || this.queryParams.isEmpty()) {
            throw new HBaseSqlAnalysisException("The parameter list cannot be empty.");
        }
        return queryParams;
    }

    @Override
    public Filter visitOrcondition(HBaseSQLParser.OrconditionContext ctx) {
        final List<HBaseSQLParser.ConditioncContext> conditioncContextList = ctx.conditionc();

        List<Filter> filters = new ArrayList<>();
        for (HBaseSQLParser.ConditioncContext conditioncContext : conditioncContextList) {
            filters.add(conditioncContext.accept(this));
        }
        if (filters.isEmpty()) {
            return null;
        }
        return new FilterList(FilterList.Operator.MUST_PASS_ONE, filters);
    }

    @Override
    public Filter visitAndcondition(HBaseSQLParser.AndconditionContext ctx) {
        final List<HBaseSQLParser.ConditioncContext> conditioncContextList = ctx.conditionc();

        List<Filter> filters = new ArrayList<>();
        for (HBaseSQLParser.ConditioncContext conditioncContext : conditioncContextList) {
            filters.add(conditioncContext.accept(this));
        }
        if (filters.isEmpty()) {
            return null;
        }
        return new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);
    }

    @Override
    public Filter visitConditionwrapper(HBaseSQLParser.ConditionwrapperContext ctx) {
        return ctx.conditionc().accept(this);
    }

    @Override
    public Filter visitEqualvar(HBaseSQLParser.EqualvarContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        Object paramVal = this.extractParamVal(ctx.var(), this.getQueryParams());
        return constructFilter(column, CompareOperator.EQUAL, paramVal);
    }

    @Override
    public Filter visitEqualconstant(HBaseSQLParser.EqualconstantContext ctx) {
        final HBaseColumn columnSchema = this.extractColumn(ctx.column());
        final Object constantVal = this.extractConstantVal(columnSchema, ctx.constant());
        return constructFilter(columnSchema, CompareOperator.EQUAL, constantVal);
    }

    @Override
    public Filter visitIsnullc(HBaseSQLParser.IsnullcContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        return constructFilter(column, CompareOperator.EQUAL, new byte[0]);
    }

    @Override
    public Filter visitIsnotnullc(HBaseSQLParser.IsnotnullcContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        return constructFilter(column, CompareOperator.NOT_EQUAL, new byte[0]);
    }

    @Override
    public Filter visitNotequalconstant(HBaseSQLParser.NotequalconstantContext ctx) {
        final HBaseColumn columnSchema = this.extractColumn(ctx.column());
        final Object constantVal = this.extractConstantVal(columnSchema, ctx.constant());
        return constructFilter(columnSchema, CompareOperator.NOT_EQUAL, constantVal);
    }

    @Override
    public Filter visitNotequalvar(HBaseSQLParser.NotequalvarContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        Object paramVal = this.extractParamVal(ctx.var(), this.getQueryParams());
        return constructFilter(column, CompareOperator.NOT_EQUAL, paramVal);
    }

    @Override
    public Filter visitLessvar(HBaseSQLParser.LessvarContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        Object paramVal = this.extractParamVal(ctx.var(), this.getQueryParams());
        return constructFilter(column, CompareOperator.LESS, paramVal);
    }

    @Override
    public Filter visitLessconstant(HBaseSQLParser.LessconstantContext ctx) {
        final HBaseColumn columnSchema = this.extractColumn(ctx.column());
        final Object constantVal = this.extractConstantVal(columnSchema, ctx.constant());
        return constructFilter(columnSchema, CompareOperator.LESS, constantVal);
    }

    @Override
    public Filter visitLessequalconstant(HBaseSQLParser.LessequalconstantContext ctx) {
        final HBaseColumn columnSchema = this.extractColumn(ctx.column());
        final Object constantVal = this.extractConstantVal(columnSchema, ctx.constant());
        return constructFilter(columnSchema, CompareOperator.LESS_OR_EQUAL, constantVal);
    }

    @Override
    public Filter visitLessequalvar(HBaseSQLParser.LessequalvarContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        Object paramVal = this.extractParamVal(ctx.var(), this.getQueryParams());
        return constructFilter(column, CompareOperator.LESS_OR_EQUAL, paramVal);
    }

    @Override
    public Filter visitGreaterconstant(HBaseSQLParser.GreaterconstantContext ctx) {
        final HBaseColumn columnSchema = this.extractColumn(ctx.column());
        final Object constantVal = this.extractConstantVal(columnSchema, ctx.constant());
        return constructFilter(columnSchema, CompareOperator.GREATER, constantVal);
    }

    @Override
    public Filter visitGreatervar(HBaseSQLParser.GreatervarContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        Object paramVal = this.extractParamVal(ctx.var(), this.getQueryParams());
        return constructFilter(column, CompareOperator.GREATER, paramVal);
    }


    @Override
    public Filter visitGreaterequalvar(HBaseSQLParser.GreaterequalvarContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        Object paramVal = this.extractParamVal(ctx.var(), this.getQueryParams());
        return constructFilter(column, CompareOperator.GREATER_OR_EQUAL, paramVal);
    }

    @Override
    public Filter visitGreaterequalconstant(HBaseSQLParser.GreaterequalconstantContext ctx) {
        final HBaseColumn columnSchema = this.extractColumn(ctx.column());
        final Object constantVal = this.extractConstantVal(columnSchema, ctx.constant());
        return constructFilter(columnSchema, CompareOperator.GREATER_OR_EQUAL, constantVal);
    }

    @Override
    public Filter visitNotinconstantlist(HBaseSQLParser.NotinconstantlistContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        HBaseSQLParser.ConstantListContext constantListContext = ctx.constantList();
        List<HBaseSQLParser.ConstantContext> constantContextList = constantListContext.constant();
        List<Object> constantValList = this.extractConstantValList(column, constantContextList);
        return constructFilterForContain(column, CompareOperator.NOT_EQUAL,
                constantValList, FilterList.Operator.MUST_PASS_ALL);
    }

    @Override
    public Filter visitNotinvarlist(HBaseSQLParser.NotinvarlistContext ctx) {
        HBaseColumn hbaseColumn = this.extractColumn(ctx.column());
        HBaseSQLParser.VarListContext varListContext = ctx.varList();
        List<Object> paramValList = this.extractParamValList(varListContext.var(), this.getQueryParams());

        return constructFilterForContain(hbaseColumn, CompareOperator.NOT_EQUAL,
                paramValList, FilterList.Operator.MUST_PASS_ALL);
    }

    @Override
    public Filter visitInvarlist(HBaseSQLParser.InvarlistContext ctx) {
        HBaseColumn hbaseColumn = this.extractColumn(ctx.column());
        List<Object> paramValList = this.extractParamValList(ctx.varList().var(), this.getQueryParams());
        return constructFilterForContain(hbaseColumn, CompareOperator.EQUAL,
                paramValList, FilterList.Operator.MUST_PASS_ONE);
    }

    @Override
    public Filter visitInconstantlist(HBaseSQLParser.InconstantlistContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        List<Object> constantValList = this.extractConstantValList(column, ctx.constantList().constant());
        return constructFilterForContain(column, CompareOperator.EQUAL, constantValList, FilterList.Operator.MUST_PASS_ONE);
    }

    @Override
    public Filter visitNotbetweenconstant(HBaseSQLParser.NotbetweenconstantContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        List<Object> constantValList = this.extractConstantValList(column, ctx.constant());
        Filter startFilter = constructFilter(column, CompareOperator.LESS, constantValList.get(0));
        Filter endFilter = constructFilter(column, CompareOperator.GREATER, constantValList.get(1));
        return new FilterList(FilterList.Operator.MUST_PASS_ONE, Arrays.asList(startFilter, endFilter));
    }

    @Override
    public Filter visitNotbetweenvar(HBaseSQLParser.NotbetweenvarContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        List<Object> parasValList = this.extractParamValList(ctx.var(), this.getQueryParams());
        Filter startFilter = constructFilter(column, CompareOperator.LESS, parasValList.get(0));
        Filter endFilter = constructFilter(column, CompareOperator.GREATER, parasValList.get(1));
        return new FilterList(FilterList.Operator.MUST_PASS_ONE, Arrays.asList(startFilter, endFilter));
    }

    @Override
    public Filter visitBetweenvar(HBaseSQLParser.BetweenvarContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        List<Object> parasValList = this.extractParamValList(ctx.var(), this.getQueryParams());
        Filter startFilter = constructFilter(column, CompareOperator.GREATER_OR_EQUAL, parasValList.get(0));
        Filter endFilter = constructFilter(column, CompareOperator.LESS_OR_EQUAL, parasValList.get(1));
        return new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(startFilter, endFilter));
    }

    @Override
    public Filter visitBetweenconstant(HBaseSQLParser.BetweenconstantContext ctx) {
        HBaseColumn column = this.extractColumn(ctx.column());
        List<Object> constantValList = this.extractConstantValList(column, ctx.constant());
        Filter startFilter = constructFilter(column, CompareOperator.LESS, constantValList.get(0));
        Filter endFilter = constructFilter(column, CompareOperator.GREATER, constantValList.get(1));
        return new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(startFilter, endFilter));
    }

    private Filter constructFilter(HBaseColumn column, CompareOperator compareOp, Object val) {
        byte[] value = column.getColumnType().getTypeHandler().toBytes(column.getColumnType().getTypeClass(), val);
        return constructFilter(column, compareOp, value);
    }

    private Filter constructFilterForContain(HBaseColumn hbaseColumnSchema, CompareOperator compareOp,
                                             List<Object> valList, FilterList.Operator operator) {
        if (valList == null || valList.isEmpty()) {
            return null;
        }
        List<Filter> filters = new ArrayList<>(valList.size());
        for (Object val : valList) {
            Filter filter = constructFilter(hbaseColumnSchema, compareOp, val);
            if (filter != null) {
                filters.add(filter);
            }
        }
        return new FilterList(operator, filters);
    }

    private Filter constructFilter(HBaseColumn column,
                                   CompareOperator compareOp,
                                   byte[] value) {
        if (value == null || value.length == 0) {
            return null;
        }
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(column.getFamilyNameBytes(),
                column.getColumnNameBytes(), compareOp, value);
        singleColumnValueFilter.setFilterIfMissing(true);
        return singleColumnValueFilter;
    }
}

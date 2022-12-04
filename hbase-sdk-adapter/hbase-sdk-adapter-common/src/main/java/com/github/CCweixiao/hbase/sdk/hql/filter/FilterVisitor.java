package com.github.CCweixiao.hbase.sdk.hql.filter;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser.*;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSqlAnalysisUtil;
import com.github.CCwexiao.hbase.sdk.dsl.manual.visitor.BaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/11/28 2:34 下午
 */
public class FilterVisitor extends BaseVisitor<Filter> {

    private final Map<String, Object> queryParams;

    public FilterVisitor(HBaseTableSchema tableSchema, Map<String, Object> queryParams) {
        super(tableSchema);
        this.queryParams = queryParams;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }

    @Override
    public Filter visitOrcondition(HBaseSQLParser.OrconditionContext ctx) {
        final List<ConditioncContext> conditioncContextList = ctx.conditionc();

        List<Filter> filters = new ArrayList<>();
        for (ConditioncContext conditioncContext : conditioncContextList) {
            filters.add(conditioncContext.accept(this));
        }

        return new FilterList(FilterList.Operator.MUST_PASS_ONE, filters);
    }

    @Override
    public Filter visitAndcondition(AndconditionContext ctx) {
        final List<ConditioncContext> conditioncContextList = ctx.conditionc();

        List<Filter> filters = new ArrayList<>();
        for (ConditioncContext conditioncContext : conditioncContextList) {
            filters.add(conditioncContext.accept(this));
        }

        return new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);
    }

    @Override
    public Filter visitConditionwrapper(ConditionwrapperContext ctx) {
        return ctx.conditionc().accept(this);
    }

    @Override
    public Filter visitEqualvar(EqualvarContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);

        return constructFilter(columnSchema, CompareFilter.CompareOp.EQUAL, object);
    }

    @Override
    public Filter visitEqualconstant(EqualconstantContext ctx) {
        ColContext colContext = ctx.col();
        final ConstantContext constantContext = ctx.constant();
        final HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        final Object obj = HBaseSqlAnalysisUtil.extractConstant(columnSchema, constantContext);
        return constructFilter(columnSchema, CompareFilter.CompareOp.EQUAL, obj);
    }

    @Override
    public Filter visitIsnullc(IsnullcContext ctx) {
        ColContext colContext = ctx.col();
        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        return constructFilter(columnSchema, CompareFilter.CompareOp.EQUAL,
                new byte[0], true);
    }

    @Override
    public Filter visitIsnotnullc(IsnotnullcContext ctx) {
        ColContext colContext = ctx.col();
        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        return constructFilter(columnSchema, CompareFilter.CompareOp.NOT_EQUAL, new byte[0], true);
    }

    @Override
    public Filter visitNotequalconstant(NotequalconstantContext ctx) {
        ColContext colContext = ctx.col();
        ConstantContext constantContext = ctx.constant();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractConstant(columnSchema, constantContext);

        return constructFilter(columnSchema, CompareFilter.CompareOp.NOT_EQUAL, object);
    }

    @Override
    public Filter visitNotequalvar(NotequalvarContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();
        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);
        return constructFilter(columnSchema, CompareFilter.CompareOp.NOT_EQUAL, object);
    }

    @Override
    public Filter visitLessvar(LessvarContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();
        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);
        return constructFilter(columnSchema, CompareFilter.CompareOp.LESS, object);
    }

    @Override
    public Filter visitLessconstant(LessconstantContext ctx) {
        ColContext colContext = ctx.col();
        ConstantContext constantContext = ctx.constant();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractConstant(columnSchema, constantContext);

        return constructFilter(columnSchema, CompareFilter.CompareOp.LESS, object);

    }

    @Override
    public Filter visitLessequalconstant(LessequalconstantContext ctx) {
        ColContext colContext = ctx.col();
        ConstantContext constantContext = ctx.constant();
        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractConstant(columnSchema, constantContext);

        return constructFilter(columnSchema, CompareFilter.CompareOp.LESS_OR_EQUAL, object);
    }

    @Override
    public Filter visitLessequalvar(LessequalvarContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);
        return constructFilter(columnSchema, CompareFilter.CompareOp.LESS_OR_EQUAL, object);
    }

    @Override
    public Filter visitGreaterconstant(GreaterconstantContext ctx) {
        ColContext colContext = ctx.col();
        ConstantContext constantContext = ctx.constant();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractConstant(columnSchema, constantContext);

        return constructFilter(columnSchema, CompareFilter.CompareOp.GREATER, object);
    }

    @Override
    public Filter visitGreatervar(GreatervarContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);
        return constructFilter(columnSchema, CompareFilter.CompareOp.GREATER, object);
    }


    @Override
    public Filter visitGreaterequalvar(GreaterequalvarContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);
        return constructFilter(columnSchema, CompareFilter.CompareOp.GREATER_OR_EQUAL, object);
    }

    @Override
    public Filter visitGreaterequalconstant(GreaterequalconstantContext ctx) {
        ColContext colContext = ctx.col();
        ConstantContext constantContext = ctx.constant();
        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractConstant(columnSchema, constantContext);
        return constructFilter(columnSchema, CompareFilter.CompareOp.GREATER_OR_EQUAL, object);
    }


    @Override
    public Filter visitIsnotmissingc(IsnotmissingcContext ctx) {
        ColContext colContext = ctx.col();
        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        return constructFilter(columnSchema, CompareFilter.CompareOp.GREATER_OR_EQUAL, new byte[0], true);
    }

    @Override
    public Filter visitIsmissingc(IsmissingcContext ctx) {
        ColContext colContext = ctx.col();
        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        return constructFilter(columnSchema, CompareFilter.CompareOp.LESS,
                new byte[0], false);
    }

    private static Filter constructFilter(HBaseColumn columnSchema, CompareFilter.CompareOp compareOp, Object object) {
        MyAssert.checkNotNull(columnSchema);
        byte[] value = columnSchema.getColumnType().getTypeHandler().toBytes(columnSchema.getColumnType().getTypeClass(), object);
        return constructFilter(columnSchema, compareOp, value, true);
    }

    private static Filter constructFilter(HBaseColumn hBaseColumnSchema,
                                          CompareFilter.CompareOp compareOp,
                                          byte[] value,
                                          boolean filterIfMissing) {
        MyAssert.checkNotNull(hBaseColumnSchema);
        MyAssert.checkNotNull(compareOp);
        MyAssert.checkNotNull(value);

        byte[] familyBytes = Bytes.toBytes(hBaseColumnSchema.getFamilyName());
        byte[] qualifierBytes = Bytes.toBytes(hBaseColumnSchema.getColumnName());


        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(
                familyBytes, qualifierBytes, compareOp, value);
        singleColumnValueFilter.setFilterIfMissing(filterIfMissing);
        return singleColumnValueFilter;
    }


    @Override
    public Filter visitNotmatchconstant(NotmatchconstantContext ctx) {
        ColContext colContext = ctx.col();
        ConstantContext constantContext = ctx.constant();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractConstant(columnSchema, constantContext);

        return constructFilterWithRegex(columnSchema, CompareFilter.CompareOp.NOT_EQUAL, object);
    }

    @Override
    public Filter visitNotmatchvar(NotmatchvarContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);

        return constructFilterWithRegex(columnSchema, CompareFilter.CompareOp.NOT_EQUAL,
                object);
    }

    @Override
    public Filter visitMatchvar(MatchvarContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);

        return constructFilterWithRegex(columnSchema, CompareFilter.CompareOp.EQUAL,
                object);
    }

    @Override
    public Filter visitMatchconstant(MatchconstantContext ctx) {
        ColContext colContext = ctx.col();
        ConstantContext constantContext = ctx.constant();

        HBaseColumn columnSchema = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractConstant(columnSchema, constantContext);

        return constructFilterWithRegex(columnSchema, CompareFilter.CompareOp.EQUAL, object);
    }

    private static Filter constructFilterWithRegex(
            HBaseColumn columnSchema, CompareFilter.CompareOp compareOp,
            Object object) {
        MyAssert.checkNotNull(columnSchema);
        MyAssert.checkNotNull(compareOp);
        MyAssert.checkNotNull(object);

        if (compareOp != CompareFilter.CompareOp.EQUAL && compareOp != CompareFilter.CompareOp.NOT_EQUAL) {
            throw new HBaseOperationsException("only EQUAL or NOT_EQUAL can use regex match. compareOp = " + compareOp);
        }
        if (object.getClass() != String.class) {
            throw new HBaseOperationsException("only string can use regex match. object = " + object);
        }
        if (columnSchema.getColumnType().getTypeClass() != String.class) {
            throw new HBaseOperationsException("only string can use regex match. columnSchema = " + columnSchema);
        }

        byte[] familyBytes = Bytes.toBytes(columnSchema.getFamilyName());
        byte[] qualifierBytes = Bytes.toBytes(columnSchema.getColumnName());

        RegexStringComparator regexStringComparator = new RegexStringComparator(
                (String) object);

        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(
                familyBytes, qualifierBytes, compareOp, regexStringComparator);
        singleColumnValueFilter.setFilterIfMissing(true);

        return singleColumnValueFilter;
    }

    @Override
    public Filter visitNotinconstantlist(NotinconstantlistContext ctx) {
        ColContext colContext = ctx.col();
        HBaseColumn hbaseColumn = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);

        ConstantListContext constantListContext = ctx.constantList();
        List<ConstantContext> constantContextList = constantListContext.constant();
        List<Object> list = HBaseSqlAnalysisUtil.extractConstantList(hbaseColumn, constantContextList);

        return constructFilterForContain(hbaseColumn, CompareFilter.CompareOp.NOT_EQUAL, list, FilterList.Operator.MUST_PASS_ALL);
    }

    @Override
    public Filter visitNotinvarlist(NotinvarlistContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();

        HBaseColumn hbaseColumn = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);

        return constructFilterForContain(hbaseColumn,
                CompareFilter.CompareOp.NOT_EQUAL, (List<Object>) object,
                FilterList.Operator.MUST_PASS_ALL);
    }

    @Override
    public Filter visitInvarlist(InvarlistContext ctx) {
        ColContext colContext = ctx.col();
        VarContext varContext = ctx.var();

        HBaseColumn hbaseColumn = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        Object object = HBaseSqlAnalysisUtil.extractParam(varContext, queryParams);

        return constructFilterForContain(hbaseColumn, CompareFilter.CompareOp.EQUAL,
                (List<Object>) object, FilterList.Operator.MUST_PASS_ONE);
    }

    @Override
    public Filter visitInconstantlist(InconstantlistContext ctx) {
        ColContext colContext = ctx.col();
        HBaseColumn hbaseColumn = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        ConstantListContext constantListContext = ctx.constantList();
        List<ConstantContext> constantContextList = constantListContext
                .constant();
        List<Object> list = HBaseSqlAnalysisUtil.extractConstantList(hbaseColumn, constantContextList);

        return constructFilterForContain(hbaseColumn, CompareFilter.CompareOp.EQUAL,
                list, FilterList.Operator.MUST_PASS_ONE);
    }

    private static Filter constructFilterForContain(
            HBaseColumn hbaseColumnSchema, CompareFilter.CompareOp compareOp,
            List<Object> list, FilterList.Operator operator) {
        MyAssert.checkNotNull(hbaseColumnSchema);
        MyAssert.checkNotNull(compareOp);
        MyAssert.checkNotNull(list);
        MyAssert.checkNotNull(operator);

        List<Filter> filters = new ArrayList<>();
        for (Object obj : list) {
            filters.add(constructFilter(hbaseColumnSchema, compareOp, obj));
        }

        return new FilterList(operator, filters);
    }

    @Override
    public Filter visitNotbetweenconstant(NotbetweenconstantContext ctx) {
        ColContext colContext = ctx.col();
        HBaseColumn hbaseColumn = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        List<ConstantContext> constantContextList = ctx.constant();
        List<Object> list = HBaseSqlAnalysisUtil.extractConstantList(hbaseColumn, constantContextList);
        Filter startFilter = constructFilter(hbaseColumn, CompareFilter.CompareOp.LESS, list.get(0));
        Filter endFilter = constructFilter(hbaseColumn, CompareFilter.CompareOp.GREATER, list.get(1));
        return new FilterList(FilterList.Operator.MUST_PASS_ONE, Arrays.asList(startFilter, endFilter));
    }

    @Override
    public Filter visitNotbetweenvar(NotbetweenvarContext ctx) {
        ColContext colContext = ctx.col();
        List<VarContext> varContextList = ctx.var();
        HBaseColumn hbaseColumn = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        List<Object> list = HBaseSqlAnalysisUtil.extractParasList(varContextList, queryParams);
        Filter startFilter = constructFilter(hbaseColumn, CompareFilter.CompareOp.LESS, list.get(0));
        Filter endFilter = constructFilter(hbaseColumn, CompareFilter.CompareOp.GREATER, list.get(1));

        return new FilterList(FilterList.Operator.MUST_PASS_ONE, Arrays.asList(startFilter, endFilter));
    }

    @Override
    public Filter visitBetweenvar(BetweenvarContext ctx) {
        ColContext colContext = ctx.col();
        List<VarContext> varContextList = ctx.var();
        HBaseColumn hbaseColumn = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        List<Object> list = HBaseSqlAnalysisUtil.extractParasList(varContextList, queryParams);
        Filter startFilter = constructFilter(hbaseColumn,
                CompareFilter.CompareOp.GREATER_OR_EQUAL, list.get(0));
        Filter endFilter = constructFilter(hbaseColumn,
                CompareFilter.CompareOp.LESS_OR_EQUAL, list.get(1));

        return new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(startFilter, endFilter));
    }

    @Override
    public Filter visitBetweenconstant(BetweenconstantContext ctx) {
        ColContext colContext = ctx.col();
        HBaseColumn hbaseColumn = HBaseSqlAnalysisUtil.extractColumnSchema(this.getTableSchema(), colContext);
        List<ConstantContext> constantContextList = ctx.constant();
        List<Object> list = HBaseSqlAnalysisUtil.extractConstantList(hbaseColumn, constantContextList);
        Filter startFilter = constructFilter(hbaseColumn, CompareFilter.CompareOp.GREATER_OR_EQUAL, list.get(0));
        Filter endFilter = constructFilter(hbaseColumn, CompareFilter.CompareOp.LESS_OR_EQUAL, list.get(1));
        return new FilterList(FilterList.Operator.MUST_PASS_ALL, Arrays.asList(startFilter, endFilter));
    }

}

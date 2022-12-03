package com.github.CCwexiao.hbase.sdk.dsl.manual.visitor;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlColValueAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLBaseVisitor;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.*;
import com.github.CCwexiao.hbase.sdk.dsl.model.TableQuerySetting;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2022/12/3 10:30
 */
public abstract class BaseVisitor<T> extends HBaseSQLBaseVisitor<T> {
    protected HBaseTableSchema tableSchema;
    protected HBaseColumn column;

    public BaseVisitor() {
        this(null, null);
    }

    public BaseVisitor(HBaseTableSchema tableSchema) {
        this(tableSchema, null);
    }

    public BaseVisitor(HBaseColumn column) {
        this(null, column);
    }

    public BaseVisitor(HBaseTableSchema tableSchema, HBaseColumn column) {
        this.tableSchema = tableSchema;
        this.column = column;
    }

    public HBaseColumn extractColumnSchema(HBaseSQLParser.ColContext colContext) {
        MyAssert.checkNotNull(colContext);

        String col = colContext.STRING().getText();
        String[] colArr = col.split(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR);
        if (colArr.length == 1) {
            return this.tableSchema.findColumn(colArr[0]);
        }
        if (colArr.length == 2) {
            return this.tableSchema.findColumn(colArr[0], colArr[1]);
        }
        throw new HBaseSqlAnalysisException("Parse hbase column schema error. col=" + col);
    }

    public List<HBaseColumn> extractColumnSchemaList(HBaseSQLParser.ColListContext colListContext) {
        MyAssert.checkNotNull(colListContext);

        List<HBaseColumn> columnList = new ArrayList<>();
        for (HBaseSQLParser.ColContext colContext : colListContext.col()) {
            columnList.add(extractColumnSchema(colContext));
        }
        return columnList;
    }

    public Object extractParam(HBaseSQLParser.VarContext varContext, Map<String, Object> params) {
        MyAssert.checkNotNull(varContext);
        MyAssert.checkNotNull(params);
        String var = varContext.STRING().getText();
        MyAssert.checkArgument(StringUtil.isNotBlank(var), "The variable must not be empty.");
        Object obj = params.get(var);
        MyAssert.checkNotNull(obj);
        return obj;
    }

    public List<Object> extractParasList(List<HBaseSQLParser.VarContext> varContextList, Map<String, Object> para) {
        MyAssert.checkNotNull(varContextList);
        MyAssert.checkNotNull(para);

        List<Object> result = new ArrayList<>();
        for (HBaseSQLParser.VarContext varContext : varContextList) {
            result.add(extractParam(varContext, para));
        }
        return result;
    }

    public HBaseSQLParser.SelecthqlcContext parseSelectHqlContext(HBaseSQLParser.ProgContext progContext) {
        MyAssert.checkNotNull(progContext);
        HBaseSQLParser.SelectHqlClContext selectHqlClContext = (HBaseSQLParser.SelectHqlClContext) progContext;
        HBaseSQLParser.SelecthqlcContext result = selectHqlClContext.selecthqlc();
        MyAssert.checkNotNull(result);
        return result;
    }

    public HBaseSQLParser.InserthqlcContext parseInsertHqlContext(HBaseSQLParser.ProgContext progContext) {
        MyAssert.checkNotNull(progContext);
        HBaseSQLParser.InsertHqlClContext insertHqlClContext = (HBaseSQLParser.InsertHqlClContext) progContext;
        HBaseSQLParser.InserthqlcContext result = insertHqlClContext.inserthqlc();
        MyAssert.checkNotNull(result);
        return result;
    }

    public HBaseSQLParser.DeletehqlcContext parseDeleteHqlContext(HBaseSQLParser.ProgContext progContext) {
        MyAssert.checkNotNull(progContext);
        HBaseSQLParser.DeleteHqlClContext deleteHqlClContext = (HBaseSQLParser.DeleteHqlClContext) progContext;
        HBaseSQLParser.DeletehqlcContext result = deleteHqlClContext.deletehqlc();
        MyAssert.checkNotNull(result);
        return result;
    }

    public long parseTimeStamp(HBaseSQLParser.TsExpContext tsExpContext) {
        MyAssert.checkNotNull(tsExpContext);
        String value = tsExpContext.timestamp().getText();
        MyAssert.checkArgument(StringUtil.isNotBlank(value), "The value of timestamp must not be empty.");
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new HBaseSqlColValueAnalysisException(String.format("The timestamp %s is not a standard timestamp format, " +
                    "please enter a 13-bit Unix timestamp.", value));
        }
    }

    public Date parseTimeStampDate(HBaseSQLParser.TsExpContext tsExpContext) {
        long timestamp = parseTimeStamp(tsExpContext);
        Date date = new Date(timestamp);
        MyAssert.checkNotNull(date);
        return date;
    }

    public Object parseConstant(HBaseColumn column, HBaseSQLParser.ConstantContext constantContext) {
        MyAssert.checkNotNull(column);
        MyAssert.checkNotNull(constantContext);
        String constant = constantContext.STRING().getText();
        MyAssert.checkArgument(StringUtil.isNotBlank(constant), "The constant value must not be empty.");
        Object obj = column.getColumnType().getTypeHandler().extractTargetTypeStrValue(constant);
        MyAssert.checkNotNull(obj);
        return obj;
    }


    public HBaseTableSchema getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(HBaseTableSchema tableSchema) {
        this.tableSchema = tableSchema;
    }

    public TableQuerySetting getSqlRuntimeSetting() {
        return this.tableSchema.getTableQuerySetting();
    }

    public RowKey<byte[]> startRow() {
        return new BytesRowKey(new byte[0]);
    }

    public RowKey<byte[]> endRow() {
        return new BytesRowKey(new byte[0]);
    }
}

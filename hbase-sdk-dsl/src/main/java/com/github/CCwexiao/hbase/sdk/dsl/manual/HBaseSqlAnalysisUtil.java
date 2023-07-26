package com.github.CCwexiao.hbase.sdk.dsl.manual;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlColValueAnalysisException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.manual.visitor.*;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2022/12/4 11:59
 */
public final class HBaseSqlAnalysisUtil {
    private HBaseSqlAnalysisUtil() {
    }

    public static HBaseColumn extractColumnSchema(HBaseTableSchema tableSchema, HBaseSQLParser.ColContext colContext) {
        MyAssert.checkNotNull(colContext);

        String col = colContext.STRING().getText();
        String[] colArr = col.split(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR);
        if (colArr.length == 1) {
            return tableSchema.findColumn(colArr[0]);
        } else if (colArr.length == 2) {
            return tableSchema.findColumn(colArr[0], colArr[1]);
        } else {
            throw new HBaseSqlAnalysisException("Can not get family and column from the col:" + col);
        }
    }

    public static List<HBaseColumn> extractColumnSchemaList(HBaseTableSchema tableSchema,
                                                            HBaseSQLParser.ColListContext colListContext) {
        MyAssert.checkNotNull(colListContext);

        List<HBaseColumn> columnList = new ArrayList<>();
        for (HBaseSQLParser.ColContext colContext : colListContext.col()) {
            columnList.add(extractColumnSchema(tableSchema, colContext));
        }
        return columnList;
    }

    public static List<HBaseColumn> extractColumnSchemaList(HBaseTableSchema tableSchema,
                                                            HBaseSQLParser.SelectColListContext selectColListContext) {
        MyAssert.checkNotNull(selectColListContext);
        SelectColListVisitor visitor = new SelectColListVisitor(tableSchema);
        return selectColListContext.accept(visitor);
    }

    public static Object extractParam(HBaseSQLParser.VarContext varContext, Map<String, Object> params) {
        MyAssert.checkNotNull(varContext);
        MyAssert.checkNotNull(params);
        String var = varContext.STRING().getText();
        MyAssert.checkArgument(StringUtil.isNotBlank(var), "The variable must not be empty.");
        Object obj = params.get(var);
        MyAssert.checkNotNull(obj);
        return obj;
    }

    public static Object extractConstant(HBaseColumn column, HBaseSQLParser.ConstantContext constantContext) {
        MyAssert.checkNotNull(column);
        MyAssert.checkNotNull(constantContext);
        String constant = constantContext.STRING().getText();
        MyAssert.checkArgument(StringUtil.isNotBlank(constant), "The constant value must not be empty.");
        Object obj = column.getColumnType().getTypeHandler().extractMatchTtypeValue(constant);
        MyAssert.checkNotNull(obj);
        return obj;
    }

    public static Object extractInsertConstantValue(HBaseColumn column, HBaseSQLParser.InsertValueContext insertValueContext) {
        MyAssert.checkNotNull(column);
        MyAssert.checkNotNull(insertValueContext);
        InsertValueVisitor visitor = new InsertValueVisitor(column);
        return insertValueContext.accept(visitor);
    }

    public static List<Object> extractConstantList(HBaseColumn column,
                                                   List<HBaseSQLParser.ConstantContext> constantContextList) {
        List<Object> result = new ArrayList<>();
        for (HBaseSQLParser.ConstantContext constantContext : constantContextList) {
            result.add(extractConstant(column, constantContext));
        }
        return result;
    }

    public static List<Object> extractParasList(List<HBaseSQLParser.VarContext> varContextList, Map<String, Object> para) {
        MyAssert.checkNotNull(varContextList);
        MyAssert.checkNotNull(para);

        List<Object> result = new ArrayList<>();
        for (HBaseSQLParser.VarContext varContext : varContextList) {
            result.add(extractParam(varContext, para));
        }
        return result;
    }

    public static HBaseSQLParser.SelecthqlcContext parseSelectHqlContext(HBaseSQLParser.ProgContext progContext) {
        MyAssert.checkNotNull(progContext);
        HBaseSQLParser.SelectHqlClContext selectHqlClContext = (HBaseSQLParser.SelectHqlClContext) progContext;
        HBaseSQLParser.SelecthqlcContext result = selectHqlClContext.selecthqlc();
        MyAssert.checkNotNull(result);
        return result;
    }

    public static HBaseSQLParser.InserthqlcContext parseInsertHqlContext(HBaseSQLParser.ProgContext progContext) {
        MyAssert.checkNotNull(progContext);
        HBaseSQLParser.InsertHqlClContext insertHqlClContext = (HBaseSQLParser.InsertHqlClContext) progContext;
        HBaseSQLParser.InserthqlcContext result = insertHqlClContext.inserthqlc();
        MyAssert.checkNotNull(result);
        return result;
    }

    public static HBaseSQLParser.DeletehqlcContext parseDeleteHqlContext(HBaseSQLParser.ProgContext progContext) {
        MyAssert.checkNotNull(progContext);
        HBaseSQLParser.DeleteHqlClContext deleteHqlClContext = (HBaseSQLParser.DeleteHqlClContext) progContext;
        HBaseSQLParser.DeletehqlcContext result = deleteHqlClContext.deletehqlc();
        MyAssert.checkNotNull(result);
        return result;
    }

    public static long extractTimeStamp(HBaseSQLParser.TsExpContext tsExpContext) {
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

    public static Date extractTimeStampDate(HBaseSQLParser.TsExpContext tsExpContext) {
        MyAssert.checkNotNull(tsExpContext);
        long timestamp = extractTimeStamp(tsExpContext);
        Date date = new Date(timestamp);
        MyAssert.checkNotNull(date);
        return date;
    }

    public static TimeStampRange extractTimeStampRange(HBaseTableSchema tableSchema, HBaseSQLParser.TsRangeContext tsRangeContext) {
        MyAssert.checkNotNull(tsRangeContext);
        TimeStampRangeVisitor visitor = new TimeStampRangeVisitor(tableSchema);
        TimeStampRange timeStampRange = tsRangeContext.accept(visitor);
        MyAssert.checkNotNull(timeStampRange);
        return timeStampRange;
    }

    public static RowKeyRange extractRowKeyRange(HBaseTableSchema tableSchema, HBaseSQLParser.RowKeyRangeExpContext rowKeyRangeContext) {
        MyAssert.checkNotNull(rowKeyRangeContext);
        RowKeyRangeVisitor visitor = new RowKeyRangeVisitor(tableSchema);
        RowKeyRange rowKeyRange = rowKeyRangeContext.accept(visitor);
        MyAssert.checkNotNull(rowKeyRange);
        return rowKeyRange;
    }

    public static RowKey<?> extractRowKey(HBaseTableSchema tableSchema, HBaseSQLParser.RowKeyExpContext rowKeyExpContext) {
        MyAssert.checkNotNull(rowKeyExpContext);
        RowKeyConstantVisitor visitor = new RowKeyConstantVisitor(tableSchema);
        RowKey<?> rowKey = rowKeyExpContext.accept(visitor);
        MyAssert.checkNotNull(rowKey);
        MyAssert.checkNotNull(rowKey.toBytes());
        return rowKey;
    }

    public static QueryExtInfo parseQueryExtInfo(HBaseTableSchema tableSchema, HBaseSQLParser.SelecthqlcContext selectHqlContext) {
        MyAssert.checkNotNull(selectHqlContext);
        QueryExtInfo queryExtInfo = new QueryExtInfo();

        // 解析最大版本号
        HBaseSQLParser.MaxVersionExpContext maxVersionExpContext = selectHqlContext.maxVersionExp();
        if (maxVersionExpContext != null) {
            int maxVersion;
            try {
                maxVersion = Integer.parseInt(maxVersionExpContext.maxversion().STRING().getText());
            } catch (NumberFormatException e) {
                throw new HBaseSqlAnalysisException("The value of max version must be one integer number.");
            }
            queryExtInfo.setMaxVersions(maxVersion);
        }

        // 解析起止时间戳范围
        HBaseSQLParser.TsRangeContext tsRangeContext = selectHqlContext.tsRange();
        if (tsRangeContext != null) {
            TimeStampRange timeStampRange = extractTimeStampRange(tableSchema, tsRangeContext);
            queryExtInfo.setTimeRange(timeStampRange.getStart(), timeStampRange.getEnd());
        }

        // 解析limit
        HBaseSQLParser.LimitExpContext limitExpContext = selectHqlContext.limitExp();
        if (limitExpContext != null) {
            int limit;
            final TerminalNode terminalNode = limitExpContext.STRING();
            try {
                limit = Integer.parseInt(terminalNode.getText());
            } catch (NumberFormatException e) {
                throw new HBaseSqlAnalysisException("The value of limit must be a number.");
            }
            if(limit <= 0) {
                throw new HBaseSqlAnalysisException("The value of limit must be a positive number.");
            }
            queryExtInfo.setLimit(limit);
        }
        return queryExtInfo;
    }
}

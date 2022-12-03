package com.github.CCwexiao.hbase.sdk.dsl.manual;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCwexiao.hbase.sdk.dsl.client.QueryExtInfo;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.RowKeyFunc;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;
import com.github.CCwexiao.hbase.sdk.dsl.model.TableQuerySetting;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.manual.visitor.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/11/27 10:31 下午
 */
@Deprecated
public class HBaseSQLContextUtil {

    /**
     * 从CidContext中解析HBaseTableSchema
     *
     * @param hbaseTableConfig HBase表信息
     * @param cidContext       CidContext
     * @return HBaseColumnSchema
     */
    public static HBaseColumn parseHBaseColumnSchema(HBaseTableConfig hbaseTableConfig, HBaseSQLParser.CidContext cidContext) {
        ObjUtil.checkIsNull(hbaseTableConfig);
        ObjUtil.checkIsNull(cidContext);

        String cid = cidContext.TEXT().getText();

        String[] parts = cid.split(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR);

        if (parts.length == 1) {
            return hbaseTableConfig.gethBaseTableSchema().findColumn(parts[0]);
        }
        if (parts.length == 2) {
            return hbaseTableConfig.gethBaseTableSchema().findColumn(parts[0], parts[1]);
        }
        throw new HBaseOperationsException("parseHBaseColumnSchema error. cid=" + cid);
    }

    /**
     * 从CidListContext解析HBaseColumnSchema列表
     *
     * @param hBaseTableConfig HBase表信息
     * @param cidListContext   CidListContext
     * @return HBaseColumnSchema列表
     */
    public static List<HBaseColumn> parseHBaseColumnSchemaList(HBaseTableConfig hBaseTableConfig, HBaseSQLParser.CidListContext cidListContext) {
        ObjUtil.checkIsNull(hBaseTableConfig);
        ObjUtil.checkIsNull(cidListContext);

        List<HBaseColumn> result = new ArrayList<>();

        for (HBaseSQLParser.CidContext cidContext : cidListContext.cid()) {
            result.add(parseHBaseColumnSchema(hBaseTableConfig, cidContext));
        }

        return result;
    }

    /**
     * 从SelectCidListContext解析HBaseColumnSchema列表
     *
     * @param hBaseTableConfig     HBase表信息
     * @param selectCidListContext SelectCidListContext
     * @return HBaseColumnSchema列表
     */
    public static List<HBaseColumn> parseHBaseColumnSchemaList(HBaseTableConfig hBaseTableConfig, HBaseSQLParser.SelectCidListContext selectCidListContext) {
        ObjUtil.checkIsNull(hBaseTableConfig);
        ObjUtil.checkIsNull(selectCidListContext);

        SelectColListVisitor visitor = new SelectColListVisitor(hBaseTableConfig);
        return selectCidListContext.accept(visitor);
    }

    /**
     * 解析参数
     *
     * @param varContext 参数上下文 # name #
     * @param para       所有传参
     * @return 解析出的数据
     */
    public static Object parsePara(HBaseSQLParser.VarContext varContext,
                                   Map<String, Object> para) {
        ObjUtil.checkIsNull(varContext);
        ObjUtil.checkIsNull(para);

        String var = varContext.TEXT().getText();
        ObjUtil.checkEmptyString(var);

        Object obj = para.get(var);
        ObjUtil.checkIsNull(obj);

        return obj;
    }


    /**
     * 解析 select 语法上下文
     *
     * @param progContext 全局语句上下文
     * @return select 语法上下文
     */
    public static HBaseSQLParser.SelecthqlcContext parseSelecthqlcContext(HBaseSQLParser.ProgContext progContext) {
        ObjUtil.checkIsNull(progContext);

        HBaseSQLParser.SelectHqlClContext selectHqlClContext = (HBaseSQLParser.SelectHqlClContext) progContext;
        HBaseSQLParser.SelecthqlcContext result = selectHqlClContext.selecthqlc();
        ObjUtil.checkIsNull(result);

        return result;
    }

    /**
     * 解析 insert 语法上下文
     *
     * @param progContext 全局语句上下文
     * @return insert 语法上下文
     */
    public static HBaseSQLParser.InserthqlcContext parseInserthqlcContext(HBaseSQLParser.ProgContext progContext) {
        ObjUtil.checkIsNull(progContext);

        HBaseSQLParser.InsertHqlClContext insertHqlClContext = (HBaseSQLParser.InsertHqlClContext) progContext;
        HBaseSQLParser.InserthqlcContext result = insertHqlClContext.inserthqlc();

        ObjUtil.checkIsNull(result);

        return result;
    }


    /**
     * 解析 delete 语法上下文
     *
     * @param progContext 全局语句上下文
     * @return delete 语法上下文
     */
    public static HBaseSQLParser.DeletehqlcContext parseDeletehqlcContext(HBaseSQLParser.ProgContext progContext) {
        ObjUtil.checkIsNull(progContext);

        HBaseSQLParser.DeleteHqlClContext deleteHqlClContext = (HBaseSQLParser.DeleteHqlClContext) progContext;
        HBaseSQLParser.DeletehqlcContext result = deleteHqlClContext.deletehqlc();

        ObjUtil.checkIsNull(progContext);

        return result;
    }

    /**
     * 时间戳转换
     *
     * @param tsExpContext 时间戳表达式
     * @return long型时间戳
     */
    public static long parseTimeStamp(HBaseSQLParser.TsExpContext tsExpContext) {
        MyAssert.checkNotNull(tsExpContext);
        String value = tsExpContext.timestamp().getText();
        MyAssert.checkArgument(StringUtil.isNotBlank(value), "The value of timestamp must not be empty.");
        return Long.parseLong(value);
    }

    public static Date parseTimeStampDate(HBaseSQLParser.TsExpContext tsExpContext, TableQuerySetting runtimeSetting) {
        MyAssert.checkNotNull(tsExpContext);
        MyAssert.checkNotNull(runtimeSetting);

        try {
            long timestamp = parseTimeStamp(tsExpContext);
            Date date = new Date(timestamp);
            MyAssert.checkNotNull(date);
            return date;

        } catch (Exception e) {
            throw new HBaseOperationsException("please enter a 13-bit Unix timestamp.");
        }
    }

    /**
     * 解析时间戳
     *
     * @param tsrangeContext 解析时间戳范围
     * @return 时间戳范围
     */
    public static TimeStampRange parseTimeStampRange(HBaseSQLParser.TsRangeContext tsrangeContext) {
        ObjUtil.checkIsNull(tsrangeContext);

        TimeStampRangeVisitor visitor = new TimeStampRangeVisitor();
        TimeStampRange timeStampRange = tsrangeContext.accept(visitor);

        ObjUtil.checkIsNull(timeStampRange);

        return timeStampRange;
    }

    /**
     * 解析row Key
     *
     * @param rowKeyExpContext       row key表达式上下文
     * @param hBaseSQLRuntimeSetting HBase SQL 运行时配置
     * @return rowKey
     */
    public static RowKey parseRowKey(HBaseSQLParser.RowKeyExpContext rowKeyExpContext, TableQuerySetting hBaseSQLRuntimeSetting) {
        ObjUtil.checkIsNull(rowKeyExpContext);
        ObjUtil.checkIsNull(hBaseSQLRuntimeSetting);
        RowKeyConstantVisitor visitor = new RowKeyConstantVisitor(hBaseSQLRuntimeSetting);
        RowKey rowKey = rowKeyExpContext.accept(visitor);
        ObjUtil.checkIsNull(rowKey);
        return rowKey;
    }

    /**
     * 解析IN语法中的rowKey列表
     * @param rowKeyExpContext row key exp
     * @param hBaseSQLRuntimeSetting HBase SQL运行时配置
     * @return rowKey列表
     */
    public static List<RowKey> parseRowKeyList(HBaseSQLParser.RowKeyExpContext rowKeyExpContext, TableQuerySetting hBaseSQLRuntimeSetting) {
        ObjUtil.checkIsNull(rowKeyExpContext);
        ObjUtil.checkIsNull(hBaseSQLRuntimeSetting);
        //RowKeyInSomeKeysVisitor visitor = new RowKeyInSomeKeysVisitor(hBaseSQLRuntimeSetting);
        RowKeyListConstantVisitor visitor = new RowKeyListConstantVisitor(hBaseSQLRuntimeSetting);
        final List<RowKey> rowKeyList = rowKeyExpContext.accept(visitor);
        if(rowKeyList == null|| rowKeyList.isEmpty()){
            throw new HBaseOperationsException("please enter one or more row key.");
        }
        return rowKeyList;
    }

    /**
     * 从SQL中解析row key的自定义函数
     * @param rowKeyExpContext row key表达式上下文
     * @param hBaseSQLRuntimeSetting HBase SQL 运行时配置
     * @return RowKeyTextFunc
     */
    public static RowKeyFunc parseRowKeyFunction(HBaseSQLParser.RowKeyExpContext rowKeyExpContext, TableQuerySetting hBaseSQLRuntimeSetting){
        ObjUtil.checkIsNull(rowKeyExpContext);
        ObjUtil.checkIsNull(hBaseSQLRuntimeSetting);
        RowKeyFunctionVisitor visitor = new RowKeyFunctionVisitor(hBaseSQLRuntimeSetting);
        final RowKeyFunc rowKeyTextFunc = rowKeyExpContext.accept(visitor);
        ObjUtil.checkIsNull(rowKeyTextFunc);
        return rowKeyTextFunc;
    }

    /**
     * 解析 rowKey range
     *
     * @param rowKeyRangeContext     rowKeyRange 表达式
     * @param hBaseSQLRuntimeSetting HBase SQL 运行时配置
     * @return rowKeyRange
     */
    public static RowKeyRange parseRowKeyRange(HBaseSQLParser.RowKeyRangeContext rowKeyRangeContext, TableQuerySetting hBaseSQLRuntimeSetting) {
        ObjUtil.checkIsNull(rowKeyRangeContext);
        ObjUtil.checkIsNull(hBaseSQLRuntimeSetting);

        RowKeyRangeVisitor visitor = new RowKeyRangeVisitor(hBaseSQLRuntimeSetting);
        RowKeyRange rowKeyRange = rowKeyRangeContext.accept(visitor);

        ObjUtil.checkIsNull(rowKeyRange);

        return rowKeyRange;

    }

    public static RowKeyRange parseRowKeyRange2(HBaseSQLParser.RowKeyRangeContext rowKeyRangeContext, TableQuerySetting hBaseSQLRuntimeSetting){
        ObjUtil.checkIsNull(rowKeyRangeContext);
        ObjUtil.checkIsNull(hBaseSQLRuntimeSetting);

        RowKeyRangeVisitor visitor = new RowKeyRangeVisitor(hBaseSQLRuntimeSetting);
        RowKeyRange rowKeyRange = rowKeyRangeContext.accept(visitor);

        ObjUtil.checkIsNull(rowKeyRange);

        return rowKeyRange;
    }

    public static Object parseInsertConstantValue(HBaseColumn hbaseColumnSchema,
                                                  HBaseSQLParser.InsertValueContext insertValueContext,
                                                  TableQuerySetting runtimeSetting) {
        ObjUtil.checkIsNull(hbaseColumnSchema);
        ObjUtil.checkIsNull(insertValueContext);
        ObjUtil.checkIsNull(runtimeSetting);

        InsertValueVisitor visitor = new InsertValueVisitor(hbaseColumnSchema, runtimeSetting);
        return insertValueContext.accept(visitor);
    }

    /**
     * 解析字段赋值变量
     *
     * @param hBaseColumnSchema HBase字段定义数据模型
     * @param constantContext   字段上下文
     * @param runtimeSetting    运行时设置
     * @return 解析字段值
     */
    public static Object parseConstant(HBaseColumn hBaseColumnSchema,
                                       HBaseSQLParser.ConstantContext constantContext,
                                       TableQuerySetting runtimeSetting) {
        ObjUtil.checkIsNull(hBaseColumnSchema);
        ObjUtil.checkIsNull(constantContext);
        ObjUtil.checkIsNull(runtimeSetting);

        String constant = constantContext.TEXT().getText();
        ObjUtil.checkEmptyString(constant);

        Object obj = runtimeSetting.interpret(hBaseColumnSchema.getType(), constant);
        ObjUtil.checkIsNull(obj);
        return obj;
    }

    /**
     * 解析参数列表
     *
     * @param varContextList 参数列表
     * @param para           参数key
     * @return 获取参数值
     */
    public static List<Object> parseParaList(List<HBaseSQLParser.VarContext> varContextList, Map<String, Object> para) {
        ObjUtil.checkIsNull(varContextList);
        ObjUtil.checkIsNull(para);

        List<Object> result = new ArrayList<>();
        for (HBaseSQLParser.VarContext varContext : varContextList) {
            result.add(parsePara(varContext, para));
        }
        return result;
    }

    public static List<Object> parseConstantList(HBaseColumn hBaseColumnSchema,
                                                 List<HBaseSQLParser.ConstantContext> constantContextList,
                                                 TableQuerySetting runtimeSetting) {
        ObjUtil.checkIsNull(hBaseColumnSchema);
        ObjUtil.checkIsNull(constantContextList);
        ObjUtil.checkIsNull(runtimeSetting);

        List<Object> result = new ArrayList<>();
        for (HBaseSQLParser.ConstantContext constantContext : constantContextList) {
            result.add(parseConstant(hBaseColumnSchema, constantContext, runtimeSetting));
        }
        return result;
    }


    public static QueryExtInfo parseQueryExtInfo(HBaseSQLParser.SelecthqlcContext selecthqlcContext) {
        ObjUtil.checkIsNull(selecthqlcContext);

        QueryExtInfo queryExtInfo = new QueryExtInfo();

        // 解析最大版本号
        HBaseSQLParser.MaxVersionExpContext maxVersionExpContext = selecthqlcContext.maxVersionExp();
        if (maxVersionExpContext != null) {
            queryExtInfo.setMaxVersions(Integer.parseInt(maxVersionExpContext.maxversion().TEXT().getText()));
        }

        // 解析起止时间戳范围
        HBaseSQLParser.TsrangeContext tsrangeContext = selecthqlcContext.tsrange();
        if (tsrangeContext != null) {
            TimeStampRange timeStampRange = parseTimeStampRange(tsrangeContext);
            queryExtInfo.setTimeRange(timeStampRange.getStart(), timeStampRange.getEnd());
        }

        // 解析limit
        HBaseSQLParser.LimitExpContext limitExpContext = selecthqlcContext.limitExp();
        if (limitExpContext != null) {
            final List<TerminalNode> terminalNodeList = limitExpContext.TEXT();
            ObjUtil.checkLimitIsValid(terminalNodeList.size() == 1 || terminalNodeList.size() == 2);

            if (terminalNodeList.size() == 1) {
                queryExtInfo.setLimit(0L, Long.parseLong(terminalNodeList.get(0).getText()));
            }

            if (terminalNodeList.size() == 2) {
                queryExtInfo.setLimit(Long.parseLong(terminalNodeList.get(0).getText()),
                        Long.parseLong(terminalNodeList.get(1).getText()));
            }
        }

        return queryExtInfo;
    }


    private HBaseSQLContextUtil() {

    }
}

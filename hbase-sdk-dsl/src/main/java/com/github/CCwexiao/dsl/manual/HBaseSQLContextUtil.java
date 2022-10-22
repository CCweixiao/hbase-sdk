package com.github.CCwexiao.dsl.manual;

import com.github.CCweixiao.hbase.sdk.common.constants.HMHBaseConstants;
import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser.*;
import com.github.CCwexiao.dsl.client.QueryExtInfo;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;
import com.github.CCwexiao.dsl.config.HBaseColumnSchema;
import com.github.CCwexiao.dsl.config.HBaseSQLRuntimeSetting;
import com.github.CCwexiao.dsl.config.HBaseTableConfig;
import com.github.CCwexiao.dsl.manual.visitor.*;
import com.github.CCwexiao.dsl.util.Util;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2020/11/27 10:31 下午
 */
public class HBaseSQLContextUtil {

    /**
     * 从CidContext中解析HBaseTableSchema
     *
     * @param hbaseTableConfig HBase表信息
     * @param cidContext       CidContext
     * @return HBaseColumnSchema
     */
    public static HBaseColumnSchema parseHBaseColumnSchema(HBaseTableConfig hbaseTableConfig, CidContext cidContext) {
        Util.checkNull(hbaseTableConfig);
        Util.checkNull(cidContext);

        String cid = cidContext.TEXT().getText();

        String[] parts = cid.split(HMHBaseConstants.FAMILY_QUALIFIER_SEPARATOR);

        if (parts.length == 1) {
            return hbaseTableConfig.gethBaseTableSchema().findColumnSchema(parts[0]);
        }
        if (parts.length == 2) {
            return hbaseTableConfig.gethBaseTableSchema().findColumnSchema(parts[0], parts[1]);
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
    public static List<HBaseColumnSchema> parseHBaseColumnSchemaList(HBaseTableConfig hBaseTableConfig, CidListContext cidListContext) {
        Util.checkNull(hBaseTableConfig);
        Util.checkNull(cidListContext);

        List<HBaseColumnSchema> result = new ArrayList<>();

        for (CidContext cidContext : cidListContext.cid()) {
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
    public static List<HBaseColumnSchema> parseHBaseColumnSchemaList(HBaseTableConfig hBaseTableConfig, SelectCidListContext selectCidListContext) {
        Util.checkNull(hBaseTableConfig);
        Util.checkNull(selectCidListContext);

        SelectCidListVisitor visitor = new SelectCidListVisitor(hBaseTableConfig);
        return selectCidListContext.accept(visitor);
    }

    /**
     * 解析参数
     *
     * @param varContext 参数上下文 # name #
     * @param para       所有传参
     * @return 解析出的数据
     */
    public static Object parsePara(VarContext varContext,
                                   Map<String, Object> para) {
        Util.checkNull(varContext);
        Util.checkNull(para);

        String var = varContext.TEXT().getText();
        Util.checkEmptyString(var);

        Object obj = para.get(var);
        Util.checkNull(obj);

        return obj;
    }


    /**
     * 解析 select 语法上下文
     *
     * @param progContext 全局语句上下文
     * @return select 语法上下文
     */
    public static SelecthqlcContext parseSelecthqlcContext(ProgContext progContext) {
        Util.checkNull(progContext);

        SelectHqlClContext selectHqlClContext = (SelectHqlClContext) progContext;
        SelecthqlcContext result = selectHqlClContext.selecthqlc();
        Util.checkNull(result);

        return result;
    }

    /**
     * 解析 insert 语法上下文
     *
     * @param progContext 全局语句上下文
     * @return insert 语法上下文
     */
    public static InserthqlcContext parseInserthqlcContext(ProgContext progContext) {
        Util.checkNull(progContext);

        InsertHqlClContext insertHqlClContext = (InsertHqlClContext) progContext;
        InserthqlcContext result = insertHqlClContext.inserthqlc();

        Util.checkNull(result);

        return result;
    }


    /**
     * 解析 delete 语法上下文
     *
     * @param progContext 全局语句上下文
     * @return delete 语法上下文
     */
    public static DeletehqlcContext parseDeletehqlcContext(ProgContext progContext) {
        Util.checkNull(progContext);

        DeleteHqlClContext deleteHqlClContext = (DeleteHqlClContext) progContext;
        DeletehqlcContext result = deleteHqlClContext.deletehqlc();

        Util.checkNull(progContext);

        return result;
    }

    /**
     * 时间戳转换
     *
     * @param tsexpContext 时间戳表达式
     * @return long型时间戳
     */
    public static long parseTimeStamp(TsexpContext tsexpContext) {
        Util.checkNull(tsexpContext);

        String constant = tsexpContext.constant().TEXT().getText();
        Util.checkEmptyTimestamp(constant);
        return Long.parseLong(constant);
    }

    public static Date parseTimeStampDate(TsexpContext tsexpContext, HBaseSQLRuntimeSetting runtimeSetting) {
        Util.checkNull(tsexpContext);
        Util.checkNull(runtimeSetting);

        try {
            long timestamp = parseTimeStamp(tsexpContext);
            Date date = new Date(timestamp);
            Util.checkNull(date);
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
    public static TimeStampRange parseTimeStampRange(TsrangeContext tsrangeContext) {
        Util.checkNull(tsrangeContext);

        TimeStampRangeVisitor visitor = new TimeStampRangeVisitor();
        TimeStampRange timeStampRange = tsrangeContext.accept(visitor);

        Util.checkNull(timeStampRange);

        return timeStampRange;
    }

    /**
     * 解析row Key
     *
     * @param rowKeyExpContext       row key表达式上下文
     * @param hBaseSQLRuntimeSetting HBase SQL 运行时配置
     * @return rowKey
     */
    public static RowKey parseRowKey(RowKeyExpContext rowKeyExpContext, HBaseSQLRuntimeSetting hBaseSQLRuntimeSetting) {
        Util.checkNull(rowKeyExpContext);
        Util.checkNull(hBaseSQLRuntimeSetting);
        RowKeyConstantVisitor visitor = new RowKeyConstantVisitor(hBaseSQLRuntimeSetting);
        RowKey rowKey = rowKeyExpContext.accept(visitor);
        Util.checkNull(rowKey);
        return rowKey;
    }

    /**
     * 解析IN语法中的rowKey列表
     * @param rowKeyExpContext row key exp
     * @param hBaseSQLRuntimeSetting HBase SQL运行时配置
     * @return rowKey列表
     */
    public static List<RowKey> parseRowKeyList(RowKeyExpContext rowKeyExpContext, HBaseSQLRuntimeSetting hBaseSQLRuntimeSetting) {
        Util.checkNull(rowKeyExpContext);
        Util.checkNull(hBaseSQLRuntimeSetting);
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
    public static RowKeyTextFunc parseRowKeyFunction(RowKeyExpContext rowKeyExpContext, HBaseSQLRuntimeSetting hBaseSQLRuntimeSetting){
        Util.checkNull(rowKeyExpContext);
        Util.checkNull(hBaseSQLRuntimeSetting);
        RowKeyFunctionVisitor visitor = new RowKeyFunctionVisitor(hBaseSQLRuntimeSetting);
        final RowKeyTextFunc rowKeyTextFunc = rowKeyExpContext.accept(visitor);
        Util.checkNull(rowKeyTextFunc);
        return rowKeyTextFunc;
    }

    /**
     * 解析 rowKey range
     *
     * @param rowKeyRangeContext     rowKeyRange 表达式
     * @param hBaseSQLRuntimeSetting HBase SQL 运行时配置
     * @return rowKeyRange
     */
    public static RowKeyRange parseRowKeyRange(RowKeyRangeContext rowKeyRangeContext, HBaseSQLRuntimeSetting hBaseSQLRuntimeSetting) {
        Util.checkNull(rowKeyRangeContext);
        Util.checkNull(hBaseSQLRuntimeSetting);

        RowKeyRangeVisitor visitor = new RowKeyRangeVisitor(hBaseSQLRuntimeSetting);
        RowKeyRange rowKeyRange = rowKeyRangeContext.accept(visitor);

        Util.checkNull(rowKeyRange);

        return rowKeyRange;

    }

    public static RowKeyRange parseRowKeyRange2(RowKeyRangeContext rowKeyRangeContext, HBaseSQLRuntimeSetting hBaseSQLRuntimeSetting){
        Util.checkNull(rowKeyRangeContext);
        Util.checkNull(hBaseSQLRuntimeSetting);

        RowKeyRangeVisitor visitor = new RowKeyRangeVisitor(hBaseSQLRuntimeSetting);
        RowKeyRange rowKeyRange = rowKeyRangeContext.accept(visitor);

        Util.checkNull(rowKeyRange);

        return rowKeyRange;
    }

    public static Object parseInsertConstantValue(HBaseColumnSchema hbaseColumnSchema,
                                                  InsertValueContext insertValueContext,
                                                  HBaseSQLRuntimeSetting runtimeSetting) {
        Util.checkNull(hbaseColumnSchema);
        Util.checkNull(insertValueContext);
        Util.checkNull(runtimeSetting);

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
    public static Object parseConstant(HBaseColumnSchema hBaseColumnSchema,
                                       ConstantContext constantContext,
                                       HBaseSQLRuntimeSetting runtimeSetting) {
        Util.checkNull(hBaseColumnSchema);
        Util.checkNull(constantContext);
        Util.checkNull(runtimeSetting);

        String constant = constantContext.TEXT().getText();
        Util.checkEmptyString(constant);

        Object obj = runtimeSetting.interpret(hBaseColumnSchema.getType(), constant);
        Util.checkNull(obj);
        return obj;
    }

    /**
     * 解析参数列表
     *
     * @param varContextList 参数列表
     * @param para           参数key
     * @return 获取参数值
     */
    public static List<Object> parseParaList(List<VarContext> varContextList, Map<String, Object> para) {
        Util.checkNull(varContextList);
        Util.checkNull(para);

        List<Object> result = new ArrayList<>();
        for (VarContext varContext : varContextList) {
            result.add(parsePara(varContext, para));
        }
        return result;
    }

    public static List<Object> parseConstantList(HBaseColumnSchema hBaseColumnSchema,
                                                 List<ConstantContext> constantContextList,
                                                 HBaseSQLRuntimeSetting runtimeSetting) {
        Util.checkNull(hBaseColumnSchema);
        Util.checkNull(constantContextList);
        Util.checkNull(runtimeSetting);

        List<Object> result = new ArrayList<>();
        for (ConstantContext constantContext : constantContextList) {
            result.add(parseConstant(hBaseColumnSchema, constantContext, runtimeSetting));
        }
        return result;
    }


    public static QueryExtInfo parseQueryExtInfo(SelecthqlcContext selecthqlcContext) {
        Util.checkNull(selecthqlcContext);

        QueryExtInfo queryExtInfo = new QueryExtInfo();

        // 解析最大版本号
        MaxVersionExpContext maxVersionExpContext = selecthqlcContext.maxVersionExp();
        if (maxVersionExpContext != null) {
            queryExtInfo.setMaxVersions(Integer.parseInt(maxVersionExpContext.maxversion().TEXT().getText()));
        }

        // 解析起止时间戳范围
        TsrangeContext tsrangeContext = selecthqlcContext.tsrange();
        if (tsrangeContext != null) {
            TimeStampRange timeStampRange = parseTimeStampRange(tsrangeContext);
            queryExtInfo.setTimeRange(timeStampRange.getStart(), timeStampRange.getEnd());
        }

        // 解析limit
        LimitExpContext limitExpContext = selecthqlcContext.limitExp();
        if (limitExpContext != null) {
            final List<TerminalNode> terminalNodeList = limitExpContext.TEXT();
            Util.checkLimitIsValid(terminalNodeList.size() == 1 || terminalNodeList.size() == 2);

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

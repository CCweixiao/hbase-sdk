package com.github.CCwexiao.hbase.sdk.dsl.util;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.model.HQLType;
import com.github.CCwexiao.hbase.sdk.dsl.antlr.HBaseSQLParser;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLErrorStrategy;
import com.github.CCwexiao.hbase.sdk.dsl.manual.HBaseSQLStatementsLexer;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.StringReader;

/**
 * @author leojie 2020/11/24 10:43 下午
 */
public class TreeUtil {
    /**
     * 从HBase sql中获取ProgContext对象
     *
     * @param hql HBase SQL
     * @return ProgContext对象
     */
    public static HBaseSQLParser.ProgContext parseProgContext(String hql) {
        Util.checkEmptyString(hql);
        try {
            ANTLRInputStream input = new ANTLRInputStream(new StringReader(hql));
            HBaseSQLStatementsLexer lexer = new HBaseSQLStatementsLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            HBaseSQLParser parser = new HBaseSQLParser(tokens);
            parser.setErrorHandler(HBaseSQLErrorStrategy.instance);
            return parser.prog();
        } catch (Exception e) {
            throw new HBaseOperationsException("parse hql error.", e);
        }
    }

    public static String parseTableName(String hql) {
        Util.checkEmptyString(hql);
        HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(hql);
        return parseTableName(progContext);
    }

    public static String parseTableName(HBaseSQLParser.ProgContext progContext) {
        Util.checkNull(progContext);

        if (progContext instanceof HBaseSQLParser.InsertHqlClContext) {
            return ((HBaseSQLParser.InsertHqlClContext) progContext)
                    .inserthqlc().tableName().TEXT().getText();
        }
        if (progContext instanceof HBaseSQLParser.SelectHqlClContext) {
            return ((HBaseSQLParser.SelectHqlClContext) progContext)
                    .selecthqlc().tableName().TEXT().getText();
        }
        if (progContext instanceof HBaseSQLParser.DeleteHqlClContext) {
            return ((HBaseSQLParser.DeleteHqlClContext) progContext)
                    .deletehqlc().tableName().TEXT().getText();
        }
        throw new HBaseOperationsException("can't parse the name of hbase table.");
    }

    public static HQLType parseHQLType(String hql) {
        Util.checkEmptyString(hql);
        HBaseSQLParser.ProgContext progContext = TreeUtil.parseProgContext(hql);
        return parseHQLType(progContext);
    }

    public static HQLType parseHQLType(HBaseSQLParser.ProgContext progContext) {
        Util.checkNull(progContext);

        if (progContext instanceof HBaseSQLParser.InsertHqlClContext) {
            return HQLType.PUT;
        }
        if (progContext instanceof HBaseSQLParser.SelectHqlClContext) {
            return HQLType.SELECT;
        }
        if (progContext instanceof HBaseSQLParser.DeleteHqlClContext) {
            return HQLType.DELETE;
        }
        throw new HBaseOperationsException("can't parse hql type.");
    }
}

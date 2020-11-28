package com.github.CCwexiao.dsl.util;

import com.github.CCweixiao.exception.HBaseOperationsException;
import com.github.CCweixiao.model.HQLType;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser;
import com.github.CCwexiao.dsl.auto.HBaseSQLParser.*;
import com.github.CCwexiao.dsl.manual.HBaseSQLErrorStrategy;
import com.github.CCwexiao.dsl.manual.HBaseSQLStatementsLexer;
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
    public static ProgContext parseProgContext(String hql) {
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

    public static String parseTableName(ProgContext progContext) {
        Util.checkNull(progContext);

        if (progContext instanceof InsertHqlClContext) {
            return ((InsertHqlClContext) progContext)
                    .inserthqlc().tableName().TEXT().getText();
        }
        if (progContext instanceof SelectHqlClContext) {
            return ((SelectHqlClContext) progContext)
                    .selecthqlc().tableName().TEXT().getText();
        }
        if (progContext instanceof DeleteHqlClContext) {
            return ((DeleteHqlClContext) progContext)
                    .deletehqlc().tableName().TEXT().getText();
        }
        throw new HBaseOperationsException("can't parse the name of hbase table.");
    }

    public static HQLType parseHQLType(ProgContext progContext) {
        Util.checkNull(progContext);

        if (progContext instanceof InsertHqlClContext) {
            return HQLType.PUT;
        }
        if (progContext instanceof SelectHqlClContext) {
            return HQLType.SELECT;
        }
        if (progContext instanceof DeleteHqlClContext) {
            return HQLType.DELETE;
        }
        throw new HBaseOperationsException("can't parse hql type.");
    }
}

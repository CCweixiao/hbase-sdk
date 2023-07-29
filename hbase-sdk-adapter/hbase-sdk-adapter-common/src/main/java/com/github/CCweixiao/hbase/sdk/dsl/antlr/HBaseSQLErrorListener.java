package com.github.CCweixiao.hbase.sdk.dsl.antlr;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSqlSyntaxException;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;


/**
 * @author leojie 2023/7/29 08:28
 */
public class HBaseSQLErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String msg, RecognitionException e) {
        TokenStream tokenStream = ((Parser)recognizer).getInputStream();
        String hql = tokenStream.getText();
        String underLineError = underlineError(hql, (Token) offendingSymbol, line, charPositionInLine);
        throw new HBaseSqlSyntaxException("\n" + underLineError + "line " + line +
                ",char position at " + charPositionInLine + ", typo: " + msg);
    }

    protected String underlineError(String hql, Token offendingToken,
                                  int line, int charPositionInLine) {
        String[] lines = hql.split("\n");
        String errorLine = lines[line - 1];
        // System.err.println(errorLine);
        StringBuilder sb = new StringBuilder(errorLine);
        sb.append("\n");
        for (int i = 0; i < charPositionInLine; i++) {
            sb.append(" ");
        }

        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if (start >= 0 && stop >= 0) {
            for (int i = start; i <= stop; i++) {
                sb.append("^");
            }
        }
        sb.append("\n");
        return sb.toString();
    }


}

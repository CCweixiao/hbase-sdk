package com.github.CCwexiao.dsl.manual;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCwexiao.dsl.auto.HBaseSQLLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

/**
 * @author leojie 2020/11/24 10:48 下午
 */
public class HBaseSQLStatementsLexer extends HBaseSQLLexer {
    public HBaseSQLStatementsLexer(CharStream input) {
        super(input);
    }

    @Override
    public void recover(LexerNoViableAltException e) {
        throw new HBaseOperationsException("lexer error.", e);
    }
}


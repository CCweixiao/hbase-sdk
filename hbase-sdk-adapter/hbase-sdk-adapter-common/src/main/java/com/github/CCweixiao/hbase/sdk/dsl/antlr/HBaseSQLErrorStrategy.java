package com.github.CCweixiao.hbase.sdk.dsl.antlr;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import org.antlr.v4.runtime.*;

/**
 * @author leojie 2020/11/24 10:55 下午
 */
public class HBaseSQLErrorStrategy extends DefaultErrorStrategy {
    public static final HBaseSQLErrorStrategy INSTANCE = new HBaseSQLErrorStrategy();

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        throw new HBaseOperationsException(e);
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        throw new HBaseOperationsException("parser error.", new InputMismatchException(recognizer));
    }

    @Override
    public void sync(Parser recognizer) {
    }

    private HBaseSQLErrorStrategy() {

    }
}

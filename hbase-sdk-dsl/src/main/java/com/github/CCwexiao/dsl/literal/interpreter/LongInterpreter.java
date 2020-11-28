package com.github.CCwexiao.dsl.literal.interpreter;

import com.github.CCwexiao.dsl.literal.AbstractLiteralInterpreter;

/**
 * @author leojie 2020/11/28 6:00 下午
 */
public class LongInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpretInternal(String literalValue) {
        return Long.parseLong(literalValue);
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return Long.class;
    }
}

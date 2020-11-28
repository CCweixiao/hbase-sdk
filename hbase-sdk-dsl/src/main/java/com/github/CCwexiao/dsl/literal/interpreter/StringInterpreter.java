package com.github.CCwexiao.dsl.literal.interpreter;

import com.github.CCwexiao.dsl.literal.AbstractLiteralInterpreter;

/**
 * @author leojie 2020/11/28 5:58 下午
 */
public class StringInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpretInternal(String literalValue) {
        return literalValue;
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return String.class;
    }
}

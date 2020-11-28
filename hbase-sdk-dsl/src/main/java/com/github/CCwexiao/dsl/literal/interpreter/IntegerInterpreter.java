package com.github.CCwexiao.dsl.literal.interpreter;

import com.github.CCwexiao.dsl.literal.AbstractLiteralInterpreter;

/**
 * @author leojie 2020/11/28 6:01 下午
 */
public class IntegerInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpretInternal(String literalValue) {
        return Integer.parseInt(literalValue);
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return Integer.class;
    }
}

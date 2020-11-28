package com.github.CCwexiao.dsl.literal.interpreter;

import com.github.CCwexiao.dsl.literal.AbstractLiteralInterpreter;

/**
 * @author leojie 2020/11/28 6:07 下午
 */
public class BooleanInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpretInternal(String literalValue) {
        return Boolean.parseBoolean(literalValue);
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return Boolean.class;
    }
}

package com.github.CCwexiao.dsl.literal.interpreter;

import com.github.CCwexiao.dsl.literal.AbstractLiteralInterpreter;

/**
 * @author leojie 2020/11/28 6:02 下午
 */
public class DoubleInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpretInternal(String literalValue) {
        return Double.parseDouble(literalValue);
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return Double.class;
    }
}

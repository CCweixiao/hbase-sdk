package com.github.CCwexiao.hbase.sdk.dsl.literal.interpreter;

import com.github.CCwexiao.hbase.sdk.dsl.literal.AbstractLiteralInterpreter;

/**
 * @author leojie 2020/11/28 5:59 下午
 */
public class ShortInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpretInternal(String literalValue) {
        return Short.parseShort(literalValue);
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return Short.class;
    }
}

package com.github.CCwexiao.dsl.literal.interpreter;

import com.github.CCwexiao.dsl.literal.AbstractLiteralInterpreter;
import com.github.CCwexiao.dsl.util.Util;

/**
 * @author leojie 2020/11/28 6:06 下午
 */
public class CharInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpretInternal(String literalValue) {
        Util.checkLength(literalValue, 1);
        return literalValue.charAt(0);
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return Character.class;
    }
}

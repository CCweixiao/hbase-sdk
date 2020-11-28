package com.github.CCwexiao.dsl.literal;

import com.github.CCwexiao.dsl.util.Util;

/**
 * @author leojie 2020/11/28 5:55 下午
 */
public abstract class AbstractLiteralInterpreter implements LiteralInterpreter {
    abstract protected Object interpretInternal(String literalValue);

    @Override
    public Object interpret(String literalValue) {
        Util.checkNull(literalValue);

        Object obj = interpretInternal(literalValue);
        Util.checkNull(obj);

        return obj;
    }
}

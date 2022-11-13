package com.github.CCwexiao.hbase.sdk.dsl.literal;

import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;

/**
 * @author leojie 2020/11/28 5:55 下午
 */
public abstract class AbstractLiteralInterpreter implements LiteralInterpreter {
    abstract protected Object interpretInternal(String literalValue);

    @Override
    public Object interpret(String literalValue) {
        ObjUtil.checkIsNull(literalValue);

        Object obj = interpretInternal(literalValue);
        ObjUtil.checkIsNull(obj);

        return obj;
    }
}

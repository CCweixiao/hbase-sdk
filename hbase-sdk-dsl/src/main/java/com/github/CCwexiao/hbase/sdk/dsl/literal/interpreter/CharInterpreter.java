package com.github.CCwexiao.hbase.sdk.dsl.literal.interpreter;

import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;
import com.github.CCwexiao.hbase.sdk.dsl.literal.AbstractLiteralInterpreter;

/**
 * @author leojie 2020/11/28 6:06 下午
 */
public class CharInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpretInternal(String literalValue) {
        ObjUtil.checkLength(literalValue, 1);
        return literalValue.charAt(0);
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return Character.class;
    }
}

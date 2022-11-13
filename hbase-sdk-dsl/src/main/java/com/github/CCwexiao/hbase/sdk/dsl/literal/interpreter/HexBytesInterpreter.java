package com.github.CCwexiao.hbase.sdk.dsl.literal.interpreter;

import com.github.CCweixiao.hbase.sdk.common.type.handler.ext.HexBytes;
import com.github.CCwexiao.hbase.sdk.dsl.literal.AbstractLiteralInterpreter;

/**
 * @author leojie 2020/11/28 6:08 下午
 */
public class HexBytesInterpreter extends AbstractLiteralInterpreter {
    @Override
    protected Object interpretInternal(String literalValue) {
        return new HexBytes(literalValue);
    }

    @Override
    public Class<?> getTypeCanInterpret() {
        return HexBytes.class;
    }
}

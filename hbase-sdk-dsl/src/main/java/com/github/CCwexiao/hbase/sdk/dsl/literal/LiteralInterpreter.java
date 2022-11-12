package com.github.CCwexiao.hbase.sdk.dsl.literal;

/**
 * java 类型解释器接口
 * @author leojie 2020/11/28 5:53 下午
 */
public interface LiteralInterpreter {

    /**
     * 转换literalValue为一个Java类型
     * @param literalValue literalValue
     * @return Java类型的数据
     */
    Object interpret(String literalValue);

    /**
     * 获取类型
     * @return 类型
     */
    Class<?> getTypeCanInterpret();
}

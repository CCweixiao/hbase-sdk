package com.github.CCwexiao.dsl.client.rowkeytextfunc;

import com.github.CCwexiao.dsl.client.RowKey;

/**
 * @author leojie 2020/11/28 11:09 上午
 */
public interface RowKeyTextFunc {
    /**
     * 转换文本到rowKey
     *
     * @param text sql文本
     * @return one row key
     */
    RowKey func(String text);

    /**
     * row key转换的函数名
     *
     * @return 函数名
     */
    String funcName();

    /**
     * row key转换函数的描述信息
     *
     * @return 描述信息
     */
    String desc();
}

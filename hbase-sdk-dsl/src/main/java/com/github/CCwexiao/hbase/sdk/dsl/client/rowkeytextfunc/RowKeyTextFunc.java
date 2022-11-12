package com.github.CCwexiao.hbase.sdk.dsl.client.rowkeytextfunc;

import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;

/**
 * @author leojie 2020/11/28 11:09 上午
 */
public interface RowKeyTextFunc {
    /**
     * 转换文本到rowKey
     *
     * @param text sql 中传入的数据值
     * @return one row key
     */
    RowKey func(String text);

    /**
     * 转换HBase的RowKey
     *
     * @param row row
     * @return RowKey
     */
    RowKey convert(byte[] row);

    /**
     * @param rowKey rowKey
     * @return 从byte数组中获取值
     */
    Object reverse(RowKey rowKey);

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

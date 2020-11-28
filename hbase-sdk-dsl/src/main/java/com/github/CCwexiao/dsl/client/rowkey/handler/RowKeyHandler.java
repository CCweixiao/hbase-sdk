package com.github.CCwexiao.dsl.client.rowkey.handler;

import com.github.CCwexiao.dsl.client.RowKey;

/**
 * @author leojie 2020/11/28 4:09 下午
 */
public interface RowKeyHandler {

    /**
     * 转换HBase的RowKey
     *
     * @param row row
     * @return RowKey
     */
    RowKey convert(byte[] row);
}

package com.github.CCwexiao.dsl.client.rowkey.handler;

import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkey.BytesRowKey;

/**
 * @author leojie 2020/11/28 4:18 下午
 */
public class BytesRowKeyHandler implements RowKeyHandler {
    @Override
    public RowKey convert(byte[] row) {
        return new BytesRowKey(row);
    }
}

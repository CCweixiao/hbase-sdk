package com.github.CCweixiao.client.rowkey.handler;

import com.github.CCweixiao.client.rowkey.LongRowKey;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkey.handler.RowKeyHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 11:32 下午
 */
public class LongRowKeyHandler implements RowKeyHandler {
    @Override
    public RowKey convert(byte[] row) {
        return new LongRowKey(Bytes.toLong(row));
    }
}

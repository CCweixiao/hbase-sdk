package com.github.CCweixiao.client.rowkey.handler;

import com.github.CCweixiao.client.rowkey.IntRowKey;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkey.handler.RowKeyHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 11:30 下午
 */
public class IntRowKeyHandler implements RowKeyHandler {
    @Override
    public RowKey convert(byte[] row) {
        return new IntRowKey(Bytes.toInt(row));
    }
}

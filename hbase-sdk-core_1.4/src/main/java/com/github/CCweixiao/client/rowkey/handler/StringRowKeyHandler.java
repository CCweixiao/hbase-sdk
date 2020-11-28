package com.github.CCweixiao.client.rowkey.handler;

import com.github.CCweixiao.client.rowkey.StringRowKey;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkey.handler.RowKeyHandler;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author leojie 2020/11/28 11:31 下午
 */
public class StringRowKeyHandler implements RowKeyHandler {
    @Override
    public RowKey convert(byte[] row) {
        return new StringRowKey(Bytes.toString(row));
    }
}

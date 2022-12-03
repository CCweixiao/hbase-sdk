package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.LongRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;

/**
 * @author leojie 2022/12/3 13:40
 */
public class ConvertLongRowKeyFunc extends ConvertRowKeyFunc<Long> {
    public ConvertLongRowKeyFunc() {
        super(ColumnType.LongType);
    }

    @Override
    public RowKey<Long> convert(String text) {
        return new LongRowKey(text);
    }
}

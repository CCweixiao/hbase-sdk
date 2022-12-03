package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.IntRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;

/**
 * @author leojie 2022/12/3 13:32
 */
public class ConvertIntRowKeyFunc extends ConvertRowKeyFunc<Integer> {

    public ConvertIntRowKeyFunc() {
        super(ColumnType.IntegerType);
    }

    @Override
    public RowKey<Integer> convert(String text) {
        return new IntRowKey(text);
    }


}

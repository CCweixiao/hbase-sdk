package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func.Md5RowKeyFunc;

/**
 * @author leojie 2022/12/3 12:53
 */
public class Md5RowKey extends BaseRowKey<String> {

    public Md5RowKey(String value) {
        super(value, new Md5RowKeyFunc());
    }
    @Override
    public ColumnType columnType() {
        return ColumnType.StringType;
    }
}

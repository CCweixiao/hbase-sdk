package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.Md5RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;

/**
 * @author leojie 2022/12/3 13:48
 */
public class Md5RowKeyFunc implements RowKeyFunc<String>{
    @Override
    public RowKey<String> convert(String text) {
        return new Md5RowKey(text);
    }

    @Override
    public String reverse(RowKey<String> rowKey) {
        return rowKey.extractValue();
    }

    @Override
    public String funcName() {
        return "md5";
    }

    @Override
    public String desc() {
        return "MD5 encryption of row key, example md5 ( 'abcdefg' )";
    }
}

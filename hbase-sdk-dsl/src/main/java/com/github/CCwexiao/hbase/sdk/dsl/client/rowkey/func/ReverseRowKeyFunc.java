package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.Md5PrefixRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;

/**
 * @author leojie 2022/12/3 13:50
 */
public class ReverseRowKeyFunc implements RowKeyFunc<String>{
    @Override
    public RowKey<String> convert(String text) {
        return new Md5PrefixRowKey(text);
    }

    @Override
    public String reverse(RowKey<String> rowKey) {
        return rowKey.extractValue();
    }

    @Override
    public String funcName() {
        return "reverse";
    }

    @Override
    public String desc() {
        return "Reverse the row key, example reverse ( 'abcd' ) ";
    }
}

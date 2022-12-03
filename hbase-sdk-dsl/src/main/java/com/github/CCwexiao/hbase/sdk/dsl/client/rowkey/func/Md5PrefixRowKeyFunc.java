package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.Md5PrefixRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;

/**
 * @author leojie 2022/12/3 13:42
 */
public class Md5PrefixRowKeyFunc implements RowKeyFunc<String> {
    private final int prefixLength;
    private final String prefixContactChar;

    public Md5PrefixRowKeyFunc(int prefixLength, String prefixContactChar) {
        this.prefixLength = prefixLength;
        this.prefixContactChar = prefixContactChar;
    }

    public Md5PrefixRowKeyFunc(int prefixLength) {
        this(prefixLength, "|");
    }

    public Md5PrefixRowKeyFunc() {
        this(4, "|");
    }

    @Override
    public RowKey<String> convert(String text) {
        return new Md5PrefixRowKey(text, this.prefixLength, this.prefixContactChar);
    }

    @Override
    public String reverse(RowKey<String> rowKey) {
        return rowKey.extractValue();
    }

    @Override
    public String funcName() {
        return "md5_prefix";
    }

    @Override
    public String desc() {
        return "Prefix the fixed bits after the rowkey splicing MD5. example: md5_prefix ( 'abcdef' ) " +
                ", md5_prefix ( abcdef , 4), md5_prefix ( abcdef , 4, '|')";
    }
}

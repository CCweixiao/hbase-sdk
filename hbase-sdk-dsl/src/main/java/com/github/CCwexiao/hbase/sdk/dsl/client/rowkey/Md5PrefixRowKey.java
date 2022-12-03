package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author leojie 2022/12/3 13:01
 */
public class Md5PrefixRowKey extends StringRowKey{
    private final int prefixLength;
    private final String prefixContactChar;

    public Md5PrefixRowKey(String value, int prefixLength, String prefixContactChar) {
        super(value);
        this.prefixLength = prefixLength;
        this.prefixContactChar = prefixContactChar;
        String prefix = DigestUtil.md5Hex(value).substring(0, prefixLength);
        this.value = prefix.concat(prefixContactChar).concat(value);
    }

    public Md5PrefixRowKey(String value, int prefixLength) {
        this(value, prefixLength, "|");
    }

    public Md5PrefixRowKey(String value) {
        this(value, 4, "|");
    }

    public int getPrefixLength() {
        return prefixLength;
    }

    public String getPrefixContactChar() {
        return prefixContactChar;
    }
}

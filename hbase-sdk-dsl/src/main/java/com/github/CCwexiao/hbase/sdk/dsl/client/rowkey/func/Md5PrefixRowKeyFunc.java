package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import cn.hutool.crypto.digest.DigestUtil;
import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.BaseRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;

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
    public String evalFuncReturnRowValue(BaseRowKey<String> rowKey) {
        String oriValue = rowKey.getOriValue();
        MyAssert.checkArgument(StringUtil.isNotBlank(oriValue), "The value of row is not empty.");
        String prefix = DigestUtil.md5Hex(oriValue).substring(0, prefixLength);
        return prefix.concat(prefixContactChar).concat(oriValue);
    }

    @Override
    public String evalFuncReturnRowValue(HBaseColumn row, String value) {
        MyAssert.checkArgument(StringUtil.isNotBlank(value), "The value of row is not empty.");
        String prefix = DigestUtil.md5Hex(value).substring(0, prefixLength);
        return prefix.concat(prefixContactChar).concat(value);
    }

    @Override
    public String showFuncName() {
        return "md5_prefix";
    }

    @Override
    public String showDesc() {
        return "Prefix the fixed bits after the rowkey splicing MD5. example: md5_prefix ( 'abcdef' ) " +
                ", md5_prefix ( abcdef , 4), md5_prefix ( abcdef , 4, '|')";
    }
}

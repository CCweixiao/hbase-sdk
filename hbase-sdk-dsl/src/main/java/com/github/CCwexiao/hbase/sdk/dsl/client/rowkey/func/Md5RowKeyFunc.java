package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.func;

import com.github.CCweixiao.hbase.sdk.common.lang.MyAssert;
import com.github.CCweixiao.hbase.sdk.common.util.DigestUtil;
import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.BaseRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseColumn;

/**
 * @author leojie 2022/12/3 13:48
 */
public class Md5RowKeyFunc implements RowKeyFunc<String> {
    @Override
    public String evalFuncReturnRowValue(BaseRowKey<String> rowKey) {
        String oriValue = rowKey.getOriValue();
        MyAssert.checkArgument(StringUtil.isNotBlank(oriValue), "The value of row is not empty.");
        return DigestUtil.md5Hex(oriValue);
    }

    @Override
    public String evalFuncReturnRowValue(HBaseColumn row, String value) {
        MyAssert.checkArgument(StringUtil.isNotBlank(value), "The value of row is not empty.");
        return DigestUtil.md5Hex(value);
    }

    @Override
    public String showFuncName() {
        return "md5";
    }

    @Override
    public String showDesc() {
        return "MD5 encryption of row key, example md5 ( 'abcdefg' )";
    }
}

package com.github.CCweixiao.client.rowkeytextfunc;

import com.github.CCwexiao.dsl.util.EncodingUtil;
import com.github.CCwexiao.dsl.client.RowKey;
import com.github.CCwexiao.dsl.client.rowkey.BytesRowKey;
import com.github.CCwexiao.dsl.client.rowkeytextfunc.RowKeyTextFunc;

/**
 * @author leojie 2020/11/28 12:50 下午
 */
public class HexBytesRowKeyTextFunc implements RowKeyTextFunc {
    @Override
    public RowKey func(String hexStr) {
        return new BytesRowKey(EncodingUtil.parseBytesFromHexString(hexStr));
    }

    @Override
    public String funcName() {
        return "hexkey";
    }

    @Override
    public String desc() {
        return null;
    }
}

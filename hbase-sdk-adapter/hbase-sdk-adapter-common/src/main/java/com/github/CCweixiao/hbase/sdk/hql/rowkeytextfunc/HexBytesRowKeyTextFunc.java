package com.github.CCweixiao.hbase.sdk.hql.rowkeytextfunc;

import com.github.CCweixiao.hbase.sdk.common.util.EncodingUtil;
import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.BytesRowKey;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkeytextfunc.RowKeyTextFunc;

/**
 * @author leojie 2020/11/28 12:50 下午
 */
public class HexBytesRowKeyTextFunc implements RowKeyTextFunc {
    @Override
    public RowKey func(String hexStr) {
        return new BytesRowKey(EncodingUtil.parseBytesFromHexString(hexStr));
    }

    @Override
    public RowKey convert(byte[] row) {
        return new BytesRowKey(row);
    }

    @Override
    public Object reverse(RowKey rowKey) {
        byte[] bytes = rowKey.toBytes();
        return EncodingUtil.toHexString(bytes);    }

    @Override
    public String funcName() {
        return RowKeyFunctionNameEnum.HEX_KEY.getFunctionName();
    }

    @Override
    public String desc() {
        return "use hex string as rowKey.";
    }

}

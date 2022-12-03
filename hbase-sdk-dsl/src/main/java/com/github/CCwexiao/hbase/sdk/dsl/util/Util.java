package com.github.CCwexiao.hbase.sdk.dsl.util;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;
import com.github.CCwexiao.hbase.sdk.dsl.client.rowkey.RowKey;

/**
 * @author leojie 2022/11/13 21:18
 */
public class Util {
    public static void checkRowKey(RowKey rowKey) {
        ObjUtil.checkIsNull(rowKey);

        if (rowKey.toBytes() == null) {
            throw new HBaseOperationsException("row key bytes is null. rowKey = " + rowKey);
        }
    }
}

package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCwexiao.hbase.sdk.dsl.client.RowKey;

/**
 * @author leojie 2020/11/28 12:01 下午
 */
public class RowKeyUtil {
    /**
     * Used by scanners, etc when they want to start at the beginning of a
     * region
     */
    public final static RowKey START_ROW = new BytesRowKey(new byte[0]);
    /**
     * Last row in a table.
     */
    public final static RowKey END_ROW   = new BytesRowKey(new byte[0]);

}

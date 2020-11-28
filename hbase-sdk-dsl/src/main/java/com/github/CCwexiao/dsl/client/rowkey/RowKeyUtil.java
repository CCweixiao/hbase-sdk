package com.github.CCwexiao.dsl.client.rowkey;

import com.github.CCwexiao.dsl.client.RowKey;


/**
 * @author leojie 2020/11/28 12:01 下午
 */
public class RowKeyUtil {
    /**
     * Used by scanners, etc when they want to start at the beginning of a
     * region
     */
    public static RowKey START_ROW = new BytesRowKey(new byte[0]);
    /**
     * Last row in a table.
     */
    public static RowKey END_ROW   = new BytesRowKey(new byte[0]);

}

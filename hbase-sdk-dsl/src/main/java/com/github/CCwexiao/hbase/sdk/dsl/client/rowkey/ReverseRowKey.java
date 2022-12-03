package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import com.github.CCweixiao.hbase.sdk.common.util.StringUtil;

/**
 * @author leojie 2022/12/3 12:57
 */
public class ReverseRowKey extends StringRowKey {

    public ReverseRowKey(String value) {
        super(value);
        this.value = StringUtil.reverse(value);
    }
}

package com.github.CCwexiao.hbase.sdk.dsl.client.rowkey;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author leojie 2022/12/3 12:53
 */
public class Md5RowKey extends StringRowKey {

    public Md5RowKey(String value) {
        super(value);
        this.value = DigestUtil.md5Hex(value);
    }
}

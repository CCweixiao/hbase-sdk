package com.github.CCweixiao.hbase.sdk.common;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseSdkException;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseFuncNotSupportedException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseFuncNotSupportedException(String message) {
        super(message);
    }

    public HBaseFuncNotSupportedException(Throwable cause) {
        super(cause);
    }

    public HBaseFuncNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}

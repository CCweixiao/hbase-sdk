package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSdkTableIsNotDisabledException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSdkTableIsNotDisabledException(String message) {
        super(message);
    }

    public HBaseSdkTableIsNotDisabledException(Throwable cause) {
        super(cause);
    }

    public HBaseSdkTableIsNotDisabledException(String message, Throwable cause) {
        super(message, cause);
    }
}

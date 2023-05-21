package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSdkTableIsNotExistsException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSdkTableIsNotExistsException(String message) {
        super(message);
    }

    public HBaseSdkTableIsNotExistsException(Throwable cause) {
        super(cause);
    }

    public HBaseSdkTableIsNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

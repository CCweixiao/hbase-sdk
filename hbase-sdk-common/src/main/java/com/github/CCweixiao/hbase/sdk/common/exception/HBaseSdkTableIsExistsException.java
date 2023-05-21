package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSdkTableIsExistsException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSdkTableIsExistsException(String message) {
        super(message);
    }

    public HBaseSdkTableIsExistsException(Throwable cause) {
        super(cause);
    }

    public HBaseSdkTableIsExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

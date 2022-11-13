package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2021/2/9 10:50 下午
 */
public class HBaseSdkUnsupportedAuthTypeException extends HBaseSdkException {

    private static final long serialVersionUID = 1L;

    public HBaseSdkUnsupportedAuthTypeException(String message) {
        super(message);
    }

    public HBaseSdkUnsupportedAuthTypeException(Throwable cause) {
        super(cause);
    }

    public HBaseSdkUnsupportedAuthTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}

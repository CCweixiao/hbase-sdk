package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leo.jie (leojie1314@gmail.com)
 */
public class HBaseSdkException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HBaseSdkException(String message) {
        super(message);
    }

    public HBaseSdkException(Throwable cause) {
        super(cause);
    }

    public HBaseSdkException(String message, Throwable cause) {
        super(message, cause);
    }
}

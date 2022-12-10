package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2021/2/9 10:50 下午
 */
public class HBaseSdkUnsupportedFunctionException extends HBaseSdkException {

    private static final long serialVersionUID = 1L;

    public HBaseSdkUnsupportedFunctionException(String message) {
        super(message);
    }

    public HBaseSdkUnsupportedFunctionException(Throwable cause) {
        super(cause);
    }

    public HBaseSdkUnsupportedFunctionException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class BaseRetryTemplateHandleException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public BaseRetryTemplateHandleException(String message) {
        super(message);
    }

    public BaseRetryTemplateHandleException(Throwable cause) {
        super(cause);
    }

    public BaseRetryTemplateHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}

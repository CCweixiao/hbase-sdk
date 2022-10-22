package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2021/2/9 10:50 下午
 */
public class HBaseSdkConnectionException extends HBaseSdkException {

    private static final long serialVersionUID = -1226550815682955571L;

    public HBaseSdkConnectionException(String message) {
        super(message);
    }

    public HBaseSdkConnectionException(Throwable cause) {
        super(cause);
    }

    public HBaseSdkConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}

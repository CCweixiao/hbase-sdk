package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseColumnTypeCastException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseColumnTypeCastException(String message) {
        super(message);
    }

    public HBaseColumnTypeCastException(Throwable cause) {
        super(cause);
    }

    public HBaseColumnTypeCastException(String message, Throwable cause) {
        super(message, cause);
    }
}

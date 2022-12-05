package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSqlExecuteException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSqlExecuteException(String message) {
        super(message);
    }

    public HBaseSqlExecuteException(Throwable cause) {
        super(cause);
    }

    public HBaseSqlExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
}

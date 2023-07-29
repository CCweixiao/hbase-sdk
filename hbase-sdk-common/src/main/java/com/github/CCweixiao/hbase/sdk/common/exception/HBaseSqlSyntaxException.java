package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSqlSyntaxException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSqlSyntaxException(String message) {
        super(message);
    }

    public HBaseSqlSyntaxException(Throwable cause) {
        super(cause);
    }

    public HBaseSqlSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
}

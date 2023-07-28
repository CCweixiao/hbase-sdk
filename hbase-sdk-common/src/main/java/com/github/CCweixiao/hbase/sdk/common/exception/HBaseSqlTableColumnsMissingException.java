package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSqlTableColumnsMissingException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSqlTableColumnsMissingException(String message) {
        super(message);
    }

    public HBaseSqlTableColumnsMissingException(Throwable cause) {
        super(cause);
    }

    public HBaseSqlTableColumnsMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}

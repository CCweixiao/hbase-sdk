package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSqlFamilyMissingException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSqlFamilyMissingException(String message) {
        super(message);
    }

    public HBaseSqlFamilyMissingException(Throwable cause) {
        super(cause);
    }

    public HBaseSqlFamilyMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}

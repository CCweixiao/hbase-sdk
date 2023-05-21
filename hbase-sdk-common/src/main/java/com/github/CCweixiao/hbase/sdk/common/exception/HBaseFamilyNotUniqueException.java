package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseFamilyNotUniqueException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseFamilyNotUniqueException(String message) {
        super(message);
    }

    public HBaseFamilyNotUniqueException(Throwable cause) {
        super(cause);
    }

    public HBaseFamilyNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseFamilyNotEmptyException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseFamilyNotEmptyException(String message) {
        super(message);
    }

    public HBaseFamilyNotEmptyException(Throwable cause) {
        super(cause);
    }

    public HBaseFamilyNotEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseFamilyNotFoundException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseFamilyNotFoundException(String message) {
        super(message);
    }

    public HBaseFamilyNotFoundException(Throwable cause) {
        super(cause);
    }

    public HBaseFamilyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

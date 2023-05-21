package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseFamilyHasExistsException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseFamilyHasExistsException(String message) {
        super(message);
    }

    public HBaseFamilyHasExistsException(Throwable cause) {
        super(cause);
    }

    public HBaseFamilyHasExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

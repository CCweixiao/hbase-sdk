package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSdkCryptoException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSdkCryptoException(String message) {
        super(message);
    }

    public HBaseSdkCryptoException(Throwable cause) {
        super(cause);
    }

    public HBaseSdkCryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}

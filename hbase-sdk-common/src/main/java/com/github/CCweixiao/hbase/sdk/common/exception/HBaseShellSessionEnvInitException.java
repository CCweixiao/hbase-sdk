package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseShellSessionEnvInitException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseShellSessionEnvInitException(String message) {
        super(message);
    }

    public HBaseShellSessionEnvInitException(Throwable cause) {
        super(cause);
    }

    public HBaseShellSessionEnvInitException(String message, Throwable cause) {
        super(message, cause);
    }
}

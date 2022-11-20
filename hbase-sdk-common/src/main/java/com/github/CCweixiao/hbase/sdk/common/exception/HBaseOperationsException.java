package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * <p>Exceptions for custom HBase database operations.</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class HBaseOperationsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HBaseOperationsException() {
        super();
    }

    public HBaseOperationsException(String message) {
        super(message);
    }

    public HBaseOperationsException(Throwable cause) {
        super(cause);
    }

    public HBaseOperationsException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

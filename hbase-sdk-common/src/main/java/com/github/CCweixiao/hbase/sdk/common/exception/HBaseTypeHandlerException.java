package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2021/2/9 10:50 下午
 */
public class HBaseTypeHandlerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HBaseTypeHandlerException() {
        super();
    }

    public HBaseTypeHandlerException(String message) {
        super(message);
    }

    public HBaseTypeHandlerException(Throwable cause) {
        super(cause);
    }

    public HBaseTypeHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

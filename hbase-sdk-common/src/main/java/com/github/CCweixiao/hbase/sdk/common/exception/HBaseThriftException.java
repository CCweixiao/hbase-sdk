package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2020/12/27 2:39 下午
 */
public class HBaseThriftException extends RuntimeException{


    private static final long serialVersionUID = -8868799879462992551L;

    public HBaseThriftException(String message) {
        super(message);
    }

    public HBaseThriftException(Throwable e) {
        super(e);
    }

    public HBaseThriftException(String message, Throwable cause) {
        super(message, cause);
    }
}

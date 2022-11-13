package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2020/12/27 2:42 下午
 */
public class HBaseThriftExhaustedPoolException extends HBaseThriftException {


    private static final long serialVersionUID = 1L;

    public HBaseThriftExhaustedPoolException(String message) {
        super(message);
    }

    public HBaseThriftExhaustedPoolException(Throwable e) {
        super(e);
    }

    public HBaseThriftExhaustedPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}

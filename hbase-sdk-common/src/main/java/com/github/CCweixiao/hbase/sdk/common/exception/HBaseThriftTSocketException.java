package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2020/12/27 2:42 下午
 */
public class HBaseThriftTSocketException extends HBaseThriftException {

    private static final long serialVersionUID = 4550472462147193078L;

    public HBaseThriftTSocketException(String message) {
        super(message);
    }

    public HBaseThriftTSocketException(Throwable e) {
        super(e);
    }

    public HBaseThriftTSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}

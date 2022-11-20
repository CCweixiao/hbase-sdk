package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/11/20 18:23
 */
public class HBaseMetaDataException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public HBaseMetaDataException() {
        super();
    }

    public HBaseMetaDataException(String message) {
        super(message);
    }

    public HBaseMetaDataException(Throwable cause) {
        super(cause);
    }

    public HBaseMetaDataException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

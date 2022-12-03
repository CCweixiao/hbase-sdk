package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSqlAnalysisException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSqlAnalysisException(String message) {
        super(message);
    }

    public HBaseSqlAnalysisException(Throwable cause) {
        super(cause);
    }

    public HBaseSqlAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}

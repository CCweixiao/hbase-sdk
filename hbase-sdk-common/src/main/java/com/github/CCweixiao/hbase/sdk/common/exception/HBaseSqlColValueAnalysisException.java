package com.github.CCweixiao.hbase.sdk.common.exception;

/**
 * @author leojie 2022/12/3 00:32
 */
public class HBaseSqlColValueAnalysisException extends HBaseSdkException {
    private static final long serialVersionUID = 1L;

    public HBaseSqlColValueAnalysisException(String message) {
        super(message);
    }

    public HBaseSqlColValueAnalysisException(Throwable cause) {
        super(cause);
    }

    public HBaseSqlColValueAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}

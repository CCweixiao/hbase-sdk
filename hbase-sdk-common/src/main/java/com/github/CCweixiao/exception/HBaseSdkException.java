package com.github.CCweixiao.exception;

/**
 * @author leo.jie (leojie1314@gmail.com)
 */
public class HBaseSdkException extends RuntimeException {

    private static final long serialVersionUID = 8492970003562011729L;

    public HBaseSdkException(String message) {
        super(message);
    }

    public HBaseSdkException(Throwable cause) {
        super(cause);
    }

    public HBaseSdkException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.github.CCweixiao.exception;

/**
 * <p>自定义HBase数据库操作的异常</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
public class HBaseOperationsException extends RuntimeException {
    private static final long serialVersionUID = 0L;

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

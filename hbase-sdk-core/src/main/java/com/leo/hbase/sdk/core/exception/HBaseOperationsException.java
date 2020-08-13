package com.leo.hbase.sdk.core.exception;

/**
 * <p>自定义HBase数据库操作的异常</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/5/13 10:23 上午
 * @since 1.0
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
        StringBuffer buffer = new StringBuffer();
        buffer.append("My HBase Exception - ")
                .append(super.toString());
        return buffer.toString();
    }
}

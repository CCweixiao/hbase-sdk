package com.github.CCweixiao.hbase.sdk.common.retry;

/**
 * @author leojie 2023/7/5 16:56
 */
public interface RetryExecutor<T> {
    default void beforeExecute() throws Exception {}
    T execute() throws Exception;
    default void afterExecute() throws Exception {}
}

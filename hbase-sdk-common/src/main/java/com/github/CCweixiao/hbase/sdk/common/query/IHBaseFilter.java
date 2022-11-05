package com.github.CCweixiao.hbase.sdk.common.query;

/**
 * @author leojie 2022/11/5 10:32
 */
public interface IHBaseFilter {
    /**
     * 构建过滤器的接口
     * @return 过滤器
     * @param <F> 范型过滤器
     */
    <F> F customFilter();
}

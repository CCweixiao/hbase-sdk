package com.github.CCweixiao.hbase.sdk.common.query;

/**
 * @author leojie 2022/11/5 10:32
 */
public interface IHBaseFilter<F> {
    /**
     * Build a method interface for filtering HBase data queries
     * @return Filter
     */
     F customFilter();
}

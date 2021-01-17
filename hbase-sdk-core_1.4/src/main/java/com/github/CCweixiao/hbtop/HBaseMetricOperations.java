package com.github.CCweixiao.hbtop;

import com.github.CCweixiao.hbtop.field.Field;
import com.github.CCweixiao.hbtop.mode.Mode;

import java.util.List;

/**
 * HBase 指标操作
 *
 * @author leojie 2021/1/16 8:29 下午
 */
public interface HBaseMetricOperations {
    /**
     * 刷新汇总指标
     *
     * @return 汇总指标
     */
    Summary refreshSummary();

    /**
     * 获取集群的统计指标
     *
     * @param currentMode      当前模式
     * @param filters          过滤器
     * @param currentSortField 当前排序字段
     * @param ascendingSort    是否升序排列
     * @return 统计指标列表
     */
    List<Record> refreshRecords(Mode currentMode, List<RecordFilter> filters, Field currentSortField, boolean ascendingSort);

}

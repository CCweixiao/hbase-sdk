package com.github.CCweixiao.hbase.sdk.hbtop;

import com.github.CCweixiao.hbase.sdk.hbtop.field.Field;
import com.github.CCweixiao.hbase.sdk.hbtop.mode.Mode;
import com.github.CCweixiao.hbase.sdk.common.model.HBaseRegionRecord;
import com.github.CCweixiao.hbase.sdk.common.model.HBaseTableRecord;

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

    /**
     * 获取集群表的统计指标
     *
     * @param currentSortField 当前排序字段
     * @param ascendingSort    是否升序排列
     * @return 统计指标
     */
    List<HBaseTableRecord> refreshTableRecords(Field currentSortField, boolean ascendingSort);

    /**
     * 获取某一张表的统计指标
     *
     * @param fullTableName 全表名
     * @return 统计指标
     */
    HBaseTableRecord refreshTableRecord(String fullTableName);

    /**
     * 获取集群region的统计指标
     *
     * @param tableName        表名
     * @param currentSortField 当前排序字段
     * @param ascendingSort    是否升序排列
     * @return 统计指标
     */
    List<HBaseRegionRecord> refreshRegionRecords(String tableName, Field currentSortField, boolean ascendingSort);

}

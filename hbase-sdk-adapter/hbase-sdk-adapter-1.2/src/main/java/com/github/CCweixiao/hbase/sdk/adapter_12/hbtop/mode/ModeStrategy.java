package com.github.CCweixiao.hbase.sdk.adapter_12.hbtop.mode;

import com.github.CCweixiao.hbase.sdk.adapter_12.hbtop.field.Field;
import com.github.CCweixiao.hbase.sdk.adapter_12.hbtop.field.FieldInfo;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.List;

import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.classification.InterfaceAudience;
import com.github.CCweixiao.hbase.sdk.adapter_12.hbtop.Record;


/**
 * @author leojie 2021/1/16 9:14 下午
 */
@InterfaceAudience.Private
interface ModeStrategy {
    List<FieldInfo> getFieldInfos();

    Field getDefaultSortField();

    List<Record> getRecords(ClusterStatus clusterStatus);

    @Nullable
    DrillDownInfo drillDown(Record selectedRecord);
}

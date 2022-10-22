package com.github.CCweixiao.hbase.sdk.adapter_12.hbtop.mode;

import com.github.CCweixiao.hbase.sdk.adapter_12.hbtop.field.Field;
import com.github.CCweixiao.hbase.sdk.adapter_12.hbtop.field.FieldInfo;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.List;
import java.util.Objects;

import org.apache.hadoop.hbase.ClusterStatus;
import org.apache.hadoop.hbase.classification.InterfaceAudience;
import com.github.CCweixiao.hbase.sdk.adapter_12.hbtop.Record;

/**
 * @author leojie 2021/1/16 9:14 下午
 */
@InterfaceAudience.Private
public enum Mode {
  NAMESPACE("Namespace", "Record per Namespace", new NamespaceModeStrategy()),
  TABLE("Table", "Record per Table", new TableModeStrategy()),
  REGION("Region", "Record per Region", new RegionModeStrategy()),
  REGION_SERVER("RegionServer", "Record per RegionServer", new RegionServerModeStrategy());

  private final String header;
  private final String description;
  private final ModeStrategy modeStrategy;

  Mode(String header, String description, ModeStrategy modeStrategy) {
    this.header  = Objects.requireNonNull(header);
    this.description = Objects.requireNonNull(description);
    this.modeStrategy = Objects.requireNonNull(modeStrategy);
  }

  public String getHeader() {
    return header;
  }

  public String getDescription() {
    return description;
  }

  public List<Record> getRecords(ClusterStatus clusterStatus) {
    return modeStrategy.getRecords(clusterStatus);
  }

  public List<FieldInfo> getFieldInfos() {
    return modeStrategy.getFieldInfos();
  }

  public Field getDefaultSortField() {
    return modeStrategy.getDefaultSortField();
  }

  @Nullable
  public DrillDownInfo drillDown(Record currentRecord) {
    return modeStrategy.drillDown(currentRecord);
  }
}

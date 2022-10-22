package com.github.CCweixiao.hbase.sdk.adapter_14.hbtop.mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.hadoop.hbase.classification.InterfaceAudience;
import com.github.CCweixiao.hbase.sdk.adapter_14.hbtop.RecordFilter;

/**
 * @author leojie 2021/1/16 9:14 下午
 */
@InterfaceAudience.Private
public class DrillDownInfo {
  private final Mode nextMode;
  private final List<RecordFilter> initialFilters;

  public DrillDownInfo(Mode nextMode, List<RecordFilter> initialFilters) {
    this.nextMode = Objects.requireNonNull(nextMode);
    this.initialFilters = Collections.unmodifiableList(new ArrayList<>(initialFilters));
  }

  public Mode getNextMode() {
    return nextMode;
  }

  public List<RecordFilter> getInitialFilters() {
    return initialFilters;
  }
}

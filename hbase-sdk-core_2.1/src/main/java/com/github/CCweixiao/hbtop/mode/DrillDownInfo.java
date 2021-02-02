package com.github.CCweixiao.hbtop.mode;

import com.github.CCweixiao.hbtop.RecordFilter;
import org.apache.yetus.audience.InterfaceAudience;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

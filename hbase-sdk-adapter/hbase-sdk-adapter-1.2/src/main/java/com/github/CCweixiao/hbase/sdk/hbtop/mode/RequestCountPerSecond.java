package com.github.CCweixiao.hbase.sdk.hbtop.mode;

import org.apache.hadoop.hbase.classification.InterfaceAudience;

/**
 * @author leojie 2021/1/16 9:14 下午
 */
@InterfaceAudience.Private
public class RequestCountPerSecond {
  private long previousLastReportTimestamp;
  private long previousReadRequestCount;
  private long previousWriteRequestCount;
  private long readRequestCountPerSecond;
  private long writeRequestCountPerSecond;

  public void refresh(long lastReportTimestamp, long readRequestCount, long writeRequestCount) {
    if (previousLastReportTimestamp == 0) {
      previousLastReportTimestamp = lastReportTimestamp;
      previousReadRequestCount = readRequestCount;
      previousWriteRequestCount = writeRequestCount;
    } else if (previousLastReportTimestamp != lastReportTimestamp) {
      long delta = (lastReportTimestamp - previousLastReportTimestamp) / 1000;
      if (delta < 1) {
        delta = 1;
      }
      readRequestCountPerSecond = (readRequestCount - previousReadRequestCount) / delta;
      writeRequestCountPerSecond = (writeRequestCount - previousWriteRequestCount) / delta;

      previousLastReportTimestamp = lastReportTimestamp;
      previousReadRequestCount = readRequestCount;
      previousWriteRequestCount = writeRequestCount;
    }
  }

  public long getReadRequestCountPerSecond() {
    return readRequestCountPerSecond < 0 ? 0 : readRequestCountPerSecond;
  }

  public long getWriteRequestCountPerSecond() {
    return writeRequestCountPerSecond < 0 ? 0 : writeRequestCountPerSecond;
  }

  public long getRequestCountPerSecond() {
    return getReadRequestCountPerSecond() + getWriteRequestCountPerSecond();
  }
}

package com.github.CCweixiao.hbtop.mode;

import org.apache.yetus.audience.InterfaceAudience;


/**
 * @author leojie 2021/1/16 9:14 下午
 */
@InterfaceAudience.Private
public class RequestCountPerSecond {
  private long previousLastReportTimestamp;
  private long previousReadRequestCount;
  private long previousFilteredReadRequestCount;
  private long previousWriteRequestCount;
  private long readRequestCountPerSecond;
  private long filteredReadRequestCountPerSecond;
  private long writeRequestCountPerSecond;

  public void refresh(long lastReportTimestamp, long readRequestCount,
                      long filteredReadRequestCount, long writeRequestCount) {
    if (previousLastReportTimestamp == 0) {
      previousLastReportTimestamp = lastReportTimestamp;
      previousReadRequestCount = readRequestCount;
      previousFilteredReadRequestCount = filteredReadRequestCount;
      previousWriteRequestCount = writeRequestCount;
    } else if (previousLastReportTimestamp != lastReportTimestamp) {
      readRequestCountPerSecond = (readRequestCount - previousReadRequestCount) /
              ((lastReportTimestamp - previousLastReportTimestamp) / 1000);
      filteredReadRequestCountPerSecond =
              (filteredReadRequestCount - previousFilteredReadRequestCount) /
                      ((lastReportTimestamp - previousLastReportTimestamp) / 1000);
      writeRequestCountPerSecond = (writeRequestCount - previousWriteRequestCount) /
              ((lastReportTimestamp - previousLastReportTimestamp) / 1000);

      previousLastReportTimestamp = lastReportTimestamp;
      previousReadRequestCount = readRequestCount;
      previousFilteredReadRequestCount = filteredReadRequestCount;
      previousWriteRequestCount = writeRequestCount;
    }
  }

  public long getReadRequestCountPerSecond() {
    return readRequestCountPerSecond < 0 ? 0 : readRequestCountPerSecond;
  }

  public long getFilteredReadRequestCountPerSecond() {
    return filteredReadRequestCountPerSecond < 0 ? 0 : filteredReadRequestCountPerSecond;
  }

  public long getWriteRequestCountPerSecond() {
    return writeRequestCountPerSecond < 0 ? 0 : writeRequestCountPerSecond;
  }

  public long getRequestCountPerSecond() {
    return getReadRequestCountPerSecond() + getWriteRequestCountPerSecond();
  }
}

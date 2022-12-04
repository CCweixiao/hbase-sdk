package com.github.CCwexiao.hbase.sdk.dsl.client;

import com.github.CCweixiao.hbase.sdk.common.exception.HBaseOperationsException;
import com.github.CCweixiao.hbase.sdk.common.util.ObjUtil;

import java.util.Date;

/**
 * @author leojie 2020/11/28 9:58 上午
 */
public class QueryExtInfo {
    private boolean isMaxVersionSet;
    private int maxVersions;

    private boolean isTimeRangeSet;
    private long minStamp;
    private long maxStamp;

    private boolean isLimitSet;
    private long limit;

    public QueryExtInfo() {

    }

    public void setMaxVersions(int maxVersions) {
        if (maxVersions < 1) {
            throw new HBaseOperationsException("maxVersions is smaller than 1. maxVersions=" + maxVersions);
        }
        this.maxVersions = maxVersions;
        this.isMaxVersionSet = true;
    }

    public void setTimeStamp(long ts) {
        setTimeRange(ts, ts + 1);
    }

    public void setTimeRange(Date minStamp, Date maxStamp) {
        ObjUtil.checkIsNull(minStamp);
        ObjUtil.checkIsNull(maxStamp);
        setTimeRange(minStamp.getTime(), maxStamp.getTime());

    }

    public void setTimeRange(long minStamp, long maxStamp) {
        if (minStamp > maxStamp) {
            throw new HBaseOperationsException("maxStamp is smaller than minStamp. minStamp=" + minStamp
                    + " maxStamp=" + maxStamp);
        }
        this.minStamp = minStamp;
        this.maxStamp = maxStamp;
        this.isTimeRangeSet = true;
    }

    public void setLimit(long limit) {
        if (limit < 0) {
            throw new HBaseOperationsException("The value of limit is must be bigger than zero.");
        }

        this.limit = limit;
        this.isLimitSet = true;
    }


    public boolean isLimitSet() {
        return isLimitSet;
    }

    public long getLimit() {
        return limit;
    }

    public boolean isMaxVersionSet() {
        return isMaxVersionSet;
    }

    public int getMaxVersions() {
        return maxVersions;
    }

    public boolean isTimeRangeSet() {
        return isTimeRangeSet;
    }

    public long getMinStamp() {
        return minStamp;
    }

    public long getMaxStamp() {
        return maxStamp;
    }

    @Override
    public String toString() {
        return "QueryExtInfo{" +
                "isMaxVersionSet=" + isMaxVersionSet +
                ", maxVersions=" + maxVersions +
                ", isTimeRangeSet=" + isTimeRangeSet +
                ", minStamp=" + minStamp +
                ", maxStamp=" + maxStamp +
                ", isLimitSet=" + isLimitSet +
                ", limit=" + limit +
                '}';
    }
}

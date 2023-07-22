package com.github.CCweixiao.hbase.sdk.common.query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2023/7/20 11:09
 */
public class GetRowsParam {
    private final List<String> rowKeyList;
    private final String family;
    private final List<String> qualifiers;
    private final TimeRange timeRange;
    private final int versions;

    public GetRowsParam(Builder builder) {
        this.rowKeyList = builder.rowKeyList;
        this.family = builder.family;
        this.qualifiers = builder.qualifiers;
        this.timeRange = builder.timeRange;
        this.versions = builder.versions;
    }

    public static class TimeRange {
        private final long minTimestamp;
        private final long maxTimestamp;

        public TimeRange(long minTimestamp, long maxTimestamp) {
            if (minTimestamp >= 0L && maxTimestamp >= 0L) {
                if (maxTimestamp < minTimestamp) {
                    throw new IllegalArgumentException("maxTimestamp is smaller than minTimestamp");
                }
            } else {
                throw new IllegalArgumentException("Timestamp cannot be negative. minTimestamp:" + minTimestamp
                        + ", maxTimestamp:" + maxTimestamp);
            }

            this.minTimestamp = minTimestamp;
            this.maxTimestamp = maxTimestamp;
        }

        public long getMinTimestamp() {
            return minTimestamp;
        }

        public long getMaxTimestamp() {
            return maxTimestamp;
        }

        @Override
        public String toString() {
            return "[" +
                    "minTimestamp=" + minTimestamp +
                    ", maxTimestamp=" + maxTimestamp +
                    ']';
        }
    }

    public static class Builder {
        private List<String> rowKeyList;
        private String family;
        private List<String> qualifiers;
        private TimeRange timeRange;
        private int versions;

        private Builder() {
            this.versions = 1;
        }

        public Builder rowKeyList(List<String> rowKeyList) {
            this.rowKeyList = rowKeyList;
            return this;
        }

        public Builder appendRowKey(String rowKey) {
            if (this.rowKeyList == null) {
                this.rowKeyList = new ArrayList<>();
            }
            this.rowKeyList.add(rowKey);
            return this;
        }

        public Builder family(String family) {
            this.family = family;
            return this;
        }

        public Builder qualifiers(List<String> qualifiers) {
            this.qualifiers = qualifiers;
            return this;
        }

        public Builder qualifier(String qualifier) {
            if (this.qualifiers == null) {
                this.qualifiers = new ArrayList<>();
            }
            this.qualifiers.add(qualifier);
            return this;
        }

        public Builder withTimeRange(long min, long max) {
            this.timeRange = new TimeRange(min, max);
            return this;
        }

        public Builder withTimestamp(long ts) {
            if (ts < 0 || ts == Long.MAX_VALUE) {
                throw new IllegalArgumentException("invalid ts:" + ts);
            }
            this.timeRange = new TimeRange(ts, ts + 1);
            return this;
        }

        public Builder versions(int versions) {
            if (versions <= 0) {
                throw new IllegalArgumentException("versions must be positive");
            }
            this.versions = versions;
            return this;
        }

        public GetRowsParam build() {
            return new GetRowsParam(this);
        }
    }

    public static Builder of() {
        return new GetRowsParam.Builder();
    }

    public List<String> getRowKeyList() {
        return rowKeyList;
    }

    public String getFamily() {
        return family;
    }

    public List<String> getQualifiers() {
        return qualifiers;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public int getVersions() {
        return versions;
    }

    public boolean onlyFamily() {
        return FamilyQualifierUtil.familyNameOnly(this.getFamily(), this.getQualifiers());
    }

    public boolean familyWithQualifiers() {
        return FamilyQualifierUtil.familyAndColumnNames(this.getFamily(), this.getQualifiers());
    }

    @Override
    public String toString() {
        return "GetParams{" +
                "rowKeyList='" + rowKeyList + '\'' +
                ", familyName='" + family + '\'' +
                ", qualifiers=" + qualifiers +
                ", timeRange=" + timeRange +
                ", versions=" + versions +
                '}';
    }
}

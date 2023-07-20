package com.github.CCweixiao.hbase.sdk.common.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leojie 2023/7/20 20:09
 */
public class HBaseRowDataWithMultiVersions implements Serializable {
    public static final long serialVersionUID = 1L;

    private final String rowKey;
    private final Map<String, List<HBaseColData>> colDataWithMultiVersions;

    private HBaseRowDataWithMultiVersions(Builder builder) {
        this.rowKey = builder.rowKey;
        this.colDataWithMultiVersions = builder.colDataWithMultiVersions;
    }

    public static class Builder {
        private String rowKey;
        private Map<String, List<HBaseColData>> colDataWithMultiVersions;

        private Builder() {
        }

        public Builder row(String rowKey) {
            this.rowKey = rowKey;
            return this;
        }

        public Builder appendColData(String familyAndQualifierName, List<HBaseColData> colDataList) {
            if (this.colDataWithMultiVersions == null) {
                this.colDataWithMultiVersions = new HashMap<>();
            }
            this.colDataWithMultiVersions.put(familyAndQualifierName, colDataList);
            return this;
        }

        public Builder appendColData(String familyAndQualifierName, String value, long ts) {
            if (this.colDataWithMultiVersions == null) {
                this.colDataWithMultiVersions = new HashMap<>();
            }
            List<HBaseColData> colDataList = this.colDataWithMultiVersions.getOrDefault(familyAndQualifierName, new ArrayList<>());
            colDataList.add(new HBaseColData(value, ts));
            this.colDataWithMultiVersions.put(familyAndQualifierName, colDataList);
            return this;
        }

        public Builder empty() {
            this.colDataWithMultiVersions = new HashMap<>(0);
            return this;
        }
        
        public HBaseRowDataWithMultiVersions build() {
            return new HBaseRowDataWithMultiVersions(this);
        }
    }
    
    public static Builder of(String rowKey) {
        return new Builder().row(rowKey);
    }

    public static HBaseRowDataWithMultiVersions empty() {
        return HBaseRowDataWithMultiVersions.of("").empty().build();
    }

    public String getRowKey() {
        return rowKey;
    }

    public Map<String, List<HBaseColData>> getColDataWithMultiVersions() {
        return colDataWithMultiVersions;
    }
}

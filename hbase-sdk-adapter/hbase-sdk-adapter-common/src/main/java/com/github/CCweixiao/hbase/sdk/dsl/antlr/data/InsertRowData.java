package com.github.CCweixiao.hbase.sdk.dsl.antlr.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leojie 2023/7/28 19:54
 */
public class InsertRowData {
    private final byte[] rows;
    private final List<InsertColData> colDataList;

    private InsertRowData(Builder builder) {
        this.rows = builder.rows;
        this.colDataList = builder.colDataList;
    }

    public static class Builder {
        private final byte[] rows;
        private List<InsertColData> colDataList;

        private Builder(byte[] rows) {
            this.rows = rows;
            this.colDataList = new ArrayList<>();
        }

        public Builder addColDataList(List<InsertColData> colDataList) {
            this.colDataList = colDataList;
            return this;
        }

        public Builder addColData(InsertColData colData) {
            this.colDataList.add(colData);
            return this;
        }

        public Builder addColData(byte[] familyBytes, byte[] qualifierBytes, byte[] valueBytes, long ts) {
            return this.addColData(new InsertColData(familyBytes, qualifierBytes, valueBytes, ts));
        }

        public Builder addColData(byte[] familyBytes, byte[] qualifierBytes, byte[] valueBytes) {
            return this.addColData(new InsertColData(familyBytes, qualifierBytes, valueBytes, -1));
        }

        public InsertRowData build() {
            return new InsertRowData(this);
        }

    }

    public static Builder of (byte[] rows) {
        return new Builder(rows);
    }

    public byte[] getRows() {
        return rows;
    }

    public List<InsertColData> getColDataList() {
        return colDataList;
    }
}

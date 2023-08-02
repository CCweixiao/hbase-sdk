package com.github.CCwexiao.hbase.sdk.dsl.model;

/**
 * @author leojie 2022/12/3 11:13
 */
public class TableQueryProperties {
    private int scanCaching = 1000;
    private int scanBatch = 100;
    private boolean scanCacheBlocks = true;
    private int deleteBatch = 100;

    public int getScanCaching() {
        return scanCaching;
    }

    public void setScanCaching(int scanCaching) {
        this.scanCaching = scanCaching;
    }

    public int getScanBatch() {
        return scanBatch;
    }

    public void setScanBatch(int scanBatch) {
        this.scanBatch = scanBatch;
    }

    public boolean isScanCacheBlocks() {
        return scanCacheBlocks;
    }

    public void setScanCacheBlocks(boolean scanCacheBlocks) {
        this.scanCacheBlocks = scanCacheBlocks;
    }

    public int getDeleteBatch() {
        return deleteBatch;
    }

    public void setDeleteBatch(int deleteBatch) {
        this.deleteBatch = deleteBatch;
    }
}

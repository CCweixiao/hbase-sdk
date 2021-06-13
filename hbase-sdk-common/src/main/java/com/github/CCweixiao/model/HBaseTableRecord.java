package com.github.CCweixiao.model;

import java.io.Serializable;

/**
 * @author leojie 2021/6/13 10:36 上午
 */
public class HBaseTableRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private String namespaceName;
    private String tableName;
    private String storeFileSizeTag;
    private double storeFileSize;
    private String uncompressedStoreFileSizeTag;
    private double uncompressedStoreFileSize;
    private int numStoreFiles;
    private String memStoreSizeTag;
    private double memStoreSize;
    private int regionCount;

    public String getNamespaceName() {
        return namespaceName;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStoreFileSizeTag() {
        return storeFileSizeTag;
    }

    public void setStoreFileSizeTag(String storeFileSizeTag) {
        this.storeFileSizeTag = storeFileSizeTag;
    }

    public double getStoreFileSize() {
        return storeFileSize;
    }

    public void setStoreFileSize(double storeFileSize) {
        this.storeFileSize = storeFileSize;
    }

    public String getUncompressedStoreFileSizeTag() {
        return uncompressedStoreFileSizeTag;
    }

    public void setUncompressedStoreFileSizeTag(String uncompressedStoreFileSizeTag) {
        this.uncompressedStoreFileSizeTag = uncompressedStoreFileSizeTag;
    }

    public double getUncompressedStoreFileSize() {
        return uncompressedStoreFileSize;
    }

    public void setUncompressedStoreFileSize(double uncompressedStoreFileSize) {
        this.uncompressedStoreFileSize = uncompressedStoreFileSize;
    }

    public int getNumStoreFiles() {
        return numStoreFiles;
    }

    public void setNumStoreFiles(int numStoreFiles) {
        this.numStoreFiles = numStoreFiles;
    }

    public String getMemStoreSizeTag() {
        return memStoreSizeTag;
    }

    public void setMemStoreSizeTag(String memStoreSizeTag) {
        this.memStoreSizeTag = memStoreSizeTag;
    }

    public double getMemStoreSize() {
        return memStoreSize;
    }

    public void setMemStoreSize(double memStoreSize) {
        this.memStoreSize = memStoreSize;
    }

    public int getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(int regionCount) {
        this.regionCount = regionCount;
    }

    @Override
    public String toString() {
        return "HBaseTableRecord{" +
                "namespaceName='" + namespaceName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", storeFileSizeTag='" + storeFileSizeTag + '\'' +
                ", storeFileSize=" + storeFileSize +
                ", uncompressedStoreFileSizeTag='" + uncompressedStoreFileSizeTag + '\'' +
                ", uncompressedStoreFileSize=" + uncompressedStoreFileSize +
                ", numStoreFiles=" + numStoreFiles +
                ", memStoreSizeTag='" + memStoreSizeTag + '\'' +
                ", memStoreSize=" + memStoreSize +
                ", regionCount=" + regionCount +
                '}';
    }
}

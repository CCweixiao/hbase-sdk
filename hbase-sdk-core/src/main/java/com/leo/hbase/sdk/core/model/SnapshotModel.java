package com.leo.hbase.sdk.core.model;

/**
 * @author leojie 2020/8/15 5:36 下午
 */
public class SnapshotModel {
    private String tableName;
    private String snapshotName;
    private long createTimestamp;

    public SnapshotModel(){

    }

    public SnapshotModel(String tableName, String snapshotName, long createTimestamp) {
        this.tableName = tableName;
        this.snapshotName = snapshotName;
        this.createTimestamp = createTimestamp;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSnapshotName() {
        return snapshotName;
    }

    public void setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
    }

    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    @Override
    public String toString() {
        return "SnapshotModel{" +
                "tableName='" + tableName + '\'' +
                ", snapshotName='" + snapshotName + '\'' +
                ", createTimestamp=" + createTimestamp +
                '}';
    }
}

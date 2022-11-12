package com.github.CCweixiao.hbase.sdk.boot.example.pojo;


import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseRowKey;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseTable;

/**
 * <p>User2 Pojo</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@HBaseTable(tableName = "user2", defaultFamilyName = "info")
public class UserPojo2 {
    @HBaseRowKey
    private String rowKey;
    private boolean isVip;
    //private String address;

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

 /*   public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }*/
}

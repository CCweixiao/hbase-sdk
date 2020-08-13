package com.leo.hbase.sdk.starter.example.pojo;


import com.leo.hbase.sdk.core.annotation.HBaseRowKey;

/**
 * <p>People pojo</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/9 11:24 上午
 * @since 1.0
 */
public class PeoplePojo {
    @HBaseRowKey
    private String rowKey;

    private Integer userStatus;

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}

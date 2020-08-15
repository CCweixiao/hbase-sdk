package com.leo.hbase.sdk.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * HBase表创建时数据模型
 * @author leojie 2020/8/15 10:49 上午
 */
public class HTableModel {
    private String tableName;
    private List<HFamilyBuilder> hFamilies;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<HFamilyBuilder> gethFamilies() {
        return hFamilies;
    }

    public void sethFamilies(List<HFamilyBuilder> hFamilies) {
        this.hFamilies = hFamilies;
    }

    public void sethFamilies(HFamilyBuilder ... hFamilyBuilders){
        hFamilies = new ArrayList<>();
        hFamilies.addAll(Arrays.asList(hFamilyBuilders));
    }
}

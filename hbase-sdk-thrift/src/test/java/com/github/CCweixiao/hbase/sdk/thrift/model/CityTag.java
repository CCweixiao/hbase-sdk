package com.github.CCweixiao.hbase.sdk.thrift.model;

/**
 * @author leojie 2022/11/5 13:59
 */
public class CityTag {
    private String tagName;

    public CityTag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "CityTag{" +
                "tagName='" + tagName + '\'' +
                '}';
    }
}

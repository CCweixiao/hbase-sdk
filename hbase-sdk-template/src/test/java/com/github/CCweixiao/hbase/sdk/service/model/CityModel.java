package com.github.CCweixiao.hbase.sdk.service.model;

import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseColumn;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseRowKey;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseTable;

import java.util.List;

/**
 * @author leojie 2022/11/5 13:56
 */
@HBaseTable(namespaceName = "default", tableName = "t2", defaultFamilyName = "info")
public class CityModel {
    @HBaseRowKey
    private String cityId;
    private String cityName;
    private String cityAddress;

    @HBaseColumn(familyName = "detail")
    private Integer cityArea;
    @HBaseColumn(familyName = "detail", toUpperCase = true)
    private Integer totalPopulation;
    @HBaseColumn(familyName = "detail", columnName = "cityTagList")
    private List<CityTag> cityTagList;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public Integer getCityArea() {
        return cityArea;
    }

    public void setCityArea(Integer cityArea) {
        this.cityArea = cityArea;
    }

    public Integer getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(Integer totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public List<CityTag> getCityTagList() {
        return cityTagList;
    }

    public void setCityTagList(List<CityTag> cityTagList) {
        this.cityTagList = cityTagList;
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityAddress='" + cityAddress + '\'' +
                ", cityArea=" + cityArea +
                ", totalPopulation=" + totalPopulation +
                ", cityTagList=" + cityTagList +
                '}';
    }
}

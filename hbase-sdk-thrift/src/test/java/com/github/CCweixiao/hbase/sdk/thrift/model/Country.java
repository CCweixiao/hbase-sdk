package com.github.CCweixiao.hbase.sdk.thrift.model;

import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseColumn;

public class Country {
    @HBaseColumn(familyName = "info", columnName = "countryName")
    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}

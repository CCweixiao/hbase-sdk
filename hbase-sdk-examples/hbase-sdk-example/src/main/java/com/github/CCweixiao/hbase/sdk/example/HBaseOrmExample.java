package com.github.CCweixiao.hbase.sdk.example;

import com.github.CCweixiao.hbase.sdk.common.model.example.CityModel;
import com.github.CCweixiao.hbase.sdk.template.BaseHBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.template.HBaseTableTemplate;
import java.util.Optional;
import java.util.Properties;

/**
 * @author leojie 2023/7/9 13:50
 */
public class HBaseOrmExample {
    public static void main(String[] args) {
        Properties properties = HBaseServiceExample.getProperties();
        BaseHBaseTableTemplate tableTemplate = HBaseTableTemplate.of(properties);
        tableTemplate.save(CityModel.createDefaultCityModel());
        Optional<CityModel> result = tableTemplate.getRow("a10001", CityModel.class);
        System.out.println(result.orElse(new CityModel()));
    }


}

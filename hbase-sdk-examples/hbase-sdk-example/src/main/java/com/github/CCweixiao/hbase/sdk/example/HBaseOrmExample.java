package com.github.CCweixiao.hbase.sdk.example;

import com.github.CCweixiao.hbase.sdk.template.IHBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseTableTemplateImpl;

import java.util.Optional;
import java.util.Properties;

/**
 * @author leojie 2023/7/9 13:50
 */
public class HBaseOrmExample {
    public static void main(String[] args) {
        Properties properties = HBaseServiceExample.getProperties();
        IHBaseTableTemplate tableTemplate = new HBaseTableTemplateImpl.Builder().properties(properties).build();
        tableTemplate.save(CityModel.createDefaultCityModel());
        Optional<CityModel> result = tableTemplate.getRow("a10001", CityModel.class);
        System.out.println(result.orElse(new CityModel()));
    }


}

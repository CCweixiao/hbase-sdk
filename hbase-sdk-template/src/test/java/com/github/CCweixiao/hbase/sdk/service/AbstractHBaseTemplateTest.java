package com.github.CCweixiao.hbase.sdk.service;

import com.github.CCweixiao.hbase.sdk.common.model.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.common.model.HTableDesc;
import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.template.IHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseSqlTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseTableTemplate;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseAdminTemplateImpl;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseSqlTemplateImpl;
import com.github.CCweixiao.hbase.sdk.template.impl.HBaseTableTemplateImpl;
import com.github.CCweixiao.hbase.sdk.service.model.CityModel;
import com.github.CCweixiao.hbase.sdk.service.model.CityTag;
import com.github.CCwexiao.hbase.sdk.dsl.context.HBaseSqlContext;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author leojie 2022/11/4 20:58
 */
public abstract class AbstractHBaseTemplateTest {
    protected IHBaseAdminTemplate adminTemplate;
    protected IHBaseTableTemplate tableTemplate;
    protected IHBaseSqlTemplate sqlTemplate;

    protected void initIHBaseAdminTemplate() {
        adminTemplate = new HBaseAdminTemplateImpl.Builder()
                .properties(getProperties()).build();
    }

    protected void initIHBaseTableTemplate() {
        tableTemplate = new HBaseTableTemplateImpl.Builder()
                .properties(getProperties()).build();
    }

    protected void initIHBaseSqlTemplate() {
        HBaseTableSchema tableSchema = new HBaseTableSchema.Builder("test:test_sql")
                .addColumn("f1", "id")
                .addColumn("f1", "name")
                .addColumn("f1", "age", ColumnType.IntegerType)
                .addColumn("f2", "address")
                .addRow("row_key")
                .scanBatch(100)
                .scanCaching(1000)
                .deleteBatch(100)
                .scanCacheBlocks(false)
                .build();
        System.out.println("tableSchema.printSchema();");
        tableSchema.printSchema();
        HBaseSqlContext.registerTableSchema(tableSchema);
        sqlTemplate = new HBaseSqlTemplateImpl.Builder()
                .properties(getProperties()).build();
    }

    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hbase.zookeeper.quorum", "myhbase");
        properties.setProperty("hbase.zookeeper.property.clientPort", "2181");
        return properties;
    }

    protected void createTest2Table() {
        ColumnFamilyDesc familyDesc1 = new ColumnFamilyDesc.Builder()
                .familyName("info")
                .versions(3)
                .build();
        ColumnFamilyDesc familyDesc2 = new ColumnFamilyDesc.Builder()
                .familyName("detail")
                .timeToLive(10 * 60 * 60 * 1000)
                .build();
        HTableDesc tableDesc = new HTableDesc.Builder()
                .tableName("t2")
                .addColumnFamilyDesc(familyDesc1)
                .addColumnFamilyDesc(familyDesc2)
                .build();
        if (!adminTemplate.tableExists("t1")) {
            adminTemplate.createTable(tableDesc);
        }
    }

    protected CityModel createDefaultCityModel() {
        return cityModel("a10001", "北京", "北京市", 1000000, 40000000,
                Arrays.asList("首都", "旅游城市"));
    }

    protected List<CityModel> createDefaultCityModelList() {
        List<CityModel> cityModels = new ArrayList<>(4);
        cityModels.add(cityModel("a10001", "北京", "北京市", 1000000, 40000000,
                Arrays.asList("首都", "旅游城市")));
        cityModels.add(cityModel("a10002", "上海", "上海市", 1000000, 20000000,
                Arrays.asList("魔都", "旅游城市")));
        cityModels.add(cityModel("b20001", "广州", "广州市", 1000000, 40000000,
                Arrays.asList("沿海城市", "旅游城市")));
        cityModels.add(cityModel("b20002", "深圳", "深圳市", 1000000, 40000000,
                Arrays.asList("沿海城市", "发达地区")));
        return cityModels;
    }

    private CityModel cityModel(String cityId, String cityName, String cityAddress, int cityArea, int totalPopulation,
                                List<String> tagNameList) {
        CityModel cityModel = new CityModel();
        cityModel.setCityId(cityId);
        cityModel.setCityName(cityName);
        cityModel.setCityAddress(cityAddress);
        cityModel.setCityArea(cityArea);
        cityModel.setTotalPopulation(totalPopulation);
        cityModel.setCityTagList(tagNameList.stream().map(CityTag::new).collect(Collectors.toList()));
        return cityModel;
    }
}

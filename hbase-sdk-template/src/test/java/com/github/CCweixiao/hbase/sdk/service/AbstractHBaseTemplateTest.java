package com.github.CCweixiao.hbase.sdk.service;


import com.github.CCweixiao.hbase.sdk.common.type.ColumnType;
import com.github.CCweixiao.hbase.sdk.schema.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.schema.HTableDesc;
import com.github.CCweixiao.hbase.sdk.template.*;
import com.github.CCweixiao.hbase.sdk.service.model.CityModel;
import com.github.CCweixiao.hbase.sdk.service.model.CityTag;
import com.github.CCwexiao.hbase.sdk.dsl.model.HBaseTableSchema;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author leojie 2022/11/4 20:58
 */
public abstract class AbstractHBaseTemplateTest {
    protected BaseHBaseAdminTemplate adminTemplate;
    protected BaseHBaseTableTemplate tableTemplate;
    protected BaseHBaseSqlTemplate sqlTemplate;

    protected void initIHBaseAdminTemplate() {
        adminTemplate = HBaseAdminTemplate.of(getProperties());
    }

    protected void initIHBaseTableTemplate() {
        tableTemplate = HBaseTableTemplate.of(getProperties());
    }

    protected void initIHBaseSqlTemplate() {
        HBaseTableSchema tableSchema = HBaseTableSchema.of("test:test_sql")
                .addColumn("f1", "id")
                .addColumn("f1", "name")
                .addColumn("f1", "age", ColumnType.IntegerType)
                .addColumn("f1", "job")
                .addColumn("f1", "pay", ColumnType.DoubleType)
                .addColumn("f2", "address")
                .addColumn("f2", "commuter")
                .addRow("row_key")
                .scanBatch(100)
                .scanCaching(1000)
                .deleteBatch(100)
                .scanCacheBlocks(false)
                .build();
        System.out.println("tableSchema.printSchema();");
        tableSchema.printSchema();
        sqlTemplate = HBaseSqlTemplate.of(getProperties());
        sqlTemplate.registerTableSchema(tableSchema);
    }

    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("hbase.zookeeper.quorum", "myhbase");
        properties.setProperty("hbase.zookeeper.property.clientPort", "2181");
        return properties;
    }

    protected void createTest2Table() {
        ColumnFamilyDesc familyDesc1 = ColumnFamilyDesc.newBuilder()
                .name("info")
                .maxVersions(3)
                .build();
        ColumnFamilyDesc familyDesc2 = ColumnFamilyDesc.newBuilder()
                .name("detail")
                .timeToLive(10 * 60 * 60 * 1000)
                .build();
        HTableDesc tableDesc = HTableDesc.newBuilder()
                .name("t2")
                .addFamilyDesc(familyDesc1)
                .addFamilyDesc(familyDesc2)
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

    protected List<String> mockHql() {
        String[] prefix = new String[]{"a100", "b100", "c100", "d100", "e100", "f100", "g100", "h100", "i100", "j100"};
        Map<Integer, String> addressMap = new HashMap<>();
        addressMap.put(0, "BeiJing");
        addressMap.put(1, "ShangHai");
        addressMap.put(2, "TianJin");
        addressMap.put(3, "NanJing");
        addressMap.put(4, "BeiJing");
        addressMap.put(6, "ZhengZhou");
        addressMap.put(7, "WuHan");
        addressMap.put(8, "GuangZhou");
        addressMap.put(9, "GuangZhou");

        Map<Integer, String> commuterMap = new HashMap<>();
        commuterMap.put(0, "Car");
        commuterMap.put(1, "Car");
        commuterMap.put(2, "Bus");
        commuterMap.put(3, "Bus");
        commuterMap.put(4, "bike");
        commuterMap.put(6, "subway");
        commuterMap.put(7, "subway");
        commuterMap.put(8, "subway");
        commuterMap.put(9, "subway");


        Map<Integer, String> jobMap = new HashMap<>();
        jobMap.put(0, "Coding");
        jobMap.put(1, "Worker");
        jobMap.put(2, "清洁工");
        jobMap.put(3, "boss");
        jobMap.put(4, "教师");
        jobMap.put(5, "司机");
        jobMap.put(6, "CEO");
        jobMap.put(7, "运维");
        jobMap.put(9, "测试");
        List<String> hqls = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (String p : prefix) {
                String rowKey = p  + i;
                String hql = String.format("insert into test:test_sql ( row_key , f1:id , f1:name , f1:age , f1:job , f1:pay , f2:address , f2:commuter ) " +
                                "values ('%s', '%s' , '%s' , %s , '%s' , %.2f , '%s' , '%s' )", rowKey, rowKey,
                        "leo_" + p + "_" + i, 18 + i, jobMap.getOrDefault(i, ""), 20000 * new Random().nextInt(10) * 0.1 / 3 + 10000,
                        addressMap.get(i), commuterMap.getOrDefault(i, ""));
                hqls.add(hql);
            }
        }
        return hqls;
    }
}

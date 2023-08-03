package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.schema.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.schema.HTableDesc;
import com.github.CCweixiao.hbase.sdk.service.model.CityModel;
import com.github.CCweixiao.hbase.sdk.service.model.CityTag;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author leojie 2023/7/3 11:01
 */
public class BaseTestTemplate {
    public static final String TEST_TABLE = "test_table";
    protected static BaseHBaseAdminTemplate adminTemplate;
    protected static BaseHBaseTableTemplate tableTemplate;

    @BeforeClass
    public static void setUp() throws Exception {
        Properties c1 = new Properties();
        c1.setProperty("hbase.zookeeper.quorum", "myhbase");
        c1.setProperty("hbase.zookeeper.property.clientPort", "2181");
        // 开启hedged read
//        c1.setProperty("hbase.zookeeper.quorum.hedged.read", "myhbase");
//        c1.setProperty("hbase.zookeeper.property.clientPort.hedged.read", "2181");
//        c1.setProperty("hbase.client.hedged.read.open", "true");
//        c1.setProperty("hbase.client.hedged.read.timeout", "50");
//        c1.setProperty("hbase.client.hedged.thread.pool.size", "10");
        adminTemplate = HBaseAdminTemplate.of(c1);
        tableTemplate = HBaseTableTemplate.of(c1);
    }

    protected String getNamespaceName() {
        return "TestNamespace";
    }

    protected void createTestTable() {
        ColumnFamilyDesc cf = ColumnFamilyDesc.newBuilder()
                .name("info")
                .maxVersions(5)
                .build();
        ColumnFamilyDesc cf2 = ColumnFamilyDesc.newBuilder()
                .name("detail")
                .build();
        HTableDesc tableDesc = HTableDesc.newBuilder()
                .name(TEST_TABLE)
                .addFamilyDesc(cf)
                .build();
      adminTemplate.createTable(tableDesc);
    }

    protected void deleteTestTable() {
        adminTemplate.disableTable(TEST_TABLE, false);
        adminTemplate.deleteTable(TEST_TABLE, false);
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

    protected List<CityModel> createDefaultMultiVersionsCityModelList() {
        List<CityModel> cityModels = new ArrayList<>(4);
        cityModels.add(cityModel("a1000112", "北京", "北京市", 1000001, 40000001,
                Arrays.asList("首都", "旅游城市")));
        cityModels.add(cityModel("a1000112", "上海", "上海市", 1000002, 20000002,
                Arrays.asList("魔都", "旅游城市")));
        cityModels.add(cityModel("a1000112", "广州", "广州市", 1000003, 40000003,
                null));
        cityModels.add(cityModel("a1000112", "深圳", "深圳市", 1000004, 40000004,
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
        if (tagNameList != null) {
            cityModel.setCityTagList(tagNameList.stream().map(CityTag::new).collect(Collectors.toList()));
        }
        return cityModel;
    }

    @AfterClass
    public static void tearDown() throws Exception {

    }


}

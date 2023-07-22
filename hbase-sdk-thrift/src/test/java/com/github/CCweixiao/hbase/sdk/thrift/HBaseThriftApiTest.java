package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowsParam;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import com.github.CCweixiao.hbase.sdk.thrift.model.CityModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class HBaseThriftApiTest extends BaseHBaseThriftTemplateTest {

    @Test
    public void testSavePOJO() {
        CityModel cityModel = createDefaultCityModel();
        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
            hBaseThrift.save(cityModel);
        }
    }

    @Test
    public void testGetPOJO() {
        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
            Optional<CityModel> cityModelO = hBaseThrift.getRow(GetRowParam.of("a10001").build(), CityModel.class);
            Assert.assertTrue(cityModelO.isPresent());
            Assert.assertEquals("a10001", cityModelO.orElse(new CityModel()).getCityId());
            Assert.assertTrue(cityModelO.orElse(new CityModel()).getCityTagList().size() > 0);
            Optional<CityModel> cityModel1 = hBaseThrift.getRow(GetRowParam.of("a100011").build(), CityModel.class);
            Assert.assertFalse(cityModel1.isPresent());
        }
    }

    @Test
    public void testSaveBatchPOJO() {
        List<CityModel> defaultCityModelList = createDefaultCityModelList();
        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
           hBaseThrift.saveBatch(defaultCityModelList);
        }
    }

    @Test
    public void testGetRowsToPOJO() {
        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
            GetRowsParam getRowsParam1 = GetRowsParam.of().rowKeyList(Arrays.asList("a11001", "a12002"))
                    .family("info").build();

            List<CityModel> rows = hBaseThrift.getRows(getRowsParam1, CityModel.class);

            Assert.assertEquals(1, rows.size());
            GetRowsParam getRowsParam2 = GetRowsParam.of().rowKeyList(Arrays.asList("a11001", "a12002"))
                    .family("info")
                    .qualifiers(Arrays.asList("city_name", "city_address")).build();
            List<CityModel> rows2 = hBaseThrift.getRows(getRowsParam2, CityModel.class);
            Assert.assertEquals(2, rows2.size());
        }
    }

    @Test
    public void testScanToPOJO() {
        ScanParams params = ScanParams.of()
                .familyName("")
                .limit(2)
                .reversed(true)
                .build();
        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
            List<CityModel> models = hBaseThrift.scan(params, CityModel.class);
            Assert.assertEquals(2, models.size());
        }
    }

    @Test
    public void testSaveMap() {
        String t1 = "t1";
        String id = "100011";
        Map<String, Object> data = createDefaultDataMap();
        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
            hBaseThrift.save(t1, id, data);
        }
    }

    @Test
    public void testSaveBatchMap() {
        String t1 = "t1";
        Map<String, Map<String, Object>> data = createDefaultListDataMap();
        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
            hBaseThrift.saveBatch(t1, data);
        }
    }

    @Test
    public void testGetRowToMap() {
        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
            GetRowParam getRowParam = GetRowParam.of("100011").family("f1").build();
            HBaseRowData data = hBaseThrift.getRow("t1", getRowParam);
            Assert.assertEquals("18", data.getColDataContainer().get("f1:age").getValue());
        }
    }

    @Test
    public void testScanToMap() {
        ScanParams params = ScanParams.of()
                .familyName("")
                .limit(10)
                .reversed(true)
                .build();

        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
            List<HBaseRowData> data = hBaseThrift.scan("t1", params);
            Assert.assertEquals(9, data.size());
        }
    }

    @Test
    public void testDelete() {
        try (HBaseThrift hBaseThrift = thriftPool.getResource()) {
            hBaseThrift.delete("t1", "100011", "f1", "pay", "name");
        }
    }
}

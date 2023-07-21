package com.github.CCweixiao.hbase.sdk.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.IHBaseFilter;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import com.github.CCweixiao.hbase.sdk.service.model.CityModel;
import com.github.CCweixiao.hbase.sdk.service.model.CityTag;
import com.github.CCweixiao.hbase.sdk.service.util.MapBuilder;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author leojie 2022/11/4 20:52
 */
public class HBaseTableTemplateTest extends AbstractHBaseTemplateTest {

    @Before
    public void init() {
        super.initIHBaseAdminTemplate();
        super.initIHBaseTableTemplate();
        super.createTest2Table();
    }

    @Test
    public void testSaveJavaBean() {
        try {
            tableTemplate.save(createDefaultCityModel());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSaveBatchJavaBean() {
        try {
            tableTemplate.saveBatch(createDefaultCityModelList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetJavaBean() {
        Optional<CityModel> a10001 = tableTemplate.getRow("a10001", CityModel.class);
        Optional<CityModel> a10001F = tableTemplate.getRow("a10001", "info", CityModel.class);
        System.out.println(a10001.orElse(new CityModel()));
        System.out.println(a10001F);
    }

    @Test
    public void testScanWithLimit() {
        ScanParams scanQueryParamsBuilder = ScanParams.builder()
                .familyName("info")
                .columnNames(Arrays.asList("city_name", "city_address", "cityTagList"))
                .limit(2)
                .build();
        List<CityModel> cityModels = tableTemplate.scan(scanQueryParamsBuilder, CityModel.class);
        System.out.println(cityModels);
    }

    @Test
    public void testScanWithStartAndEndRow() {
        // 不包含endRow的数据
        ScanParams scanQueryParamsBuilder = ScanParams.builder()
                .startRow("a10001")
                .stopRow("a10002")
                .build();
        List<CityModel> cityModels = tableTemplate.scan(scanQueryParamsBuilder, CityModel.class);
        System.out.println(cityModels);
    }

    @Test
    public void testScanWithFilter() {
        ScanParams scanQueryParamsBuilder = ScanParams.builder()
                .filter(new IHBaseFilter<Filter>() {
                    @Override
                    public Filter customFilter() {
                        List<Filter> filters = new ArrayList<>(2);
                        // 筛选row key 大于b20001的数据
                        Filter rowFilter = new RowFilter(CompareFilter.CompareOp.GREATER,
                                new BinaryComparator("b20001".getBytes()));
                        // 筛选列前缀city_address的数据
                        ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter(Bytes.toBytes("city_address"));
                        // 筛选列值与深圳市相等的数据
                        ValueFilter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("深圳市")));
                        // 多过滤器，注意顺序
                        filters.add(rowFilter);
                        filters.add(columnPrefixFilter);
                        filters.add(valueFilter);
                        // 需所有条件全部通过
                        FilterList andFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);
                        // 满足其中一个条件即可
                        FilterList orFilterList = new FilterList(FilterList.Operator.MUST_PASS_ONE, filters);
                        return orFilterList;
                    }
                })
                .build();
        List<CityModel> cityModels = tableTemplate.scan(scanQueryParamsBuilder, CityModel.class);
        System.out.println(cityModels);
    }

    @Test
    public void testGetRowMapper() {
        CityModel cityModel = tableTemplate.getRow("t2", "a10001", new RowMapper<CityModel>() {
            @Override
            public <R> CityModel mapRow(R r, int rowNum) throws Exception {
                Result result = (Result) r;
                if (result == null) {
                    return null;
                }
                CityModel c = new CityModel();
                c.setCityId(Bytes.toString(result.getRow()));
                c.setCityName(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("city_name"))));
                c.setCityAddress(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("city_address"))));
                c.setCityArea(Bytes.toInt(result.getValue(Bytes.toBytes("detail"), Bytes.toBytes("city_area"))));
                c.setTotalPopulation(Bytes.toInt(result.getValue(Bytes.toBytes("detail"), Bytes.toBytes("TOTAL_POPULATION"))));
                String value = Bytes.toString(result.getValue(Bytes.toBytes("detail"), Bytes.toBytes("cityTagList")));
                JSONArray jsonArray = JSON.parseArray(value);
                List<CityTag> tags = new ArrayList<>(jsonArray.size());
                for (int i = 0; i < jsonArray.size(); i++) {
                    tags.add(jsonArray.getObject(i, CityTag.class));
                }
                c.setCityTagList(tags);
                return c;
            }
        }).orElse(new CityModel());
        System.out.println(cityModel);
    }

    @Test
    public void testSaveBatchMapData() {
        List<String> tags = new ArrayList<>();
        tags.add("boy");
        Map<String, Map<String, Object>> data = new HashMap<>(4);
        data.put("1001", new MapBuilder.Builder<String, Object>()
                .put("f1:name", "leo_1001")
                .put("f2:name", "leo_1001")
                .put("f2:address", "上海市浦东新区")
                .put("f3:age", 18)
                .put("f3:tags", tags)
                .build());
        data.put("1002", new MapBuilder.Builder<String, Object>()
                .put("f1:name", "leo_1002")
                .put("f2:name", "leo_1002")
                .put("f2:address", "上海市浦东新区")
                .put("f3:age", 18)
                .build());
        data.put("1101", new MapBuilder.Builder<String, Object>()
                .put("f1:name", "leo_1101")
                .put("f2:name", "leo_1101")
                .put("f2:address", "上海市浦东新区")
                .put("f3:age", 20)
                .build());
        data.put("1102", new MapBuilder.Builder<String, Object>()
                .put("f1:name", "leo_1102")
                .put("f2:name", "leo_1102")
                .put("f2:address", "上海市浦东新区")
                .put("f3:age", 18)
                .build());
        tableTemplate.saveBatch("t1", data);
    }

    @Test
    public void testGetRow() {
        HBaseRowData d1 = tableTemplate.getToRowData("t1", "1001");
        JSONArray objects = JSON.parseArray(d1.getColDataContainer().get("f3:tags").getValue());
        HBaseRowData d2 = tableTemplate.getToRowData("t1", "1002");
        List<String> rows = new ArrayList<>(2);
        rows.add("1001");
        rows.add("1002");

    }

    @Test
    public void testGetRows() {
    }

    @Test
    public void getRowToMapWithMultiVersions() {
        HBaseRowDataWithMultiVersions rowToMapWithMultiVersions = tableTemplate.getRowWithMultiVersions("leo_test", "1001",
                "f1", Arrays.asList("name", "age", "address"), 3);
        System.out.println(rowToMapWithMultiVersions);

    }
}

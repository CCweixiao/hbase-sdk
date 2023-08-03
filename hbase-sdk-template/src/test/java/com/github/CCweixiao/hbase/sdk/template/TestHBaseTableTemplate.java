package com.github.CCweixiao.hbase.sdk.template;

import com.github.CCweixiao.hbase.sdk.common.mapper.RowMapper;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowDataWithMultiVersions;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowParam;
import com.github.CCweixiao.hbase.sdk.common.query.GetRowsParam;
import com.github.CCweixiao.hbase.sdk.common.query.IHBaseFilter;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import com.github.CCweixiao.hbase.sdk.service.model.CityModel;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author leojie 2023/7/3 11:00
 */
public class TestHBaseTableTemplate extends BaseTestTemplate {

    @Test
    public void testSaveMap() {
        Map<String, Object> data = new HashMap<>(2);
        data.put("info:name", "leo");
        data.put("info:age", 18);
        tableTemplate.save(TEST_TABLE, "1001", data);
        GetRowParam getRowParam = GetRowParam.of("1001").build();
        HBaseRowData result = tableTemplate.getRow(TEST_TABLE, getRowParam);
        Assert.assertEquals(2, result.getColDataContainer().size());
    }

    @Test
    public void testSave() {
        CityModel cityModel = createDefaultCityModel();
        tableTemplate.save(cityModel);
        GetRowParam getRowParam = GetRowParam.of(cityModel.getCityId()).build();
        Optional<CityModel> cityModelRes = tableTemplate.getRow(getRowParam, CityModel.class);
        Assert.assertNotNull(cityModelRes);
    }

    @Test
    public void testSaveBatchMap() {
        Map<String, Object> data1 = new HashMap<>();
        data1.put("info:name", "leo1");
        data1.put("info:age", 17);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("info:name", "leo2");
        data2.put("info:age", 18);

        Map<String, Map<String, Object>> data = new HashMap<>(2);
        data.put("1001", data1);
        data.put("1002", data2);

        tableTemplate.saveBatch(TEST_TABLE, data);
        GetRowsParam getRowsParam = GetRowsParam.of().appendRowKey("1001")
                .appendRowKey("1002").build();
        List<HBaseRowData> rowDataList = tableTemplate.getRows(TEST_TABLE, getRowsParam);
        Assert.assertEquals(2, rowDataList.size());
    }

    @Test
    public void testSaveBatch() {
        List<CityModel> cityModelList = createDefaultCityModelList();
        tableTemplate.saveBatch(cityModelList);
        List<String> rowKeys = cityModelList.stream().map(CityModel::getCityId)
                .collect(Collectors.toList());

        GetRowsParam getRowsParam = GetRowsParam.of().rowKeyList(rowKeys).build();
        List<CityModel> rows = tableTemplate.getRows(getRowsParam, CityModel.class);
        Assert.assertEquals(rowKeys.size(), rows.size());
    }

    @Test
    public void testGetByRowMapper() {
        GetRowParam getRowParam = GetRowParam.of("1001").build();
        Map<String, String> data = tableTemplate.getRow(TEST_TABLE, getRowParam, new RowMapper<Map<String, String>>() {
            @Override
            public <R> Map<String, String> mapRow(R r, int rowNum) throws Exception {
                Result result = (Result) r;
                if (result == null) {
                    return new HashMap<>(0);
                }
                Map<String, String> data = new HashMap<>(2);
                for (Cell cell : result.listCells()) {
                    data.put(Bytes.toString(CellUtil.cloneFamily(cell)) + ":" +
                            Bytes.toString(CellUtil.cloneQualifier(cell)),
                            Bytes.toString(CellUtil.cloneValue(cell)));
                }
                return data;
            }
        }).orElse(new HashMap<>(0));
        Assert.assertEquals(2, data.size());
    }

    @Test
    public void testScanWithCustomFilter() {
        ScanParams scanParams = ScanParams.of()
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
                        return andFilterList;
                    }
                })
                .build();
        List<CityModel> cityModels = tableTemplate.scan(scanParams, CityModel.class);
        Assert.assertEquals(1, cityModels.size());
    }

    @Test
    public void testGetRowToList() {
        tableTemplate.delete(TEST_TABLE, "a1000112");
        List<CityModel> defaultCityModelList = createDefaultMultiVersionsCityModelList();
        for (CityModel cityModel : defaultCityModelList) {
            tableTemplate.save(cityModel);
        }
        GetRowParam getRowParam = GetRowParam.of("a1000112").versions(5).build();
        List<CityModel> cityModels = tableTemplate.getWithMultiVersions(getRowParam, CityModel.class);
        Assert.assertEquals(5, cityModels.size());
    }

    @Test
    public void testGetMultiVersions() throws InterruptedException {
        tableTemplate.delete(TEST_TABLE, "b1");
        Map<String, Object> data1 = new HashMap<>(3);
        data1.put("info:version", "v1");
        data1.put("info:age", 15);
        data1.put("info:name", "leo1");
        //Thread.sleep(2000);
        tableTemplate.save(TEST_TABLE, "b1", data1);

        Map<String, Object> data2 = new HashMap<>(3);
        data2.put("info:version", "v2");
        data2.put("info:age", 16);
        data2.put("info:name", "leo2");
        //Thread.sleep(2000);
        tableTemplate.save(TEST_TABLE, "b1", data2);

        Map<String, Object> data3 = new HashMap<>(3);
        data3.put("info:version", "v3");
        data3.put("info:age", 17);
        data3.put("info:name", "leo3");
        //Thread.sleep(2000);
        tableTemplate.save(TEST_TABLE, "b1", data3);

        Map<String, Object> data4 = new HashMap<>(3);
        data4.put("info:version", "v4");
        data4.put("info:age", 18);
        data4.put("info:name", "leo4");
        //Thread.sleep(2000);
        tableTemplate.save(TEST_TABLE, "b1", data4);

        Map<String, Object> data5 = new HashMap<>(3);
        data5.put("info:version", "v5");
        //Thread.sleep(2000);
        tableTemplate.save(TEST_TABLE, "b1", data5);

        GetRowParam getRowParam = GetRowParam.of("b1").versions(5).build();
        HBaseRowDataWithMultiVersions rowData = tableTemplate.getWithMultiVersions(TEST_TABLE, getRowParam);

        Assert.assertEquals(3, rowData.getColDataWithMultiVersions().size());
        Assert.assertEquals(4, rowData.getColDataWithMultiVersions().get("info:name").size());
    }

    @Test
    public void testScan() {
        ScanParams scanParams = ScanParams.of()
                .startRow("1001")
                .inclusiveStartRow(true)
                .stopRow("1002")
                .inclusiveStopRow(true)
                .caching(100)
                .build();
        List<HBaseRowData> rowDataList = tableTemplate.scan(TEST_TABLE, scanParams);
        Assert.assertEquals(2, rowDataList.size());
    }

    @Test
    public void testScanWithMultiVersions() {
        ScanParams scanParams = ScanParams.of().startRow("b1").stopRow("b1").versions(5).build();
        List<HBaseRowDataWithMultiVersions> hBaseRowDataWithMultiVersions =
                tableTemplate.scanWithMultiVersions(TEST_TABLE, scanParams);
        Assert.assertEquals(1, hBaseRowDataWithMultiVersions.size());
    }

    @Test
    public void testDelete() {
        tableTemplate.delete("t1", "r1", "f", "version");
    }
}

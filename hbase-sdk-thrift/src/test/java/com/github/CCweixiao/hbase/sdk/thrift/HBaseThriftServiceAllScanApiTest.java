package com.github.CCweixiao.hbase.sdk.thrift;

import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leojie 2020/12/30 10:17 下午
 */
public class HBaseThriftServiceAllScanApiTest {
    private HBaseThriftService hBaseThriftService;
    private static final String tableName = "LEO_USER";

    @Before
    public void init() {
        hBaseThriftService = HBaseThriftServiceHolder.getInstance();
        Map<String, Map<String, Object>> data = new HashMap<>(100);
        for (int i = 0; i < 100; i++) {
            String row = (i + 1) + "000" + (i + 2);
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("f:name", "leo" + i);
            tmp.put("f:address", "上海" + i);
            tmp.put("g:name", "yyf" + i);
            tmp.put("g:address", "北京" + i);
            data.put(row, tmp);
        }
        hBaseThriftService.saveBatch(tableName, data);
        System.out.println("数据初始化成功");
    }
    /*
        @Test
        public void testFindAllRowToMapList() {
            System.out.println(hBaseThriftService.findAllRowToMapList(tableName, 93));
        }

        @Test
        public void testFindAllRowWithFamilyToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithFamilyToMapList(tableName, "g", 1));
            System.out.println(hBaseThriftService.findAllRowWithFamilyToMapList(tableName, "f", 1));
        }

        @Test
        public void testFindAllRowWithFamilyAndQualifiersToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithFamilyAndQualifiersToMapList(tableName, "f",
                    Collections.singletonList("name"), 3));
        }

        @Test
        public void testFindAllRowWithStartRowToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithStartRowToMapList(tableName, "1100012", 3));
        }

        @Test
        public void testFindAllRowWithStartRowAndFamilyToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithStartRowAndFamilyToMapList(tableName, "1100012", "f", 3));
        }

        @Test
        public void testFindAllRowWithStartRowAndFamilyAndQualifiersToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithStartRowAndFamilyAndQualifiersToMapList(tableName,
                    "1100012", "g", Collections.singletonList("name"), 3));
        }

        @Test
        public void testFindAllRowWithStartAndStopRowToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithStartAndStopRowToMapList(tableName, "1100012", "1300015", 3));
        }

        @Test
        public void testFindAllRowWithStartAndStopRowAndFamilyToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithStartAndStopRowAndFamilyToMapList(tableName, "1100012", "1300015", "g", 3));
        }

        @Test
        public void testFindAllRowWithStartAndStopRowAndFamilyAndQualifiersToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithStartAndStopRowAndFamilyAndQualifiersToMapList(tableName,
                    "1100012", "1300015", "f", Collections.singletonList("name"), 3));
        }

        @Test
        public void testFindAllRowWithPrefixToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithPrefixToMapList(tableName, "11", 10));
        }

        @Test
        public void testFindAllRowWithPrefixAndFamilyToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithPrefixAndFamilyToMapList(tableName,
                    "11", "f", 10));
        }

        @Test
        public void testFindAllRowWithPrefixAndFamilyAndQualifiersToMapList() {
            System.out.println(hBaseThriftService.findAllRowWithPrefixAndFamilyAndQualifiersToMapList(tableName,
                    "12", "g", Collections.singletonList("name"), 10));
        }

        @Test
        public void testScan(){
            final List<Map<String, Map<String, String>>> mapList = hBaseThriftService.scan(tableName, "", "", "", "g"
                    , Collections.singletonList("name"), "", null, 10, 10, true, 10);
            System.out.println(mapList);
            final List<Map<String, Map<String, String>>> mapList2 = hBaseThriftService.scan(tableName, "", "", "", "g"
                    , Collections.singletonList("name"), "", null, 10, 10, false, 10);
            System.out.println(mapList2);
        }*/

}

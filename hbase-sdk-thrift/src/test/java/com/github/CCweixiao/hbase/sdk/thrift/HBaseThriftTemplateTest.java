package com.github.CCweixiao.hbase.sdk.thrift;

import com.github.CCweixiao.hbase.sdk.common.model.data.HBaseRowData;
import com.github.CCweixiao.hbase.sdk.common.query.IHBaseFilter;
import com.github.CCweixiao.hbase.sdk.common.query.ScanParams;
import com.github.CCweixiao.hbase.sdk.thrift.model.UserModel;
import org.junit.Test;

import java.util.*;

/**
 * @author leojie 2022/12/10 17:40
 */
public class HBaseThriftTemplateTest extends BaseHBaseThriftTemplateTest{
    @Test
    public void testSave() {
        UserModel userModel = new UserModel();
        userModel.setUserId("u10001");
        userModel.setNickName("不会飞的猪");
        userModel.setDetailAddress("上海浦东新区");
        userModel.setDetailPay(1000.5);
        thriftTemplate.save(userModel);
    }

    @Test
    public void testSaveBatch() {
        UserModel userModel = new UserModel();
        userModel.setUserId("u10001");
        userModel.setNickName("不会飞的猪1");
        userModel.setDetailAddress("上海浦东新区");
        userModel.setDetailPay(1500.5);

        UserModel userModel2 = new UserModel();
        userModel2.setUserId("u21000");
        userModel2.setNickName("不会飞的猪2");
        userModel2.setDetailAddress("上海浦东新区");
        userModel2.setDetailPay(2000.5);


        UserModel userModel3 = new UserModel();
        userModel3.setUserId("u22000");
        userModel3.setNickName("不会飞的猪3");
        userModel3.setDetailAddress("上海浦东新区");
        userModel3.setDetailPay(3000.5);

        List<UserModel> userModelList = new ArrayList<>(3);
        userModelList.add(userModel);
        userModelList.add(userModel2);
        userModelList.add(userModel3);

        thriftTemplate.saveBatch(userModelList);
    }

    @Test
    public void testSaveMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("info:nick_name", "会飞的猪");
        data.put("detail:DETAIL_PAY", 1234.5);
        data.put("detail:detailAddress", "上海黄浦区");
        thriftTemplate.save("test:t1", "u10002", data);
    }

    @Test
    public void testGetRow() {
        Optional<UserModel> userModel = thriftTemplate.getRow("u10001", UserModel.class);
        System.out.println(userModel);
        HBaseRowData data = thriftTemplate.getToRowData("test:t1", "u10002");
        System.out.println(data);
    }

    @Test
    public void testGetRows() {
        List<UserModel> userModels = thriftTemplate.getRows(Arrays.asList("u10001", "u21000", "u22000"), UserModel.class);
        System.out.println(userModels);
        List<UserModel> userModels2 = thriftTemplate.getRows(Arrays.asList("u10001", "u21000", "u22000"),
                "detail", UserModel.class);
        System.out.println(userModels2);
        List<UserModel> userModels3 = thriftTemplate.getRows(Arrays.asList("u10001", "u21000", "u22000"),
                "detail", Collections.singletonList("detailAddress"), UserModel.class);
        System.out.println(userModels3);
    }

    @Test
    public void testScanWithLimit() {
        ScanParams queryParams = ScanParams.builder()
                .limit(2)
                .build();
        // 全表扫描所有数据，并设置limit
        // Map 保存的数据，与模型类保存的数据，非string类型不能互通
        List<UserModel> userModelList = thriftTemplate.scan(queryParams, UserModel.class);
        System.out.println(userModelList);
    }

    @Test
    public void testScanWithStarAndRow() {
        // 根据起止row key扫描数据，不包含stopRow
        ScanParams queryParams = ScanParams.builder()
                .startRow("u10001")
                .stopRow("u21000")
                .build();

        List<UserModel> userModelList = thriftTemplate.scan(queryParams, UserModel.class);
        System.out.println(userModelList);
    }

    @Test
    public void testScanWithFilter() {
        // 设置过滤器扫描，列名为nick_前缀，且列对应值ascii码比：不会飞的猪2大的被筛选出
        ScanParams queryParams = ScanParams.builder()
                .filter(new IHBaseFilter<String>() {
                    @Override
                    public String customFilter() {
                        return "ColumnPrefixFilter('nick_') AND ValueFilter(>=, 'binary:不会飞的猪2')";
                    }
                })
                .build();

        List<UserModel> userModelList = thriftTemplate.scan(queryParams, UserModel.class);
        System.out.println(userModelList);
    }

    @Test
    public void testDelete() {
        // 删除 row u10001 列簇 detail qualifier detailAddress的数据
        thriftTemplate.delete("test:t1", "u10001", "detail", "detailAddress");
        // 删除 row u10001 列簇 detail 的数据
        thriftTemplate.delete("test:t1", "u10001", "detail");
        // 删除 row u10001 的整行数据
        thriftTemplate.delete("test:t1", "u10001");

    }
}

package com.github.CCweixiao;

import com.github.CCweixiao.entity.UserEntity;
import com.github.CCweixiao.util.JsonUtil;
import org.apache.hadoop.hbase.client.Scan;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * <p>HBaseTemplate Test</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/29 11:07 上午
 * @since 1.0
 */
public class HBaseTemplateTest {
    private HBaseTemplate hBaseTemplate;

    @Before
    public void initHBaseTemplate() {
        hBaseTemplate = new HBaseTemplate("localhost", "2181");
    }

    @Test
    public void testSaveUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("10001");
        userEntity.setUsername("leo");
        userEntity.setAge(18);
        userEntity.setVip(true);
        userEntity.setAddresses(Arrays.asList("北京", "上海"));
        userEntity.setCreateBy("admin");
        userEntity.setCreateTime(System.currentTimeMillis());

        Map<String, Object> contactInfo = new HashMap<>(3);
        contactInfo.put("email", "2326130720@qq.com");
        contactInfo.put("phone", "18739577988");
        contactInfo.put("address", "浦东新区");

        userEntity.setContactInfo(contactInfo);
        userEntity.setPay(100000.0d);

        try {
            hBaseTemplate.save(userEntity);
            System.out.println("用户数据保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet() {
        UserEntity userEntity = hBaseTemplate.getByRowKey("10001", UserEntity.class);
        System.out.println("用户数据获取成功！");
        System.out.println(userEntity);
    }

    @Test
    public void testToSave() {
        for (int i = 0; i < 10000; i++) {
            Map<String, Object> data = new HashMap<>();
            List<String> testData = Arrays.asList("d518c833-67e3-44a8-ad6f-c0670418391b:3", "7fdc51e2-d187-465d-8d96-c5abb00a687e:3", "a39fddee-8da7-4654-9af6-3796b5d99e78:1", "36e686c2-df51-4750-8718-a77fb9cb3011:3", "e28b5e30-7b1f-4a75-a3dd-d03443744bdc:1", "11ed7418-7a9e-4559-8faa-44b63888faa1:1", "045ecbeb-ec76-4f35-b863-338b9a136520:3", "7a96588c-f20d-40d9-85a5-9b83e2baa235:1", "7b01bfb8-aba8-4200-8051-6d63c2b8af0f:1", "136df375-37c7-4c40-9683-382b5d7e400b:1", "49eeaa63-12ef-444e-ac0c-9e3f15828dfc:3", "d33349fa-1494-4629-95e5-435bea6416f5:3", "0659a2bd-ee97-4782-b9f4-5da7d3ef7086:1", "75d1e9c2-ed30-43c9-9478-b11b9378ee6b:1", "0f54b3d8-5a26-480b-be07-bfc23171d36d:1", "e0bd3f98-2487-47ed-b243-57063552a03b:1", "797925d6-b429-45dc-9d3a-180cb85cbd9f:1", "b89ca8a1-bbd3-41a5-aafe-4a6f78927837:1", "a1251adf-2a98-4830-ad6e-b9450995e033:3", "acfb588d-be26-4bf4-b9d0-c6c5c8b7c9e4:3", "00bbf3c4-c4ba-427c-89c9-db1ad0bb327b:1", "53191bb7-9617-4916-af63-ed50bf304cea:3", "a0f96c3e-38cf-451f-ba2f-6b49442d0d7a:1", "6873f355-ad5d-4ae0-bca4-d52be3170daf:3", "6ebe84c1-6457-4a3f-9583-4cd03271b1e2:3", "a432abb3-85b2-48f3-9dd8-a6ad0accfa50:1", "3c9fe3bf-766d-4bba-849d-f32fb4490207:3", "f0317e48-9715-415f-b3ee-d8cbd6dad2fc:1", "82a86268-ed8e-4685-ab6e-1f6eb52bf998:1", "fe835a95-3577-4e54-b2be-35253a5b31e4:3", "87749881-bbc6-40d9-8bd9-a5dd737627e1:3", "198b8bee-5582-4226-a0a6-71750d18b91a:3", "8e168f25-f720-448a-a3db-e7f3c916cbde:1", "4191daa8-ea7d-4b56-b7e7-ac1880410aa4:3", "c561d884-6d51-4cc9-97f0-b3e448aebf84:3", "4cfd733b-45ac-471f-a09d-a8e8c30994d6:1", "07cdcb57-5122-4dfd-bcac-6678b1297ccb:1", "36bc0495-dd1c-4940-8690-3be05664021f:1", "75f9c64d-6896-466f-a8ca-c5cdb9352ac8:1", "768eef7e-4153-4623-8e73-432981e39af2:1", "5821a678-9b1d-44de-9d3f-1d8172999390:3", "f04f749b-c06a-45b1-9752-8daf9aa306e1:1", "01f766a1-9e4b-4050-ba33-8598249e6542:1", "2514dc5c-caa9-441f-bf12-843e8da36400:1", "36001dd0-14da-4016-9bda-b02d2af6a997:1", "971bfc60-781b-4ac4-9dca-6729708b589c:1", "165f8af6-b79e-453d-a5aa-33315c91357d:1", "a1118069-1368-492a-9517-0c43e627dcdf:3", "5a26db8a-c0e6-4920-aa26-7da2565c9765:1", "1bd7b94a-f11c-4763-8c27-08fea2b20afb:3", "97a05ada-1c10-4133-ae51-6dd91de587db:3", "399eff44-206f-4e91-9104-2195ba78da0b:1", "566dfc57-8045-4b7f-8e3f-445adc9b1296:3", "b484af23-9c74-4ff0-874a-dba54c5163d3:1", "6ff79c0f-c878-433b-bfc1-bd615e4b691e:1", "a0d37af8-97fa-4e20-8827-164a27d9c99a:1", "fa8ae778-7346-4adb-b583-db0b54851c29:3", "b4b45ccf-fc77-467b-9193-71e495540eda:1", "116a3988-7da7-476b-9438-98f8f066e114:3", "44d36dff-5e9a-48fc-96ab-7c0784a1f2dc:3", "05457c99-6801-4140-adff-0b8841effd2c:1", "7ec403c3-f8eb-49fc-8476-d67bc217745b:3", "fd1c12aa-f372-4728-acaa-a086ec3e5c70:1", "844d9066-6f9e-49f2-84ac-eb7b5f1b5939:1", "f10bf94b-7860-4c5a-9ec9-3520a19a58cf:3", "8987d58c-2591-4348-901e-77f4fe979ceb:3", "07295374-c2bb-4992-80a2-22071077a6d9:1", "7725ca07-41ec-4363-912a-48c854347d78:1", "13aaa27f-a3bb-4319-9b47-e2b88056147d:1", "482dbea5-c78f-4374-bfeb-f4327c1df8ef:3", "d06d2367-83e7-4480-b9ab-f7a9b4ec514b:3", "b5699cf0-d631-4cb2-8ae3-069e316baca1:3", "6a06a50a-8ebd-4a30-81d1-24e4aaf1fa46:1", "17181f7c-4a43-4935-b066-0a086ba53d22:1", "f5215e0e-57c9-4876-950e-2923929a1627:1", "84bb948a-7946-4431-a942-dceaf712717c:3", "3b7764ea-8c04-44d6-8dba-7ddbe121c5b0:1", "d0fe93da-fa3d-4def-a0ca-ee39c23fa24d:3", "20e474c9-fdfd-4e44-afbd-bc4f41e6aab5:3", "24c33137-1326-46a7-9e87-4d8f955c33b8:3", "72c00e50-c9e4-45a2-beac-d19245083b94:1", "f739edad-de84-4a41-be22-16310a1ac1f7:1", "a492b9a2-d938-41cb-b830-2c5eaeb36efa:1", "65d7b9d0-b1bd-428a-8880-8056e3077b1e:3", "42e35970-c737-4c21-9d6c-8b9d8d2280ea:3", "77a5644b-acf2-455f-80ee-98db4e80c778:1", "a5e03aa1-d3ca-470f-976d-2a659c1ac7cf:1", "4e9095ba-8bd5-4493-9aed-647b991ae6dc:1", "9c9dda61-7384-46da-9a12-8b49da718fe4:3", "00855ac1-7d4f-4bec-8211-c6f46ecd99ac:1", "df022272-803c-402f-8ca4-151e3b494509:3", "2f991c20-a2ae-41da-b314-881040131dc1:3", "4b4e28bb-4e44-4557-8097-9856f2a91ea2:1", "58c76fb2-d6be-40ce-9888-f51cfbc33f33:3", "a090c3e2-8319-4c8a-8cc8-39614c8c5cb6:1", "88334fba-d346-4347-a572-8fb299f31b1a:1", "dbc6d0ed-72dd-460a-a857-d348106f4526:3", "0b9799f7-e516-4c4f-96dc-816b5645920c:3", "4fed5cae-bd4c-4a80-8ced-4b0e88d10fb3:3", "a0200a0c-2e75-4f63-980b-2b703f3b9523:1");
            data.put("INFO:addresses", Arrays.asList("广州", "深圳"));
            data.put("INFO:username", "leo");
            data.put("INFO:age", 18);
            data.put("INFO2:IS_VIP", true);
            data.put("INFO:pay", 10000.1d);
            data.put("INFO:create_by", "tom");
            data.put("INFO:details", testData);
            data.put("INFO:create_time", System.currentTimeMillis());
            Map<String, Object> contactInfo = new HashMap<>(2);
            contactInfo.put("email", "2326130720@qq.com");
            contactInfo.put("phone", "18739577988");
            contactInfo.put("address", "浦东新区");
            data.put("INFO:contact_info", contactInfo);
            hBaseTemplate.save("TEST:USER", "100000" + i, data);
            System.out.println("用户数据保存成功！");
        }

    }


    @Test
    public void testToSaveBatch() {
        Map<String, Map<String, Object>> data = new HashMap<>();

        Map<String, Object> data1 = new HashMap<>();
        data1.put("info1:username", "kangkang");
        data1.put("info1:age", 18);
        data1.put("INFO2:IS_VIP", true);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("info1:username", "jane");
        data2.put("info1:age", 18);
        data2.put("INFO2:IS_VIP", false);

        data.put("12003", data1);
        data.put("11004", data2);

        hBaseTemplate.saveBatch("TEST:LEO_USER", data);
        System.out.println("用户数据批量保存成功！");
    }

    @Test
    public void testGetToMap() {
        Map<String, String> userInfo = hBaseTemplate.getByRowKey("TEST:LEO_USER", "10001");
        String info = userInfo.get("info1:contact_info");
        System.out.println(JsonUtil.fromJsonToMap(info));
        System.out.println(JsonUtil.toJson(info));
        System.out.println(userInfo.get("info1:contact_info"));
        System.out.println(userInfo);
    }

    @Test
    public void testFind() {
        final List<UserEntity> userEntities = hBaseTemplate.findAll(10, UserEntity.class);
        System.out.println(userEntities);
        System.out.println("用户数据批量查询");
    }

    @Test
    public void testFindByPrefix() {
        final List<UserEntity> userEntities = hBaseTemplate.findAllByPrefix("11", 10, UserEntity.class);
        System.out.println("用户数据批量查询");
    }

    @Test
    public void testDeleteData() {
        hBaseTemplate.delete("TEST:LEO_USER", "12003");
        hBaseTemplate.delete("TEST:LEO_USER", "11004", "INFO2");
        hBaseTemplate.delete("TEST:LEO_USER", "10001", "info1", "addresses");
        System.out.println("数据删除完成");
    }

    @Test
    public void testDeleteBatch() {
        hBaseTemplate.deleteBatch("TEST:LEO_USER", Arrays.asList("10001", "10002"));
        hBaseTemplate.deleteBatch("TEST:LEO_USER", Collections.singletonList("10003"), "INFO2");
        hBaseTemplate.deleteBatch("TEST:LEO_USER", Collections.singletonList("10004"),
                "info1", "age", "username");
    }

    @Test
    public void testGetToListMap() {
        final List<Map<String, Object>> mapList = hBaseTemplate.getToListMap("LEO_NS2:USER", "10000099");
        System.out.println(mapList);
    }

    @Test
    public void testGetToOneMap() {
       /* final Map<String, String> map = hBaseTemplate.getToMap("LEO_USER", "11004&weqe=1213", "g", "name");
        System.out.println(map);*/
    }

    @Test
    public void testFindToListMap() {
        final List<List<Map<String, Object>>> mapList = hBaseTemplate.findToListMap("TEST:USER", 10);
        System.out.println(mapList);
    }

    @Test
    public void testSaveJsonValue() {

        Map<String, Object> data = new HashMap<>();
        List<String> testData = Arrays.asList("d518c833-67e3-44a8-ad6f-c0670418391b:3", "7fdc51e2-d187-465d-8d96-c5abb00a687e:3", "a39fddee-8da7-4654-9af6-3796b5d99e78:1", "36e686c2-df51-4750-8718-a77fb9cb3011:3", "e28b5e30-7b1f-4a75-a3dd-d03443744bdc:1", "11ed7418-7a9e-4559-8faa-44b63888faa1:1", "045ecbeb-ec76-4f35-b863-338b9a136520:3", "7a96588c-f20d-40d9-85a5-9b83e2baa235:1", "7b01bfb8-aba8-4200-8051-6d63c2b8af0f:1", "136df375-37c7-4c40-9683-382b5d7e400b:1", "49eeaa63-12ef-444e-ac0c-9e3f15828dfc:3", "d33349fa-1494-4629-95e5-435bea6416f5:3", "0659a2bd-ee97-4782-b9f4-5da7d3ef7086:1", "75d1e9c2-ed30-43c9-9478-b11b9378ee6b:1", "0f54b3d8-5a26-480b-be07-bfc23171d36d:1", "e0bd3f98-2487-47ed-b243-57063552a03b:1", "797925d6-b429-45dc-9d3a-180cb85cbd9f:1", "b89ca8a1-bbd3-41a5-aafe-4a6f78927837:1", "a1251adf-2a98-4830-ad6e-b9450995e033:3", "acfb588d-be26-4bf4-b9d0-c6c5c8b7c9e4:3", "00bbf3c4-c4ba-427c-89c9-db1ad0bb327b:1", "53191bb7-9617-4916-af63-ed50bf304cea:3", "a0f96c3e-38cf-451f-ba2f-6b49442d0d7a:1", "6873f355-ad5d-4ae0-bca4-d52be3170daf:3", "6ebe84c1-6457-4a3f-9583-4cd03271b1e2:3", "a432abb3-85b2-48f3-9dd8-a6ad0accfa50:1", "3c9fe3bf-766d-4bba-849d-f32fb4490207:3", "f0317e48-9715-415f-b3ee-d8cbd6dad2fc:1", "82a86268-ed8e-4685-ab6e-1f6eb52bf998:1", "fe835a95-3577-4e54-b2be-35253a5b31e4:3", "87749881-bbc6-40d9-8bd9-a5dd737627e1:3", "198b8bee-5582-4226-a0a6-71750d18b91a:3", "8e168f25-f720-448a-a3db-e7f3c916cbde:1", "4191daa8-ea7d-4b56-b7e7-ac1880410aa4:3", "c561d884-6d51-4cc9-97f0-b3e448aebf84:3", "4cfd733b-45ac-471f-a09d-a8e8c30994d6:1", "07cdcb57-5122-4dfd-bcac-6678b1297ccb:1", "36bc0495-dd1c-4940-8690-3be05664021f:1", "75f9c64d-6896-466f-a8ca-c5cdb9352ac8:1", "768eef7e-4153-4623-8e73-432981e39af2:1", "5821a678-9b1d-44de-9d3f-1d8172999390:3", "f04f749b-c06a-45b1-9752-8daf9aa306e1:1", "01f766a1-9e4b-4050-ba33-8598249e6542:1", "2514dc5c-caa9-441f-bf12-843e8da36400:1", "36001dd0-14da-4016-9bda-b02d2af6a997:1", "971bfc60-781b-4ac4-9dca-6729708b589c:1", "165f8af6-b79e-453d-a5aa-33315c91357d:1", "a1118069-1368-492a-9517-0c43e627dcdf:3", "5a26db8a-c0e6-4920-aa26-7da2565c9765:1", "1bd7b94a-f11c-4763-8c27-08fea2b20afb:3", "97a05ada-1c10-4133-ae51-6dd91de587db:3", "399eff44-206f-4e91-9104-2195ba78da0b:1", "566dfc57-8045-4b7f-8e3f-445adc9b1296:3", "b484af23-9c74-4ff0-874a-dba54c5163d3:1", "6ff79c0f-c878-433b-bfc1-bd615e4b691e:1", "a0d37af8-97fa-4e20-8827-164a27d9c99a:1", "fa8ae778-7346-4adb-b583-db0b54851c29:3", "b4b45ccf-fc77-467b-9193-71e495540eda:1", "116a3988-7da7-476b-9438-98f8f066e114:3", "44d36dff-5e9a-48fc-96ab-7c0784a1f2dc:3", "05457c99-6801-4140-adff-0b8841effd2c:1", "7ec403c3-f8eb-49fc-8476-d67bc217745b:3", "fd1c12aa-f372-4728-acaa-a086ec3e5c70:1", "844d9066-6f9e-49f2-84ac-eb7b5f1b5939:1", "f10bf94b-7860-4c5a-9ec9-3520a19a58cf:3", "8987d58c-2591-4348-901e-77f4fe979ceb:3", "07295374-c2bb-4992-80a2-22071077a6d9:1", "7725ca07-41ec-4363-912a-48c854347d78:1", "13aaa27f-a3bb-4319-9b47-e2b88056147d:1", "482dbea5-c78f-4374-bfeb-f4327c1df8ef:3", "d06d2367-83e7-4480-b9ab-f7a9b4ec514b:3", "b5699cf0-d631-4cb2-8ae3-069e316baca1:3", "6a06a50a-8ebd-4a30-81d1-24e4aaf1fa46:1", "17181f7c-4a43-4935-b066-0a086ba53d22:1", "f5215e0e-57c9-4876-950e-2923929a1627:1", "84bb948a-7946-4431-a942-dceaf712717c:3", "3b7764ea-8c04-44d6-8dba-7ddbe121c5b0:1", "d0fe93da-fa3d-4def-a0ca-ee39c23fa24d:3", "20e474c9-fdfd-4e44-afbd-bc4f41e6aab5:3", "24c33137-1326-46a7-9e87-4d8f955c33b8:3", "72c00e50-c9e4-45a2-beac-d19245083b94:1", "f739edad-de84-4a41-be22-16310a1ac1f7:1", "a492b9a2-d938-41cb-b830-2c5eaeb36efa:1", "65d7b9d0-b1bd-428a-8880-8056e3077b1e:3", "42e35970-c737-4c21-9d6c-8b9d8d2280ea:3", "77a5644b-acf2-455f-80ee-98db4e80c778:1", "a5e03aa1-d3ca-470f-976d-2a659c1ac7cf:1", "4e9095ba-8bd5-4493-9aed-647b991ae6dc:1", "9c9dda61-7384-46da-9a12-8b49da718fe4:3", "00855ac1-7d4f-4bec-8211-c6f46ecd99ac:1", "df022272-803c-402f-8ca4-151e3b494509:3", "2f991c20-a2ae-41da-b314-881040131dc1:3", "4b4e28bb-4e44-4557-8097-9856f2a91ea2:1", "58c76fb2-d6be-40ce-9888-f51cfbc33f33:3", "a090c3e2-8319-4c8a-8cc8-39614c8c5cb6:1", "88334fba-d346-4347-a572-8fb299f31b1a:1", "dbc6d0ed-72dd-460a-a857-d348106f4526:3", "0b9799f7-e516-4c4f-96dc-816b5645920c:3", "4fed5cae-bd4c-4a80-8ced-4b0e88d10fb3:3", "a0200a0c-2e75-4f63-980b-2b703f3b9523:1");
        data.put("INFO:addresses", Arrays.asList("广州", "深圳"));
        data.put("INFO:username", "leo");
        data.put("INFO:age", 18);
        data.put("INFO2:IS_VIP", true);
        data.put("INFO:pay", 10000.1d);
        data.put("INFO:create_by", "tom");
        data.put("INFO:details", testData);
        data.put("INFO:create_time", System.currentTimeMillis());
        Map<String, Object> contactInfo = new HashMap<>(2);
        contactInfo.put("email", "2326130720@qq.com");
        contactInfo.put("phone", "18739577988");
        contactInfo.put("address", "浦东新区");
        data.put("INFO:contact_info", contactInfo);
        String schemaStr = "{\"type\":\"record\",\"name\":\"CLOUD_BEHAVIOR\",\"namespace\":\"com.intsig.new.schema\",\"fields\":[{\"name\":\"operation\",\"type\":[\"string\",\"null\"]},{\"name\":\"operation_channel\",\"type\":[\"string\",\"null\"]},{\"name\":\"time\",\"type\":[\"string\",\"null\"]},{\"name\":\"ip\",\"type\":[\"string\",\"null\"]},{\"name\":\"lat\",\"type\":[\"string\",\"null\"]},{\"name\":\"lng\",\"type\":[\"string\",\"null\"]},{\"name\":\"user_id\",\"type\":[\"string\",\"null\"]},{\"name\":\"device_id\",\"type\":[\"string\",\"null\"]},{\"name\":\"imei\",\"type\":[\"string\",\"null\"]},{\"name\":\"targets\",\"type\":[{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"target\",\"fields\":[{\"name\":\"type\",\"type\":[\"string\",\"null\"]},{\"name\":\"value\",\"type\":[\"string\",\"null\"]}]}},\"null\"]},{\"name\":\"product_name\",\"type\":[\"string\",\"null\"]},{\"name\":\"product_version\",\"type\":[\"string\",\"null\"]},{\"name\":\"product_vendor\",\"type\":[\"string\",\"null\"]},{\"name\":\"platform\",\"type\":[\"string\",\"null\"]},{\"name\":\"platform_version\",\"type\":[\"string\",\"null\"]},{\"name\":\"language\",\"type\":[\"string\",\"null\"]},{\"name\":\"locale\",\"type\":[\"string\",\"null\"]},{\"name\":\"other_para\",\"type\":[{\"type\":\"map\",\"values\":[\"string\",\"null\"]},\"null\"]},{\"name\":\"product\",\"type\":[\"string\",\"null\"]},{\"name\":\"batch\",\"type\":[\"string\",\"null\"]}]}";

        data.put("INFO:schema", schemaStr);
        hBaseTemplate.save("TEST:USER", "100000", data);

        System.out.println("用户数据保存成功！");

    }

    @Test
    public void testScan() {
        Scan scan = new Scan();
        final List<Map<String, String>> dataList = hBaseTemplate.find("TEST:USER", scan, 100);
        System.out.println(dataList);
    }
}

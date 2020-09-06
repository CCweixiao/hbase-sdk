package com.github.CCweixiao;

import com.github.CCweixiao.HBaseTemplate;
import com.github.CCweixiao.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        hBaseTemplate = new HBaseTemplate("node1", "2181");
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

        Map<String, Object> contactInfo = new HashMap<>(2);
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
        final UserEntity userEntity1 = hBaseTemplate.getByRowKey("10002", UserEntity.class);
        System.out.println("用户数据获取成功！");
        System.out.println(userEntity);
    }

    @Test
    public void testToSave() {
        Map<String, Object> data = new HashMap<>();
        data.put("info1:addresses", Arrays.asList("广州", "深圳"));
        data.put("info1:username", "leo");
        data.put("info1:age", 18);
        data.put("INFO2:IS_VIP", true);
        data.put("info1:pay", 10000.1d);
        data.put("info1:create_by", "tom");
        data.put("info1:create_time", System.currentTimeMillis());
        Map<String, Object> contactInfo = new HashMap<>(2);
        contactInfo.put("email", "2326130720@qq.com");
        contactInfo.put("phone", "18739577988");
        contactInfo.put("address", "浦东新区");
        data.put("info1:contact_info", contactInfo);
        hBaseTemplate.save("TEST:LEO_USER", "10002", data);
        System.out.println("用户数据保存成功！");
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
        Map<String, Object> userInfo = hBaseTemplate.getByRowKey("TEST:LEO_USER", "10001");
        System.out.println(Boolean.valueOf(userInfo.get("INFO2:IS_VIP").toString()));
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
        final List<UserEntity> userEntities = hBaseTemplate.findByPrefix("11", 10, UserEntity.class);
        System.out.println("用户数据批量查询");
    }
}

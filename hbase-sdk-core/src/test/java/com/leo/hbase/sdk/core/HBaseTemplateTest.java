package com.leo.hbase.sdk.core;

import com.leo.hbase.sdk.core.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
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
        hBaseTemplate = new HBaseTemplate("docker-hbase", "2181");
    }

    @Test
    public void testSaveUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("10001");
        userEntity.setUsername("leo");
        userEntity.setAge(18);
        userEntity.setVip(true);
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
        Map<String, Object> data = new HashMap<>();
        data.put("info1:username", "leo");
        data.put("info1:age", 18);
        data.put("INFO2:IS_VIP", true);
        hBaseTemplate.save("TEST:LEO_USER", "10001", data);
        System.out.println("用户数据保存成功！");
    }

    @Test
    public void testToSaveBatch() {
        Map<String, Map<String, Object>> data = new HashMap<>();

        Map<String, Object> data1 = new HashMap<>();
        data1.put("info1:username", "leo");
        data1.put("info1:age", 18);
        data1.put("INFO2:IS_VIP", true);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("info1:username", "leo");
        data2.put("info1:age", 18);
        data2.put("INFO2:IS_VIP", true);

        data.put("10001", data1);
        data.put("10002", data2);

        hBaseTemplate.saveBatch("TEST:LEO_USER", data);
        System.out.println("用户数据批量保存成功！");
    }

    @Test
    public void testGetToMap() {
        Map<String, Object> userInfo = hBaseTemplate.getByRowKey("TEST:LEO_USER", "10001");
        System.out.println(Boolean.valueOf(userInfo.get("INFO2:IS_VIP").toString()));
        System.out.println(userInfo);
    }
}

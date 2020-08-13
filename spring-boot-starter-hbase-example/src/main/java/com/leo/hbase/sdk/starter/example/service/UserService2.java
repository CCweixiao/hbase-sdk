package com.leo.hbase.sdk.starter.example.service;


import com.leo.hbase.sdk.core.HBaseTemplate;
import com.leo.hbase.sdk.starter.example.pojo.UserPojo2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>UserService2</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/23 6:00 下午
 * @since 1.0
 */
@Service
public class UserService2 {
    @Autowired
    private HBaseTemplate hBaseTemplate;

    public void saveUser() {
        UserPojo2 userPojo2 = new UserPojo2();
        //userPojo2.setRowKey("10001");
        userPojo2.setVip(true);
        //userPojo2.setAddress("上海");
        try {
            hBaseTemplate.save(userPojo2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserPojo2> findUser() {
        return hBaseTemplate.findAll(10, UserPojo2.class);
    }

    public UserPojo2 getUser(String rowKey) {
        return hBaseTemplate.getByRowKey(rowKey, UserPojo2.class);
    }
}

//package com.github.CCweixiao.starter.example.service;
//
//
//import com.github.CCweixiao.HBaseTemplate;
//import com.github.CCweixiao.starter.example.pojo.UserPojo2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * <p>UserService2</p>
// *
// * @author leo.jie (leojie1314@gmail.com)
// */
//@Service
//public class UserService2 {
//    @Autowired
//    private HBaseTemplate hBaseTemplate;
//
//    public void saveUser() {
//        UserPojo2 userPojo2 = new UserPojo2();
//        //userPojo2.setRowKey("10001");
//        userPojo2.setVip(true);
//        //userPojo2.setAddress("上海");
//        try {
//            hBaseTemplate.save(userPojo2);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public List<UserPojo2> findUser() {
//        return hBaseTemplate.findAll(10, UserPojo2.class);
//    }
//
//    public UserPojo2 getUser(String rowKey) {
//        return hBaseTemplate.getByRowKey(rowKey, UserPojo2.class);
//    }
//}

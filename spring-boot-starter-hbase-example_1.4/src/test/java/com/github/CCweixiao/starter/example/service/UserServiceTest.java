//package com.github.CCweixiao.starter.example.service;
//
//import com.github.CCweixiao.starter.example.SpringBootHBaseExampleApp14;
//import com.github.CCweixiao.starter.example.pojo.UserPojo;
//import com.github.CCweixiao.util.HBytesUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.Map;
//
//
///**
// * <p>用户服务测试类</p>
// *
// * @author leo.jie (leojie1314@gmail.com)
// * @version 1.0
// * @organization bigdata
// * @website https://www.jielongping.com
// * @date 2020/6/29 10:09 上午
// * @since 1.0
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {SpringBootHBaseExampleApp14.class})
//public class UserServiceTest {
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void testSaveUser() {
//        userService.saveUser();
//    }
//
//    @Test
//    public void testSaveBatchUser() {
//        userService.saveBatchUser();
//        System.out.println("批量数据添加成功！");
//    }
//
//    @Test
//    public void testGetUser() {
//        UserPojo userPojo = userService.getUser("10001");
//        System.out.println(userPojo);
//        System.out.println(userPojo.getAddress());
//        System.out.println("sfsf");
//    }
//
//    @Test
//    public void testFind() {
//        List<UserPojo> users = userService.findUser();
//
//        System.out.println(users);
//    }
//
//    @Test
//    public void testGetUserMap() {
//        Map<String, String> userMap = userService.findUserMap("test:user", "10001");
//        List<String> addressList = HBytesUtil.toList(userMap.get("info:address"), String.class);
//        System.out.println(addressList);
//        System.out.println(userMap);
//    }
//}

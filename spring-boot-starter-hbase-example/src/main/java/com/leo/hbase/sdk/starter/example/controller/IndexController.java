package com.leo.hbase.sdk.starter.example.controller;


import com.leo.hbase.sdk.starter.example.pojo.UserPojo;
import com.leo.hbase.sdk.starter.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>测试的一个controller</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/8 5:07 下午
 * @since 1.0
 */
@RestController
public class IndexController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public UserPojo getUser() {
        return userService.getUser("10001");
    }
}

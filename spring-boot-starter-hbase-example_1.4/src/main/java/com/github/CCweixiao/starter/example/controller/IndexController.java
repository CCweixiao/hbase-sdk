package com.github.CCweixiao.starter.example.controller;


import com.github.CCweixiao.starter.example.pojo.UserPojo;
import com.github.CCweixiao.starter.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>测试的一个controller</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@RestController
public class IndexController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public UserPojo getUser() {
        return userService.getUser("10001");
    }

    @GetMapping("/getUser2")
    public List<Map<String, Object>> getUser2(){
        return userService.getDataWithMapper();
    }

}

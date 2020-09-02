package com.github.CCweixiao.starter.example.controller;


import com.github.CCweixiao.starter.example.pojo.UserPojo;
import com.github.CCweixiao.starter.example.service.AdminService;
import com.github.CCweixiao.starter.example.service.UserService;
import org.apache.hadoop.hbase.client.SnapshotDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>测试的一个controller</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@RestController
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/getUser")
    public UserPojo getUser() {
        return userService.getUser("10001");
    }

    @GetMapping("/getAllSnapshot")
    public List<SnapshotDescription> getAllSnapshot() {
        return adminService.getAllSnapshot();
    }
}

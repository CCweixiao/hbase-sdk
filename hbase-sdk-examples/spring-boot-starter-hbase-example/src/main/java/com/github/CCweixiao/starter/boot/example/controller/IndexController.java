package com.github.CCweixiao.starter.boot.example.controller;


import com.github.CCweixiao.starter.boot.example.service.HBaseAdminService;
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
    private HBaseAdminService hBaseAdminService;

    @GetMapping("/tables")
    public List<String> getAllTableNames() {
        return hBaseAdminService.allTables();
    }

}

package com.github.CCweixiao.hbase.sdk.boot.example.service;

import com.github.CCweixiao.hbase.sdk.boot.example.SpringBootHBaseExampleApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * @author leo.jie (leojie1314@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootHBaseExampleApp.class})
public class UserServiceTest {
    @Autowired
    private HBaseAdminService adminService;

    @Test
    public void testListTables() {
        System.out.println(adminService.allTables());
    }

}

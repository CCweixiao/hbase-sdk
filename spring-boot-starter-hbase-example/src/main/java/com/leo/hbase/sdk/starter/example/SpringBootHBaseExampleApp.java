package com.leo.hbase.sdk.starter.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>使用示例</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 * @version 1.0
 * @organization bigdata
 * @website https://www.jielongping.com
 * @date 2020/6/29 10:00 上午
 * @since 1.0
 */
@SpringBootApplication
public class SpringBootHBaseExampleApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootHBaseExampleApp.class);
        springApplication.run(args);
    }
}

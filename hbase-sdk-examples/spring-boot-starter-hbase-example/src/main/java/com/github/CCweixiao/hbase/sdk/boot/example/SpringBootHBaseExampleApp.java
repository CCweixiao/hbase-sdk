package com.github.CCweixiao.hbase.sdk.boot.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>使用示例</p>
 *
 * @author leo.jie (leojie1314@gmail.com)
 */
@SpringBootApplication
public class SpringBootHBaseExampleApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootHBaseExampleApp.class);
        springApplication.run(args);
    }
}

package com.atguigu.ssyx.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 权限管理模块启动类
 * @author ASUS
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProductApplication.class, args);
    }

}

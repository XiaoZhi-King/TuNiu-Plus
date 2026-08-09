package com.tuniu.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.tuniu.user", "com.tuniu.common"})
@EnableDiscoveryClient
@MapperScan("com.tuniu.user.dao")
public class TuniuUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuniuUserServiceApplication.class, args);
    }
}

package com.tuniu.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.tuniu.order", "com.tuniu.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tuniu.order.feign"})
@MapperScan("com.tuniu.order.dao")
public class TuniuOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuniuOrderServiceApplication.class, args);
    }
}

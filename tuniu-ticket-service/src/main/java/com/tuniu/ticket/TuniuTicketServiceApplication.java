package com.tuniu.ticket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.tuniu.ticket", "com.tuniu.common"})
@EnableDiscoveryClient
@EnableCaching
@MapperScan("com.tuniu.ticket.dao")
public class TuniuTicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuniuTicketServiceApplication.class, args);
    }
}

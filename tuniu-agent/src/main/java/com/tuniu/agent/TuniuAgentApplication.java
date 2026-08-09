package com.tuniu.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.tuniu.agent", "com.tuniu.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.tuniu.agent.feign"})
public class TuniuAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuniuAgentApplication.class, args);
    }
}

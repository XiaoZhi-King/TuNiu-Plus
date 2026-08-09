package com.tuniu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 途牛 API 网关。
 * <p>对外端口 8080，前端 baseURL 保持 http://localhost:8080 不变。</p>
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TuniuGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuniuGatewayApplication.class, args);
    }
}

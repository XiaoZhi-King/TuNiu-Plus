package com.tuniu.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 网关配置：跨域 + 限流 KeyResolver
 */
@Configuration
public class GatewayConfig {

    /**
     * 跨域：允许前端直连网关
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    /**
     * 限流维度：优先按用户账号，其次按客户端 IP
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String account = exchange.getRequest().getHeaders().getFirst("X-User-Account");
            if (account != null && !account.isEmpty()) {
                return reactor.core.publisher.Mono.just(account);
            }
            String ip = exchange.getRequest().getRemoteAddress() == null
                    ? "anonymous"
                    : exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            return reactor.core.publisher.Mono.just(ip == null ? "anonymous" : ip);
        };
    }
}

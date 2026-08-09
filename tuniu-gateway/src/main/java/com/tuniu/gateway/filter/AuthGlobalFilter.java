package com.tuniu.gateway.filter;

import com.tuniu.common.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局鉴权过滤器（练习：请自行实现以下能力）
 * <p>
 * TODO 实现要求：
 * 1) 从请求头取出 token（key 见 CommonConstants.HEADER_TOKEN）
 * 2) 调用 JwtUtil.checkToken(token) 校验签名与有效期
 * 3) token 合法时，使用 JwtUtil.getAccount(token) 解析出账号
 * 4) 通过 request.mutate().header(HEADER_USER_ACCOUNT, account).build() 把账号透传给下游微服务
 * 5) token 非法或不存在时，建议直接放行（不阻断），但不透传账号（保持与旧前端兼容：userId 通过业务参数传递）
 * <p>
 * 参考工具类：com.tuniu.common.util.JwtUtil / com.tuniu.common.constant.CommonConstants
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        // TODO: 在这里实现 JWT 校验与透传 HEADER_USER_ACCOUNT，下方是默认放行占位
        log.info("[网关-Auth-TODO] path={}，JWT 鉴权请自行实现（当前默认放行）", path);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 在限流过滤器之前执行
        return -100;
    }
}

package com.tuniu.agent.feign;

import com.tuniu.common.domain.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 订单服务 Feign 客户端（Agent 工具调用使用）
 */
@FeignClient(name = "tuniu-order-service", contextId = "agentOrderClient")
public interface OrderFeignClient {

    /** 查询用户订单列表 */
    @RequestMapping("/orders/list")
    List<Orders> listOrders(@RequestParam(value = "userId", required = false) Integer userId,
                            @RequestParam(value = "status", defaultValue = "") String status);
}

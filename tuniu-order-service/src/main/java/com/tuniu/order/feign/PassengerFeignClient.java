package com.tuniu.order.feign;

import com.tuniu.common.domain.Passenger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 用户服务 Feign 客户端
 * 通过 Nacos 服务名 tuniu-user-service 负载均衡调用
 */
@FeignClient(name = "tuniu-user-service", contextId = "passengerClient")
public interface PassengerFeignClient {

    /** 按 ID 集合批量查询乘车人（订单详情乘车人回填） */
    @PostMapping("/passenger/listByIds")
    List<Passenger> listByIds(@RequestBody List<Integer> ids);
}

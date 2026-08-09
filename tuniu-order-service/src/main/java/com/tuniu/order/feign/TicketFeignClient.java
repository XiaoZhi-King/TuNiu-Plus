package com.tuniu.order.feign;

import com.tuniu.common.domain.Passenger;
import com.tuniu.common.domain.TrainTickets;
import com.tuniu.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 票务服务 Feign 客户端
 * 通过 Nacos 服务名 tuniu-ticket-service 负载均衡调用
 */
@FeignClient(name = "tuniu-ticket-service", contextId = "ticketClient")
public interface TicketFeignClient {

    /** 查询车票详情 */
    @GetMapping("/trainTickets/{id}")
    Result findById(@PathVariable("id") Integer id);

    /** 数据库层原子扣减库存（防超卖兜底） */
    @GetMapping("/trainTickets/{id}/decrement")
    Result decrementAvailableSeats(@PathVariable("id") Integer id);
}

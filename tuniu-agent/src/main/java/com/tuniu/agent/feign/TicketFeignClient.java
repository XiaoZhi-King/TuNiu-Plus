package com.tuniu.agent.feign;

import com.tuniu.common.domain.City;
import com.tuniu.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 票务服务 Feign 客户端（Agent 工具调用使用）
 */
@FeignClient(name = "tuniu-ticket-service", contextId = "agentTicketClient")
public interface TicketFeignClient {

    /** 全部城市列表 */
    @GetMapping("/city/list")
    List<City> listCities();

    /** 按名称查询城市 */
    @GetMapping("/city/which")
    Result findCityByName(@RequestParam("name") String name);

    /** 按条件查询车票 */
    @GetMapping("/trainTickets/list")
    com.tuniu.common.domain.TrainTickets[] listTickets(
            @RequestParam("startStation") Integer startStation,
            @RequestParam("endStation") Integer endStation,
            @RequestParam("startTime") String startTime,
            @RequestParam(value = "condition", required = false) String condition);
}

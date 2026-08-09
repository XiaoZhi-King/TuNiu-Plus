package com.tuniu.ticket.controller;

import com.tuniu.common.domain.TrainTickets;
import com.tuniu.common.vo.Result;
import com.tuniu.ticket.service.TrainTicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 车票查询接口
 * 路径与前端历史调用保持一致：/trainTickets/list
 */
@RestController
@RequestMapping("/trainTickets")
public class TrainTicketsController {

    @Autowired
    private TrainTicketsService trainTicketsService;

    /**
     * 按条件查询车票列表
     * 前端直接以 List 形式消费（res.data 即数组），保持原返回结构
     */
    @GetMapping("/list")
    public List<TrainTickets> list(@RequestParam("startStation") Integer startStation,
                                   @RequestParam("endStation") Integer endStation,
                                   @RequestParam("startTime") String startTime,
                                   @RequestParam(value = "condition", required = false) String condition) {
        return trainTicketsService.list(startStation, endStation, startTime, condition);
    }

    /**
     * 库存预热到 Redis（运维 / 启动时调用）
     */
    @GetMapping("/importAll")
    public Result importAll() {
        return Result.ok(trainTicketsService.importAll());
    }

    /**
     * 按主键查询车票详情
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") Integer id) {
        return Result.ok(trainTicketsService.findById(id));
    }

    /**
     * 数据库层原子扣减库存（供订单服务通过 OpenFeign 调用，防超卖兜底）
     * 仅限内部服务调用，网关不对外暴露
     */
    @GetMapping("/{id}/decrement")
    public Result decrementAvailableSeats(@PathVariable("id") Integer id) {
        boolean ok = trainTicketsService.decrementAvailableSeats(id);
        return ok ? Result.ok("扣减成功", true) : Result.error(500, "库存不足");
    }
}

package com.tuniu.order.controller;

import com.tuniu.common.domain.Orders;
import com.tuniu.common.domain.TrainTickets;
import com.tuniu.common.vo.Result;
import com.tuniu.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 订单接口（路径与前端历史调用保持一致）
 */
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/list")
    public List<Orders> list(@RequestParam(name = "userId", required = false) Integer userId,
                             @RequestParam(name = "status", defaultValue = "") String status) {
        log.info("[订单] 查询订单列表，userId={}，status='{}'", userId, status);
        return orderService.list(userId, status);
    }

    @RequestMapping("/del")
    public Result deleteById(@RequestParam("id") Integer id) {
        log.info("[订单] 删除订单，id={}", id);
        int r = orderService.deleteById(id);
        if (r > 0) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("rows", r);
            data.put("id", id);
            return new Result(200, "删除成功", data);
        }
        return Result.error(500, "删除失败");
    }

    @RequestMapping("/save")
    public Result save(@RequestParam(name = "userId") Integer userId,
                       TrainTickets trainTickets,
                       @RequestParam(name = "passengers") String passengers,
                       @RequestParam(name = "price") Integer price) {
        Integer ticketId = (trainTickets == null ? null : trainTickets.getId());
        log.info("[订单] 下单请求，userId={}，ticketId={}，price={}", userId, ticketId, price);

        String orderId = orderService.save(userId, trainTickets, passengers, price);
        if ("失败".equals(orderId)) {
            return Result.error(500, "下单失败");
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("orderId", orderId);
        data.put("totalPrice", price);
        data.put("status", "待支付");
        return new Result(200, "下单成功，订单号：" + orderId, data);
    }
}

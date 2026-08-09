package com.tuniu.order.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuniu.common.domain.Orders;
import com.tuniu.common.domain.TrainTickets;
import com.tuniu.order.dao.OrderDetailMapper;
import com.tuniu.order.dao.OrderMapper;
import com.tuniu.order.feign.PassengerFeignClient;
import com.tuniu.order.feign.TicketFeignClient;
import com.tuniu.order.mq.OrderProducer;
import com.tuniu.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单服务（练习：请自行实现以下能力 - 这是面试高并发的核心）
 * <p>
 * TODO 实现要求（3 层防超卖 + MQ 异步 + 事务 + Feign 跨服务调用）：
 * 1) list(userId, status)：
 *      - orderMapper.list(userId, status) 查订单列表
 *      - 遍历每个订单，把 ticketId / passengerIds 查出后用 Feign 回填：
 *          - ticketId 通过 orderDetailMapper.findTicketIdByOrderId，再 ticketFeignClient.findById(ticketId) → objectMapper.convertValue 转 TrainTickets
 *          - passengerIds 通过 orderDetailMapper.findPassengerIdsByOrderId，再 passengerFeignClient.listByIds(ids)
 *          - passengers 同时赋给 Orders.passengers / Orders.passenger（前端别名）
 * 2) save(userId, trainTickets, passengers, price)：高并发核心流程
 *      - 基础校验：userId / trainTickets.id / 乘客列表非空
 *      - 解析乘客：把 "[1,2]" / "1,2" 等字符串解析成 List<Integer> passengerIds
 *      - Redisson 分布式锁：lockKey = "lock:ticket:{ticketId}"，tryLock(3,10,SECONDS)，获取失败返回 "失败"
 *      - Redis 库存预扣：stockKey = "ticket:stock:{ticketId}"，DECR；若结果<0则INCR回滚返回失败；若key不存在先回源 ticketService.findById 初始化
 *      - 落库（事务 @Transactional(rollbackFor=Exception.class)）：
 *          * Orders(status="待支付", time=yyyy-MM-dd HH:mm:ss, userId, totalPrice) insert，返回自增主键
 *          * 遍历 passengerIds 批量 insert order_detail
 *          * 任何失败 → 同时把 Redis 库存 INCR 回滚 → return "失败"
 *      - MQ 异步：构造 OrderMessage(orderIdStr, userIdL, ticketIdL, pidsL, priceD) → orderProducer.sendOrder(msg)（失败只 error，不影响订单落库）
 *      - 返回 String.valueOf(orderId)
 *      - finally 解锁
 * 3) deleteById(id)：事务内先 orderDetailMapper.deleteByOrderId，再 orderMapper.deleteById(id)
 * 4) paySuccess(orderId)：orderMapper.updateStatusById(orderId, "已支付")，rows > 0 返回 true
 * <p>
 * 关键依赖（已在本类注入，可直接用）：RedissonClient、StringRedisTemplate、OrderProducer、TicketFeignClient、PassengerFeignClient、ObjectMapper
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private TicketFeignClient ticketFeignClient;
    @Autowired
    private PassengerFeignClient passengerFeignClient;
    @Autowired
    private OrderProducer orderProducer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Orders> list(Integer userId, String status) {
        // TODO: 自行实现（订单查询 + Feign 回填车票/乘车人）
        throw new UnsupportedOperationException("练习待实现：OrderServiceImpl.list");
    }

    @Override
    public String save(Integer userId, TrainTickets trainTickets, String passengers, Integer price) {
        // TODO: 自行实现（Redisson 锁 + Redis 预扣减库存 + 事务落库 + MQ 异步扣减 DB）
        throw new UnsupportedOperationException("练习待实现：OrderServiceImpl.save");
    }

    @Override
    public int deleteById(Integer id) {
        // TODO: 自行实现（事务：先删明细，再删主表）
        throw new UnsupportedOperationException("练习待实现：OrderServiceImpl.deleteById");
    }

    @Override
    public boolean paySuccess(Integer orderId) {
        // TODO: 自行实现（把订单状态从 "待支付" 改成 "已支付"）
        throw new UnsupportedOperationException("练习待实现：OrderServiceImpl.paySuccess");
    }
}

package com.tuniu.order.service;

import com.tuniu.common.domain.Orders;
import com.tuniu.common.domain.TrainTickets;

import java.util.List;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 查询订单列表（按用户、状态过滤，回填车票与乘车人信息）
     */
    List<Orders> list(Integer userId, String status);

    /**
     * 下单：Redisson 分布式锁防超卖 + Redis 库存预扣 + MQ 异步 DB 兜底
     *
     * @param userId      用户 ID
     * @param trainTickets 车票（取 id 即可）
     * @param passengers  乘客 ID 集合的字符串形式（兼容前端 "[1,2]" 或 "1,2"）
     * @param price       总价
     * @return 订单 ID；失败返回 "失败"
     */
    String save(Integer userId, TrainTickets trainTickets, String passengers, Integer price);

    /**
     * 删除订单（同时删除订单明细）
     */
    int deleteById(Integer id);

    /**
     * 模拟支付成功：更新订单状态为已支付
     */
    boolean paySuccess(Integer orderId);
}

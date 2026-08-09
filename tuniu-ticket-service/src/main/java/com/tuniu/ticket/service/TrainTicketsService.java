package com.tuniu.ticket.service;

import com.tuniu.common.domain.TrainTickets;

import java.util.List;

/**
 * 车票服务：车票查询 + Redis 缓存
 */
public interface TrainTicketsService {

    /**
     * 按出发/到达站点 + 日期 + 排序条件查询车票列表
     *
     * @param startStation 出发城市 ID
     * @param endStation   到达城市 ID
     * @param startTime    出发日期 yyyy-MM-dd
     * @param condition    排序条件：time / price / lasts
     */
    List<TrainTickets> list(Integer startStation, Integer endStation, String startTime, String condition);

    /**
     * 按主键查询车票
     */
    TrainTickets findById(Integer id);

    /**
     * 把全部车票余票预热到 Redis，支撑高并发下单库存判定
     */
    boolean importAll();

    /**
     * 库存原子扣减（数据库层防超卖兜底，订单服务通过 OpenFeign 调用）
     */
    boolean decrementAvailableSeats(Integer id);
}

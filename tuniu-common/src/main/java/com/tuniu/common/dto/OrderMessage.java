package com.tuniu.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 订单异步处理消息（RabbitMQ 载荷）：用于异步扣减库存、记录明细
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage implements Serializable {
    private String orderId;
    private Long userId;
    private Long ticketId;
    private List<Long> passengerIds;
    private Double price;
}

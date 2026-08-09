package com.tuniu.order.mq;

import com.tuniu.common.dto.OrderMessage;
import com.tuniu.order.feign.TicketFeignClient;
import com.tuniu.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单消息消费者：异步处理下单后续动作
 * - 通过 Feign 调用票务服务原子扣减库存（DB 兜底，保证最终一致性）
 *
 * 注意：Redis 库存已在下单主流程同步扣减，这里只做 DB 兜底
 */
@Component
@Slf4j
public class OrderMessageConsumer {

    @Autowired
    private TicketFeignClient ticketFeignClient;

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void handleOrderMessage(OrderMessage message) {
        if (message == null || message.getTicketId() == null) {
            log.warn("[订单MQ] 消息非法，已丢弃：{}", message);
            return;
        }
        log.info("[订单MQ] 收到消息，orderId={}，ticketId={}，开始异步扣减 DB 库存",
                message.getOrderId(), message.getTicketId());

        try {
            Result result = ticketFeignClient.decrementAvailableSeats(message.getTicketId().intValue());
            if (result != null && Integer.valueOf(200).equals(result.getCode())) {
                log.info("[订单MQ] DB 库存扣减成功，orderId={}，ticketId={}",
                        message.getOrderId(), message.getTicketId());
            } else {
                log.warn("[订单MQ] DB 库存扣减未生效，可能已无库存，orderId={}，ticketId={}，result={}",
                        message.getOrderId(), message.getTicketId(), result);
            }
        } catch (Exception e) {
            // 兜底失败不影响已落库订单，由人工或对账处理
            log.error("[订单MQ] DB 库存扣减异常，orderId={}，ticketId={}，原因={}",
                    message.getOrderId(), message.getTicketId(), e.getMessage(), e);
        }
    }
}

package com.tuniu.order.mq;

import com.tuniu.common.dto.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单消息生产者：下单成功后发送异步消息
 */
@Component
@Slf4j
public class OrderProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendOrder(OrderMessage message) {
        amqpTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_ROUTING_KEY,
                message
        );
        log.info("[订单MQ] 发送消息成功，orderId={}，ticketId={}",
                message.getOrderId(), message.getTicketId());
    }
}

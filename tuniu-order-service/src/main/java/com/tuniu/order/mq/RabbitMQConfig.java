package com.tuniu.order.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 配置：订单异步处理队列
 *
 * 拓扑：
 *   order.exchange (direct) -> order.queue (routingKey = order.create)
 *
 * 用途：下单成功后异步处理库存扣减、通知等耗时操作，保证下单主流程快速返回
 */
@Configuration
public class RabbitMQConfig {

    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_QUEUE = "order.queue";
    public static final String ORDER_ROUTING_KEY = "order.create";

    @Bean
    public DirectExchange orderExchange() {
        return ExchangeBuilder.directExchange(ORDER_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(ORDER_QUEUE).build();
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(ORDER_ROUTING_KEY);
    }
}

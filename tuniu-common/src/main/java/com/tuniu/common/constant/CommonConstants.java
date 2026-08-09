package com.tuniu.common.constant;

/**
 * 公共常量
 */
public final class CommonConstants {

    private CommonConstants() {
    }

    /** 请求头中的 token 字段名 */
    public static final String HEADER_TOKEN = "token";
    /** 请求头中的用户 id 字段名（网关解析后透传） */
    public static final String HEADER_USER_ID = "X-User-Id";
    /** 请求头中的用户账号字段名（网关解析后透传） */
    public static final String HEADER_USER_ACCOUNT = "X-User-Account";

    /** 订单状态 */
    public static final String ORDER_STATUS_UNPAID = "待支付";
    public static final String ORDER_STATUS_PAID = "已支付";

    /** RabbitMQ 订单交换机 / 路由键 / 队列 */
    public static final String ORDER_EXCHANGE = "tuniu.order.exchange";
    public static final String ORDER_ROUTING_KEY = "tuniu.order.create";
    public static final String ORDER_QUEUE = "tuniu.order.queue";
}

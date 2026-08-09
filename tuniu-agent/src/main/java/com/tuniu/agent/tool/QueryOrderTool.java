package com.tuniu.agent.tool;

import com.tuniu.agent.feign.OrderFeignClient;
import com.tuniu.common.domain.Orders;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 工具：查询用户订单（LangChain4j @Tool 注解版）
 */
@Component
@Slf4j
public class QueryOrderTool {

    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 查询用户的订单列表，可按状态过滤
     */
    @Tool("查询用户的订单列表，可按状态过滤。当用户想查看订单、查行程、了解支付状态时调用。")
    public String queryOrders(
            @P("用户 ID") Integer userId,
            @P("订单状态过滤：待支付/已支付，可选，为空查全部") String status) {
        log.info("[Tool] 调用 queryOrders userId={} status={}", userId, status);
        if (userId == null) {
            return "{\"error\":\"参数不完整，需要 userId\"}";
        }
        try {
            List<Orders> orders = orderFeignClient.listOrders(userId, status == null ? "" : status);
            if (orders == null) {
                return "{\"error\":\"查询订单失败\"}";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("{\"total\":").append(orders.size()).append(",\"orders\":[");
            for (int i = 0; i < orders.size(); i++) {
                Orders o = orders.get(i);
                if (i > 0) sb.append(",");
                sb.append("{\"id\":").append(o.getId())
                        .append(",\"status\":\"").append(o.getStatus() == null ? "" : o.getStatus()).append("\"")
                        .append(",\"totalPrice\":").append(o.getTotalPrice() == null ? 0 : o.getTotalPrice())
                        .append(",\"time\":\"").append(o.getTime() == null ? "" : o.getTime()).append("\"");
                if (o.getTrainTickets() != null) {
                    sb.append(",\"trainNumber\":\"").append(o.getTrainTickets().getTrainNumber()).append("\"");
                    sb.append(",\"startStation\":\"").append(o.getTrainTickets().getStartStation()).append("\"");
                    sb.append(",\"endStation\":\"").append(o.getTrainTickets().getEndStation()).append("\"");
                    sb.append(",\"startTime\":\"").append(o.getTrainTickets().getStartTime()).append("\"");
                }
                sb.append("}");
            }
            sb.append("]}");
            return sb.toString();
        } catch (Exception e) {
            log.error("[Tool] queryOrders 异常：{}", e.getMessage(), e);
            return "{\"error\":\"查询订单失败：" + e.getMessage() + "\"}";
        }
    }
}

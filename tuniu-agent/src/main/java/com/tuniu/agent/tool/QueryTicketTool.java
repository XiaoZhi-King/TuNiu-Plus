package com.tuniu.agent.tool;

import com.tuniu.agent.feign.TicketFeignClient;
import com.tuniu.common.domain.City;
import com.tuniu.common.domain.TrainTickets;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 工具：按出发/到达城市 + 日期查询车票（LangChain4j @Tool 注解版）
 */
@Component
@Slf4j
public class QueryTicketTool {

    @Autowired
    private TicketFeignClient ticketFeignClient;

    /**
     * 根据出发城市、到达城市和出发日期查询火车票
     */
    @Tool("根据出发城市、到达城市和出发日期查询火车票。当用户想查票、比价、了解车次时调用。")
    public String queryTickets(
            @P("出发城市 ID") Integer startStation,
            @P("到达城市 ID") Integer endStation,
            @P("出发日期，格式 yyyy-MM-dd") String startTime,
            @P("排序条件：time(按时间)/price(按价格)/lasts(按时长)，可选，无排序需求时传 null") String condition) {
        log.info("[Tool] 调用 queryTickets start={} end={} date={} condition={}",
                startStation, endStation, startTime, condition);
        if (startStation == null || endStation == null || startTime == null) {
            return "{\"error\":\"参数不完整，需要 startStation、endStation、startTime\"}";
        }
        try {
            TrainTickets[] tickets = ticketFeignClient.listTickets(startStation, endStation, startTime, condition);
            if (tickets == null) {
                return "{\"error\":\"查询车票失败\"}";
            }
            // 顺便把城市名回填，便于 LLM 自然语言输出
            City start = findCityName(startStation);
            City end = findCityName(endStation);

            StringBuilder sb = new StringBuilder();
            sb.append("{\"start\":\"").append(start == null ? startStation : start.getName()).append("\",");
            sb.append("\"end\":\"").append(end == null ? endStation : end.getName()).append("\",");
            sb.append("\"date\":\"").append(startTime).append("\",");
            sb.append("\"total\":").append(tickets.length).append(",\"tickets\":[");
            for (int i = 0; i < tickets.length; i++) {
                TrainTickets t = tickets[i];
                if (i > 0) sb.append(",");
                sb.append("{\"id\":").append(t.getId())
                        .append(",\"trainNumber\":\"").append(t.getTrainNumber()).append("\"")
                        .append(",\"startStation\":\"").append(t.getStartStation()).append("\"")
                        .append(",\"endStation\":\"").append(t.getEndStation()).append("\"")
                        .append(",\"startTime\":\"").append(t.getStartTime()).append("\"")
                        .append(",\"endTime\":\"").append(t.getEndTime()).append("\"")
                        .append(",\"price\":").append(t.getPrice())
                        .append(",\"availableSeats\":").append(t.getAvailableSeats())
                        .append(",\"seatType\":\"").append(t.getSeatType()).append("\"")
                        .append(",\"lasts\":\"").append(t.getLasts() == null ? "" : t.getLasts()).append("\"")
                        .append("}");
            }
            sb.append("]}");
            return sb.toString();
        } catch (Exception e) {
            log.error("[Tool] queryTickets 异常：{}", e.getMessage(), e);
            return "{\"error\":\"查询车票失败：" + e.getMessage() + "\"}";
        }
    }

    private City findCityName(Integer id) {
        try {
            List<City> cities = ticketFeignClient.listCities();
            if (cities != null) {
                for (City c : cities) {
                    if (id.equals(c.getId())) return c;
                }
            }
        } catch (Exception ignore) {
        }
        return null;
    }
}

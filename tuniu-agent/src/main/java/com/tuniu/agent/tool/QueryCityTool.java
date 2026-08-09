package com.tuniu.agent.tool;

import com.tuniu.agent.feign.TicketFeignClient;
import com.tuniu.common.domain.City;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 工具：查询城市列表（LangChain4j @Tool 注解版）
 * <p>
 * - LangChain4j 启动时自动扫描所有 @Tool 方法，生成 OpenAI Function 定义
 * - LLM 决策调用 queryCities() 时，框架自动执行方法并把返回值回填给 LLM
 */
@Component
@Slf4j
public class QueryCityTool {

    @Autowired
    private TicketFeignClient ticketFeignClient;

    /**
     * 查询系统支持的全部城市列表
     * 当用户想了解出发/到达城市或需要城市 ID 时调用
     */
    @Tool("查询系统支持的全部城市列表，返回城市 ID 与名称。当用户想了解出发/到达城市或需要城市 ID 时调用。")
    public String queryCities() {
        log.info("[Tool] 调用 queryCities");
        List<City> cities = ticketFeignClient.listCities();
        if (cities == null) {
            return "{\"error\":\"查询城市失败\"}";
        }
        // 精简输出，避免上下文过长
        List<String> items = cities.stream()
                .map(c -> "{\"id\":" + c.getId() + ",\"name\":\"" + c.getName() + "\"}")
                .collect(Collectors.toList());
        return "{\"total\":" + cities.size() + ",\"cities\":[" + String.join(",", items) + "]}";
    }
}

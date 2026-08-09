package com.tuniu.agent.controller;

import com.tuniu.agent.service.Assistant;
import com.tuniu.common.dto.ChatRequest;
import com.tuniu.common.dto.ChatResponse;
import com.tuniu.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Agent 聊天接口（LangChain4j 版）
 * <p>
 * 网关路由：/agent/** -> tuniu-agent
 * - POST /agent/chat  单轮对话，LangChain4j 自动完成 ReAct 循环
 * - GET  /agent/info  健康检查
 */
@RestController
@RequestMapping("/agent")
@Slf4j
public class AgentController {

    @Autowired
    private Assistant assistant;

    /**
     * 单轮对话（无状态）
     * <p>
     * LangChain4j 自动处理：LLM 决策 → 工具调用 → 结果回填 → 再问 LLM → 返回最终回复
     * 后续可扩展为多轮：前端传 sessionId，后端用 ChatMemory 维护会话历史
     */
    @PostMapping("/chat")
    public Result chat(@RequestBody ChatRequest request) {
        if (request == null || request.getMessage() == null || request.getMessage().trim().isEmpty()) {
            return Result.error(400, "消息不能为空");
        }
        long start = System.currentTimeMillis();
        log.info("[Agent] 收到对话请求，userId={}，message='{}'",
                request.getUserId(), request.getMessage());

        // 把 userId 拼到用户消息里，让 LLM 能查到该用户订单
        String userMessage = buildUserMessage(request);

        String reply;
        try {
            reply = assistant.chat(userMessage);
        } catch (Exception e) {
            log.error("[Agent] 对话异常：{}", e.getMessage(), e);
            return Result.error(500, "智能助手开小差了：" + e.getMessage());
        }

        long cost = System.currentTimeMillis() - start;
        boolean toolUsed = reply != null && !reply.isEmpty();
        ChatResponse resp = new ChatResponse(reply, toolUsed, cost);
        log.info("[Agent] 对话完成，cost={}ms，reply长度={}", cost, reply == null ? 0 : reply.length());
        return Result.ok(resp);
    }

    /**
     * 健康检查 / 能力清单
     */
    @RequestMapping("/info")
    public Result info() {
        java.util.Map<String, Object> info = new java.util.LinkedHashMap<>();
        info.put("name", "tuniu-agent");
        info.put("framework", "LangChain4j (OpenAI compatible + @AiService + @Tool)");
        info.put("endpoints", List.of("POST /agent/chat", "GET /agent/info"));
        info.put("tools", List.of("queryCities", "queryTickets", "queryOrders"));
        return Result.ok(info);
    }

    /**
     * 把 userId 注入到用户消息里，让 LLM 查询订单时能拿到当前登录用户
     */
    private String buildUserMessage(ChatRequest request) {
        StringBuilder sb = new StringBuilder(request.getMessage());
        if (request.getUserId() != null) {
            sb.append("\n（当前登录用户 ID 为 ").append(request.getUserId())
              .append("，查询该用户订单时请使用此 ID）");
        }
        return sb.toString();
    }
}

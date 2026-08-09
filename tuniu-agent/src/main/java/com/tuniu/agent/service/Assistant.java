package com.tuniu.agent.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * 途牛智能客服 Agent（LangChain4j 声明式 AI Service）
 * <p>
 * - 框架在启动时自动扫描 @AiService 接口，生成代理实现并注册为 Bean
 * - 自动注入 ChatModel（来自 langchain4j-open-ai-spring-boot-starter）
 * - 自动发现所有 @Tool 注解的方法并注册为 Function Calling 工具
 * - 调用 chat() 时，LangChain4j 内部完成 ReAct 循环（LLM 决策 → 工具执行 → 结果回填 → 再问 LLM）
 * <p>
 * 业务代码只关心这个接口，框架帮我们处理所有 Agent 编排细节
 */
@AiService
public interface Assistant {

    /**
     * 单轮对话：用户问 → LLM 决策是否调工具 → 执行工具 → LLM 总结 → 返回自然语言
     *
     * @param userMessage 用户输入
     * @return 智能客服回复
     */
    @SystemMessage("""
            你是途牛旅游的智能客服助手，可以帮用户查询城市、车票和订单信息。
            请用简洁友好的中文回答。当需要查询具体数据时，调用对应的工具，不要编造数据。
            """)
    String chat(String userMessage);
}

package com.tuniu.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Agent 聊天请求
 */
@Data
public class ChatRequest implements Serializable {
    /** 用户消息 */
    private String message;
    /** 可选：用户 id（用于查询个人订单） */
    private Integer userId;
    /** 可选：会话 id */
    private String sessionId;
}

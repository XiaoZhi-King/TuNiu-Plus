package com.tuniu.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Agent 聊天响应
 */
@Data
public class ChatResponse implements Serializable {
    /** 回复内容 */
    private String reply;
    /** 是否命中工具调用 */
    private boolean toolUsed;
    /** 耗时(ms) */
    private long costMs;

    public ChatResponse(String reply, boolean toolUsed, long costMs) {
        this.reply = reply;
        this.toolUsed = toolUsed;
        this.costMs = costMs;
    }
}

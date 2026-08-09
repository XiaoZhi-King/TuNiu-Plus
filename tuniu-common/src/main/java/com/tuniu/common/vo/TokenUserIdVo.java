package com.tuniu.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录成功后返回的 token + userId + 昵称
 */
@Data
public class TokenUserIdVo implements Serializable {
    private String token;
    private Integer userId;
    private String name;
}

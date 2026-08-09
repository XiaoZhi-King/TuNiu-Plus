package com.tuniu.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 */
@Data
public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public Result() {
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result ok() {
        return new Result(200, "成功", null);
    }

    public static Result ok(Object data) {
        return new Result(200, "成功", data);
    }

    public static Result ok(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result ok(String msg, Object data) {
        return new Result(200, msg, data);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, msg, null);
    }
}

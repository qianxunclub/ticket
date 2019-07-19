package com.qianxunclub.ticket.model;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-19 15:44
 * @description: TODO
 */
@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

package com.qianxunclub.ticket.constant;

/**
 * @author zhangbin
 * @date 2019-07-02 10:13
 * @description: TODO
 */
public enum StatusEnum {

    START("开始"),
    ING("正在抢票"),
    SUCCESS("抢票成功")
    ;

    private String status;

    StatusEnum(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

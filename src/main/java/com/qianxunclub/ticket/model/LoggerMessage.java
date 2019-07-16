package com.qianxunclub.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-01 14:24
 * @description: TODO
 */
@Data
@AllArgsConstructor
public class LoggerMessage {
    private String body;
    private String timestamp;
    private String threadName;
    private String className;
    private String level;

    @Override
    public String toString(){
        return "[" + timestamp + "][" + threadName + "]:" + body;
    }
}

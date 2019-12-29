package com.qianxunclub.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangbin
 * @date 2019-06-08 16:11
 * @description: TODO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeModel {
    private String phoneNumber;
    private String userName;
    private String password;
    private String orderId;
    private String name;
    private String trainNum;
    private String trainDate;
    private String from;
    private String to;
    private String serverSckey;
}

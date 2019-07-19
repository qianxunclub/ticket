package com.qianxunclub.ticket.model;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-19 15:53
 * @description: TODO
 */
@Data
public class TicketRequest {

    private String passengerCode;

    private String username;

    private String password;

    private String date;

    private String from;

    private String to;

    private String trainNumber;

    private String mobile;

    private String realName;
}

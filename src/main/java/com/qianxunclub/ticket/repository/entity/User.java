package com.qianxunclub.ticket.repository.entity;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-18 17:00
 * @description: TODO
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String mobile;
    private String createDate;
}

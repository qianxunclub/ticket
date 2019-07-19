package com.qianxunclub.ticket.model;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-05-31 15:19
 * @description: TODO
 */
@Data
public class UserModel {
    private String username;
    private String password;
    private String answer;
    private String uamtk;
    private LogdeviceModel logdeviceModel;
    private String globalRepeatSubmitToken;
    private String keyCheckIsChange;
}

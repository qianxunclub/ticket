package com.qianxunclub.ticket.model;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-23 09:32
 * @description: TODO
 */
@Data
public class LoginRequest {
    private String username;
    private String password;

    public UserModel toUserModel(){
        UserModel userModel = new UserModel();
        userModel.setUsername(username);
        userModel.setPassword(password);
        return userModel;
    }
}

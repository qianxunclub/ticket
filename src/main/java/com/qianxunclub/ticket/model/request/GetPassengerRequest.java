package com.qianxunclub.ticket.model.request;

import com.qianxunclub.ticket.model.UserModel;

import org.springframework.beans.BeanUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-23 09:32
 * @description: TODO
 */
@Data
@ApiModel("登录信息")
public class GetPassengerRequest {
    @ApiModelProperty("12306用户名")
    private String username;
    @ApiModelProperty("12306密码")
    private String password;

    public UserModel toUserModel() {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(this, userModel);
        return userModel;
    }
}

package com.qianxunclub.ticket.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-26 16:51
 * @description: TODO
 */
@Data
@ApiModel("乘客信息")
public class PassengerResponse {
    @ApiModelProperty("乘客编号")
    String code;
    @ApiModelProperty("乘客姓名")
    String passengerName;
    @ApiModelProperty("乘客性别")
    String sexName;
    @ApiModelProperty("证件类型")
    String passengerIdTypeName;
    @ApiModelProperty("证件号码")
    String passengerIdNo;
    @ApiModelProperty("电话号码")
    String mobileNo;
    @ApiModelProperty("手机号码")
    String phoneNo;
}

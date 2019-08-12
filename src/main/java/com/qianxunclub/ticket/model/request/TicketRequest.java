package com.qianxunclub.ticket.model.request;

import com.qianxunclub.ticket.constant.SeatLevelEnum;
import com.qianxunclub.ticket.model.BuyTicketInfoModel;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-25 12:01
 * @description: TODO
 */
@Data
@ApiModel("购票信息")
public class TicketRequest extends GetPassengerRequest {
    @ApiModelProperty(value = "乘车日期", example = "2019-07-08")
    private String date;
    @ApiModelProperty(value = "出发站点", example = "深圳")
    private String from;
    @ApiModelProperty(value = "到达站点", example = "三门峡")
    private String to;
    @ApiModelProperty(value = "车次", example = "G818")
    private String trainNumber;
    @ApiModelProperty(value = "乘客编号", example = "登录完成后会乘客信息CODE的值")
    private String passengerCode;
    @ApiModelProperty(value = "乘车人手机号", example = "1234567890")
    private String mobile;
    @ApiModelProperty(value = "乘车人真实姓名", example = "XXX")
    private String realName;
    @ApiModelProperty(value = "座位级别", example = "ONE,RUANWO")
    private String seat;

    public List<SeatLevelEnum> seatList() {
        List<SeatLevelEnum> seatList = new ArrayList<>();
        String[] seats = seat.split(",");
        for (int i = 0; i < seats.length; i++) {
            SeatLevelEnum seatLevelEnum = SeatLevelEnum.valueOf(seats[i]);
            seatList.add(seatLevelEnum);
        }
        return seatList;
    }

    public BuyTicketInfoModel toBuyTicketInfoModel() {
        BuyTicketInfoModel buyTicketInfoModel = new BuyTicketInfoModel();
        BeanUtils.copyProperties(this, buyTicketInfoModel);
        buyTicketInfoModel.setSeat(this.seatList());
        return buyTicketInfoModel;
    }
}

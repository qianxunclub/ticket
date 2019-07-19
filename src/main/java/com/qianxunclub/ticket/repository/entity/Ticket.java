package com.qianxunclub.ticket.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.qianxunclub.ticket.constant.SeatLevelEnum;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-19 10:18
 * @description: TODO
 */
@Data
public class Ticket {

    @TableField("`date`")
    private String date;

    @TableField("`from`")
    private String from;
    @TableField("`to`")
    private String to;

    private String trainNumber;

    private String passengerIdTypeCode;

    private String mobile;

    private String realName;

    private String seat;

    public List<SeatLevelEnum> getSeatList() {
        List<SeatLevelEnum> seatList = new ArrayList<>();
        String[] seats = seat.split(",");
        for (int i = 0; i < seats.length; i++) {
            SeatLevelEnum seatLevelEnum = SeatLevelEnum.valueOf(seats[i]);
            seatList.add(seatLevelEnum);
        }
        return seatList;
    }
}

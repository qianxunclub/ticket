package com.qianxunclub.ticket.model;

import com.qianxunclub.ticket.constant.SeatLevelEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-05-31 10:50
 * @description: TODO
 */
@Data
@AllArgsConstructor
public class SeatModel {

    private SeatLevelEnum seatLevel;
    private String count;

    public SeatModel(SeatLevelEnum seatLevel,String[] info) {
        this.seatLevel = seatLevel;
        String n = info[seatLevel.getIndex()];
        this.count = n;
    }


    @Override
    public String toString(){
        return seatLevel.getName() + "-" + count + "票";
    }

}

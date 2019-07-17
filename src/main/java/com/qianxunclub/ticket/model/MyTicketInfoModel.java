package com.qianxunclub.ticket.model;

import com.qianxunclub.ticket.config.SeatLevelEnum;
import com.qianxunclub.ticket.config.StatusEnum;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-05-30 17:00
 * @description: TODO
 */
@Data
public class MyTicketInfoModel extends UserModel {

    private String date;

    private String from;

    private String to;

    private String trainNumber;

    private String passengerIdTypeCode;

    private String mobile;

    private String realName;

    private PassengerModel passengerModel;

    private String globalRepeatSubmitToken;

    private String keyCheckIsChange;

    private List<SeatLevelEnum> seat = new ArrayList<>();

    private int queryNum;

    private StatusEnum status = StatusEnum.START;

    private LogdeviceModel logdeviceModel;

}

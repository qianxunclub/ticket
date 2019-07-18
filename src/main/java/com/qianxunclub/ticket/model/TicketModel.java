package com.qianxunclub.ticket.model;

import com.qianxunclub.ticket.constant.SeatLevelEnum;
import com.qianxunclub.ticket.ticket.Station;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-05-31 10:46
 * @description: TODO
 */
@Data
public class TicketModel {

    private String trainDate;
    private String secret;
    private String trainCode;
    private String trainNumber;
    private String from;
    private String to;
    private String trainLocation;
    private String departDate;
    private String arriveDate;
    private String interval;
    private String leftTicket;

    private List<SeatModel> seat = new ArrayList<>();

    public void setInfo(String[] info) {
        this.secret = URLDecoder.decode(info[0]);
        this.trainCode = info[2];
        this.trainNumber = info[3];
        this.from = info[6];
        this.to = info[7];
        this.trainLocation = info[15];
        this.departDate = info[8];
        this.arriveDate = info[9];
        this.interval = info[10];
        this.leftTicket = info[12];

        for(SeatLevelEnum seatLevelEnum : SeatLevelEnum.values()){
            SeatModel seatModel = new SeatModel(seatLevelEnum,info);
            seat.add(seatModel);
        }
    }

    @Override
    public String toString(){
        return "✅车次[" + trainNumber + "][" + Station.getNameByCode(from) + "-" + Station.getNameByCode(to) + "][" + departDate + "-" + arriveDate + "]:" + seat.toString();
    }
}

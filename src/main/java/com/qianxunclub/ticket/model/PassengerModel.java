package com.qianxunclub.ticket.model;

import java.util.Map;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-06-05 10:30
 * @description: TODO
 */
@Data
public class PassengerModel {
    String code;
    String passengerName;
    String sexCode;
    String sexName;
    String bornDate;
    String countryCode;
    String passengerIdTypeCode;
    String passengerIdTypeName;
    String passengerIdNo;
    String passengerType;
    String passengerFlag;
    String passengerTypeName;
    String mobileNo;
    String phoneNo;
    String email;
    String address;
    String postalcode;
    String firstLetter;
    String recordCount;
    String totalLimes;
    String indexId;
    String gatBornDate;
    String gatValidDateStart;
    String gatValidDateEnd;
    String gatVersi;
    String allEncStr;

    public PassengerModel() {
        super();
    }

    public PassengerModel(Map<String, String> passengerMap) {
        this.code = passengerMap.get("code");
        this.passengerName = passengerMap.get("passenger_name");
        this.sexCode = passengerMap.get("sex_code");
        this.sexName = passengerMap.get("sex_name");
        this.bornDate = passengerMap.get("born_date");
        this.countryCode = passengerMap.get("countryCode");
        this.passengerIdTypeCode = passengerMap.get("passenger_id_type_code");
        this.passengerIdTypeName = passengerMap.get("passenger_id_type_name");
        this.passengerIdNo = passengerMap.get("passenger_id_no");
        this.passengerType = passengerMap.get("passenger_type");
        this.passengerFlag = passengerMap.get("passenger_flag");
        this.passengerTypeName = passengerMap.get("passenger_type_name");
        this.mobileNo = passengerMap.get("mobile_no");
        this.phoneNo = passengerMap.get("phone_no");
        this.email = passengerMap.get("email");
        this.address = passengerMap.get("address");
        this.postalcode = passengerMap.get("postalcode");
        this.firstLetter = passengerMap.get("first_letter");
        this.recordCount = passengerMap.get("record_count");
        this.totalLimes = passengerMap.get("total_limes");
        this.indexId = passengerMap.get("index_id");
        this.gatBornDate = passengerMap.get("gat_born_date");
        this.gatValidDateStart = passengerMap.get("gat_valid_date_start");
        this.gatValidDateEnd = passengerMap.get("gat_valid_date_end");
        this.gatVersi = passengerMap.get("gat_versi");
        this.allEncStr = passengerMap.get("allEncStr");
    }

    public String getPassengerTicketStr(TicketInfoModel ticketInfoModel) {
        PassengerModel passengerModel = ticketInfoModel.getPassengerModel();
        return ticketInfoModel.getSeat().get(0).getCode() + "," +
                "0,1," + passengerModel.getPassengerName() + "," +
                passengerModel.getPassengerIdTypeCode() + "," +
                passengerModel.getPassengerIdNo() + "," +
                passengerModel.getMobileNo() + "," +
                "N," + passengerModel.getAllEncStr();
    }

    public String getOldPassengerStr(PassengerModel passengerModel) {
        return passengerModel.getPassengerName() + "," +
                passengerModel.getPassengerIdTypeCode() + "," +
                passengerModel.getPassengerIdNo() + "," +
                passengerModel.getPassengerType() + "_";
    }

}

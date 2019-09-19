package com.qianxunclub.ticket.model.response;

import com.qianxunclub.ticket.constant.StatusEnum;
import lombok.Data;

@Data
public class TicketInfoResponse {

    private String date;
    private String from;
    private String to;
    private String trainNumber;
    private String realName;
    private int queryNum;
    private StatusEnum status = StatusEnum.START;
}

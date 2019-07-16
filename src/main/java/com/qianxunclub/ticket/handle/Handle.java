package com.qianxunclub.ticket.handle;

import com.qianxunclub.ticket.config.Config;
import com.qianxunclub.ticket.model.MyTicketInfoModel;
import com.qianxunclub.ticket.model.TicketModel;
import com.qianxunclub.ticket.request.Request;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-05-30 15:55
 * @description: TODO
 */
@Component
@Slf4j
@AllArgsConstructor
public class Handle {

    private Login login;
    private Ticket ticket;
    private Request request;
    private Buy buy;
    private Config config;

    public TicketModel queryicket(MyTicketInfoModel myTicketInfoModel) {
        try {
            return ticket.getMyTicket(myTicketInfoModel);
        } catch (Exception e) {
            log.error("车票查询失败", e);
        }
        return null;
    }

    public boolean buy(TicketModel ticketModel, MyTicketInfoModel myTicketInfoModel) {

        if (!login.login(myTicketInfoModel)) {
            return false;
        }

        if (!request.checkUser(myTicketInfoModel.getUsername())) {
            return false;
        }
        boolean flag = buy.buy(myTicketInfoModel, ticketModel);
        return flag;
    }

    public void sleep(Long time) {
        try {
            Thread.sleep(time == null ? config.getQueryTicketSellpTime() * 1000 : time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

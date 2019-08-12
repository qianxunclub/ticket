package com.qianxunclub.ticket.service;

import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.model.PassengerModel;
import com.qianxunclub.ticket.model.Result;
import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.model.UserTicketStore;
import com.qianxunclub.ticket.repository.dao.TicketDao;
import com.qianxunclub.ticket.repository.entity.Ticket;
import com.qianxunclub.ticket.ticket.DoHandle;
import com.qianxunclub.ticket.ticket.Login;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-07-19 10:33
 * @description: TODO
 */
@Slf4j
@Service
@AllArgsConstructor
public class TicketService {
    private TicketDao ticketDao;
    private DoHandle doHandle;
    private Login login;
    private ApiRequestService apiRequestService;

    public List<PassengerModel> login(UserModel userModel) {

        if (!login.login(userModel)) {
            UserTicketStore.userBasicCookieStore.remove(userModel.getUsername());
            return null;
        }
        List<PassengerModel> passengerModelList = this.passengers(userModel.getUsername());
        return passengerModelList;
    }

    public Result addTicketInfo(BuyTicketInfoModel buyTicketInfoModel) {
        UserTicketStore.add(buyTicketInfoModel);
        Ticket ticket = ticketDao.getTicketByUserName(buyTicketInfoModel.getUsername());
        if (ticket != null) {
            return new Result("ERROR", "该账户已经在购票中！");
        }
        ticket = new Ticket();
        BeanUtils.copyProperties(buyTicketInfoModel, ticket);
        ticket.setSeat(buyTicketInfoModel.getSeatStr());
        ticketDao.add(ticket);
        doHandle.add(buyTicketInfoModel);
        return new Result("SUCCESS", "添加成功");
    }

    private List<PassengerModel> passengers(String userName) {
        return apiRequestService.passengers(userName);
    }

    public List<BuyTicketInfoModel> getBuyTicketInfoModel() {
        List<BuyTicketInfoModel> buyTicketInfoModelList = new ArrayList<>();
        List<Ticket> ticketList = ticketDao.list();
        ticketList.forEach(ticket -> {
            BuyTicketInfoModel buyTicketInfoModel = new BuyTicketInfoModel();
            BeanUtils.copyProperties(ticket, buyTicketInfoModel);
            buyTicketInfoModel.setSeat(ticket.toSeatList());
            buyTicketInfoModelList.add(buyTicketInfoModel);
        });
        return buyTicketInfoModelList;
    }
}

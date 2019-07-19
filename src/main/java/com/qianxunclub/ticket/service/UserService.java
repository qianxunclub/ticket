package com.qianxunclub.ticket.service;

import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.repository.dao.TicketDao;
import com.qianxunclub.ticket.repository.dao.UserDao;
import com.qianxunclub.ticket.repository.entity.Ticket;
import com.qianxunclub.ticket.repository.entity.User;

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
public class UserService {
    private UserDao userDao;
    private TicketDao ticketDao;

    public void addTicketInfo(BuyTicketInfoModel buyTicketInfoModel) {
        User user = new User();
        BeanUtils.copyProperties(buyTicketInfoModel, user);
        userDao.add(user);
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(buyTicketInfoModel, ticket);
        ticketDao.add(ticket);
    }

    public List<BuyTicketInfoModel> getBuyTicketInfoModel() {
        List<BuyTicketInfoModel> buyTicketInfoModelList = new ArrayList<>();
        List<User> userList = userDao.list();
        userList.forEach(user -> {
            BuyTicketInfoModel buyTicketInfoModel = new BuyTicketInfoModel();
            Ticket ticket = ticketDao.getTicketByUserId(user.getId());
            if (ticket == null) {
                return;
            }
            BeanUtils.copyProperties(user, buyTicketInfoModel);
            BeanUtils.copyProperties(ticket, buyTicketInfoModel);
            buyTicketInfoModelList.add(buyTicketInfoModel);
        });
        return buyTicketInfoModelList;
    }
}

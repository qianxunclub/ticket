package com.qianxunclub.ticket.controller;

import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.ticket.DoHandle;
import com.qianxunclub.ticket.model.UserTicketStore;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * @author zhangbin
 * @date 2019-06-08 19:31
 * @description: TODO
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = "application/json")
public class Index {

    private DoHandle doHandle;

    @ResponseBody
    @RequestMapping(value = "user",method = RequestMethod.GET)
    public Object buying(){
        return UserTicketStore.buyTicketInfoModelList;
    }

    @ResponseBody
    @RequestMapping(value = "user",method = RequestMethod.POST)
    public void user(@RequestBody BuyTicketInfoModel buyTicketInfoModel){
        doHandle.add(buyTicketInfoModel);
    }

}

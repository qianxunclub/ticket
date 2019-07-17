package com.qianxunclub.ticket.controller;

import com.qianxunclub.ticket.model.MyTicketInfoModel;
import com.qianxunclub.ticket.thread.Start;
import com.qianxunclub.ticket.thread.UserInfo;

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

    private Start start;

    @ResponseBody
    @RequestMapping(value = "user",method = RequestMethod.GET)
    public Object buying(){
        return UserInfo.myTicketInfoModelList;
    }

    @ResponseBody
    @RequestMapping(value = "user",method = RequestMethod.POST)
    public void user(@RequestBody MyTicketInfoModel myTicketInfoModel){
        start.add(myTicketInfoModel);
    }

}

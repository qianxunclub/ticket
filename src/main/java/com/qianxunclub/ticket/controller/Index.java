package com.qianxunclub.ticket.controller;

import com.qianxunclub.ticket.thread.UserInfo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbin
 * @date 2019-06-08 19:31
 * @description: TODO
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class Index {

    @ResponseBody
    @RequestMapping(value = "user",method = RequestMethod.GET)
    public Object user(){
        return UserInfo.myTicketInfoModelList;
    }

    @ResponseBody
    @RequestMapping(value = "init",method = RequestMethod.POST)
    public Object init(){
        return UserInfo.myTicketInfoModelList;
    }

}

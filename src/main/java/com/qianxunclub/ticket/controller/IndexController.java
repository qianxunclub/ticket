package com.qianxunclub.ticket.controller;

import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.model.Result;
import com.qianxunclub.ticket.model.UserModel;
import com.qianxunclub.ticket.service.TicketService;
import com.qianxunclub.ticket.model.UserTicketStore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * @author zhangbin
 * @date 2019-06-08 19:31
 * @description: TODO
 */
@Api("接口文档")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = "application/json")
public class IndexController {

    private TicketService ticketService;

    @ApiOperation("登录")
    @ResponseBody
    @GetMapping("login")
    public Result login(UserModel userModel) {

        return ticketService.login(userModel);
    }

    @ApiOperation("正在抢票中的用户")
    @ResponseBody
    @GetMapping("user")
    public Object buying() {
        return UserTicketStore.buyTicketInfoModelList;
    }

    @ApiOperation("添加抢票信息")
    @ResponseBody
    @PostMapping("user")
    public Result user(@RequestBody BuyTicketInfoModel buyTicketInfoModel) {
        return ticketService.addTicketInfo(buyTicketInfoModel);
    }

}

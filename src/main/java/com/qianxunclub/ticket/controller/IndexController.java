package com.qianxunclub.ticket.controller;

import com.qianxunclub.ticket.model.PassengerModel;
import com.qianxunclub.ticket.model.request.PassengerRequest;
import com.qianxunclub.ticket.model.Result;
import com.qianxunclub.ticket.model.request.TicketRequest;
import com.qianxunclub.ticket.model.response.PassengerResponse;
import com.qianxunclub.ticket.service.TicketService;
import com.qianxunclub.ticket.model.UserTicketStore;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    @PostMapping("getPassenger")
    public Result getPassenger(@RequestBody PassengerRequest passengerRequest) {
        Result result = new Result("SUCCESS", "登录成功");
        List<PassengerModel> passengerModelList = ticketService.login(passengerRequest.toUserModel());
        if (CollectionUtils.isEmpty(passengerModelList)) {
            result = new Result("ERROR", "登录失败");
        }
        List<PassengerResponse> passengerResponseList = new ArrayList<>();
        passengerModelList.forEach(passengerModel -> {
            PassengerResponse passengerResponse = passengerModel.toPassengerResponse();
            passengerResponseList.add(passengerResponse);
        });
        result.setData(passengerResponseList);
        return result;
    }

    @ApiOperation("正在抢票中的用户")
    @ResponseBody
    @GetMapping("buying")
    public Object buying() {
        return UserTicketStore.buyTicketInfoModelList;
    }

    @ApiOperation("添加抢票信息")
    @ResponseBody
    @PostMapping("ticket")
    public Result user(@RequestBody TicketRequest TicketRequest) {
        return ticketService.addTicketInfo(TicketRequest.toBuyTicketInfoModel());
    }

}

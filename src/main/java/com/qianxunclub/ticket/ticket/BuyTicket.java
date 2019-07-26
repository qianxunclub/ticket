package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.model.NoticeModel;
import com.qianxunclub.ticket.model.PassengerModel;
import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.model.TicketModel;
import com.qianxunclub.ticket.service.NoticeService;
import com.qianxunclub.ticket.service.ApiRequestService;
import com.qianxunclub.ticket.util.CaptchaImageForPy;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-06-04 15:22
 * @description: TODO
 */
@AllArgsConstructor
@Component
@Slf4j
public class BuyTicket {

    private ApiRequestService apiRequestService;
    private PassengerService passengerService;
    private CaptchaImageForPy captchaImageForPy;
    private NoticeService noticeService;
    private Login login;

    public boolean buy(BuyTicketInfoModel buyTicketInfoModel, TicketModel ticketModel) {

        if (!login.login(buyTicketInfoModel)) {
            return false;
        }

        if (!apiRequestService.checkUser(buyTicketInfoModel.getUsername())) {
            return false;
        }

        if (!apiRequestService.submitOrderRequest(buyTicketInfoModel, ticketModel)) {
            return false;
        }

        String token = apiRequestService.initDc(buyTicketInfoModel.getUsername());
        buyTicketInfoModel.setGlobalRepeatSubmitToken(token.split(",")[0]);
        buyTicketInfoModel.setKeyCheckIsChange(token.split(",")[1]);

        PassengerModel passengerModel = passengerService.getPassenger(buyTicketInfoModel);
        if (passengerModel == null) {
            return false;
        }
        buyTicketInfoModel.setPassengerModel(passengerModel);


        String isShowPassCode = apiRequestService.checkOrderInfo(buyTicketInfoModel);
        if (StringUtils.isEmpty(isShowPassCode)) {
            return false;
        }
        if (isShowPassCode.equals("Y")) {
            String captchaImage = apiRequestService.captchaImage();
            String position = captchaImageForPy.check(captchaImage);
            if (!apiRequestService.checkRandCodeAnsyn(position, buyTicketInfoModel.getGlobalRepeatSubmitToken())) {
                return false;
            }
        }
        if (!apiRequestService.getQueueCount(buyTicketInfoModel, ticketModel)) {
            return false;
        }
        if (!apiRequestService.confirmSingleForQueue(buyTicketInfoModel, ticketModel)) {
            return false;
        }

        String orderid = apiRequestService.queryOrderWaitTime(buyTicketInfoModel);
        if (!StringUtils.isEmpty(orderid)) {
            NoticeModel noticeModel = new NoticeModel();
            noticeModel.setName(buyTicketInfoModel.getRealName());
            noticeModel.setUserName(buyTicketInfoModel.getUsername());
            noticeModel.setPassword(buyTicketInfoModel.getPassword());
            noticeModel.setPhoneNumber(buyTicketInfoModel.getMobile());
            noticeModel.setOrderId(orderid);
            noticeService.send(noticeModel);
            return true;
        }

        return false;
    }
}

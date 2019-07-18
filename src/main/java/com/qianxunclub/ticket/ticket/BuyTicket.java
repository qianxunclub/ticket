package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.model.NoticeModel;
import com.qianxunclub.ticket.model.PassengerModel;
import com.qianxunclub.ticket.model.TicketInfoModel;
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
    private CaptchaImageForPy captchaImageForPy;
    private NoticeService noticeService;
    private Login login;

    public boolean buy(TicketInfoModel ticketInfoModel, TicketModel ticketModel) {

        if (!login.login(ticketInfoModel)) {
            return false;
        }

        if (!apiRequestService.checkUser(ticketInfoModel.getUsername())) {
            return false;
        }

        if (!apiRequestService.submitOrderRequest(ticketInfoModel, ticketModel)) {
            return false;
        }

        String token = apiRequestService.initDc(ticketInfoModel.getUsername());
        ticketInfoModel.setGlobalRepeatSubmitToken(token.split(",")[0]);
        ticketInfoModel.setKeyCheckIsChange(token.split(",")[1]);

        PassengerModel passengerModel = this.getPassenger(ticketInfoModel);
        if (passengerModel == null) {
            return false;
        }
        ticketInfoModel.setPassengerModel(passengerModel);


        String isShowPassCode = apiRequestService.checkOrderInfo(ticketInfoModel);
        if (StringUtils.isEmpty(isShowPassCode)) {
            return false;
        }
        if (isShowPassCode.equals("Y")) {
            String captchaImage = apiRequestService.captchaImage();
            String position = captchaImageForPy.check(captchaImage);
            if (!apiRequestService.checkRandCodeAnsyn(position, ticketInfoModel.getGlobalRepeatSubmitToken())) {
                return false;
            }
        }
        if (!apiRequestService.getQueueCount(ticketInfoModel, ticketModel)) {
            return false;
        }
        if (!apiRequestService.confirmSingleForQueue(ticketInfoModel, ticketModel)) {
            return false;
        }

        String orderid = apiRequestService.queryOrderWaitTime(ticketInfoModel);
        if (!StringUtils.isEmpty(orderid)) {
            NoticeModel noticeModel = new NoticeModel();
            noticeModel.setName(ticketInfoModel.getRealName());
            noticeModel.setUserName(ticketInfoModel.getUsername());
            noticeModel.setPassword(ticketInfoModel.getPassword());
            noticeModel.setPhoneNumber(ticketInfoModel.getMobile());
            noticeModel.setOrderId(orderid);
            noticeService.send(noticeModel);
            return true;
        }

        return false;
    }


    public PassengerModel getPassenger(TicketInfoModel ticketInfoModel) {
        List<PassengerModel> passengerModelList = apiRequestService.getPassengerDTOs(ticketInfoModel.getGlobalRepeatSubmitToken());
        PassengerModel passengerModel = passengerModelList.stream().filter(model -> (
                model.getPassengerIdTypeCode().equals(ticketInfoModel.getPassengerIdTypeCode()) && model.getPassengerName().equals(ticketInfoModel.getRealName())
        )).findFirst().orElse(null);
        if (passengerModel == null) {
            log.error("没有找到对应的乘客信息：" + ticketInfoModel.getRealName() + ",passengerIdTypeCode:" + ticketInfoModel.getPassengerIdTypeCode());
        }
        return passengerModel;
    }

}

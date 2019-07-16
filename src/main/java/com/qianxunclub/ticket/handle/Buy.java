package com.qianxunclub.ticket.handle;

import com.qianxunclub.ticket.model.NoticeModel;
import com.qianxunclub.ticket.model.PassengerModel;
import com.qianxunclub.ticket.model.MyTicketInfoModel;
import com.qianxunclub.ticket.model.TicketModel;
import com.qianxunclub.ticket.request.Notice;
import com.qianxunclub.ticket.request.Request;
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
public class Buy {

    private Request request;
    private CaptchaImageForPy captchaImageForPy;
    private Notice notice;

    public boolean buy(MyTicketInfoModel myTicketInfoModel, TicketModel ticketModel) {

        if (!request.submitOrderRequest(myTicketInfoModel, ticketModel)) {
            return false;
        }

        String token = request.initDc(myTicketInfoModel.getUsername());
        myTicketInfoModel.setGlobalRepeatSubmitToken(token.split(",")[0]);
        myTicketInfoModel.setKeyCheckIsChange(token.split(",")[1]);

        PassengerModel passengerModel = this.getPassenger(myTicketInfoModel);
        if (passengerModel == null) {
            return false;
        }
        myTicketInfoModel.setPassengerModel(passengerModel);


        String isShowPassCode = request.checkOrderInfo(myTicketInfoModel);
        if (StringUtils.isEmpty(isShowPassCode)) {
            return false;
        }
        if (isShowPassCode.equals("Y")) {
            String captchaImage = request.captchaImage();
            String position = captchaImageForPy.check(captchaImage);
            if (!request.checkRandCodeAnsyn(position, myTicketInfoModel.getGlobalRepeatSubmitToken())) {
                return false;
            }
        }
        if (!request.getQueueCount(myTicketInfoModel, ticketModel)) {
            return false;
        }
        if (!request.confirmSingleForQueue(myTicketInfoModel, ticketModel)) {
            return false;
        }

        String orderid = request.queryOrderWaitTime(myTicketInfoModel);
        if (!StringUtils.isEmpty(orderid)) {
            NoticeModel noticeModel = new NoticeModel();
            noticeModel.setName(myTicketInfoModel.getName());
            noticeModel.setUserName(myTicketInfoModel.getUsername());
            noticeModel.setPassword(myTicketInfoModel.getPassword());
            noticeModel.setPhoneNumber(myTicketInfoModel.getMobile());
            noticeModel.setOrderId(orderid);
            notice.send(noticeModel);
            return true;
        }

        return false;
    }


    public PassengerModel getPassenger(MyTicketInfoModel myTicketInfoModel) {
        List<PassengerModel> passengerModelList = request.getPassengerDTOs(myTicketInfoModel.getGlobalRepeatSubmitToken());
        PassengerModel passengerModel = passengerModelList.stream().filter(model -> model.getPassengerIdTypeCode().equals(myTicketInfoModel.getPassengerIdTypeCode())).findFirst().orElse(null);
        if (passengerModel == null) {
            log.error("乘客不存在：" + myTicketInfoModel.getUsername() + ",passengerIdTypeCode:" + myTicketInfoModel.getPassengerIdTypeCode());
        }
        return passengerModel;
    }

}

package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.model.PassengerModel;
import com.qianxunclub.ticket.service.ApiRequestService;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-07-26 16:24
 * @description: TODO
 */
@AllArgsConstructor
@Component
@Slf4j
public class PassengerService {

    private ApiRequestService apiRequestService;

    public PassengerModel getPassenger(BuyTicketInfoModel buyTicketInfoModel) {
        List<PassengerModel> passengerModelList = apiRequestService
                .getPassengerDTOs(buyTicketInfoModel.getUsername(),
                        buyTicketInfoModel.getGlobalRepeatSubmitToken());
        PassengerModel passengerModel = passengerModelList.stream().filter(model -> {
            if (!StringUtils.isEmpty(buyTicketInfoModel.getAllEncStr())) {
                return model.getAllEncStr().equals(buyTicketInfoModel.getAllEncStr());
            }
            return false;
        }).findFirst().orElse(null);
        if (passengerModel == null) {
            log.error("没有找到对应的乘客信息：" + buyTicketInfoModel.getRealName() + ",allEncStr:"
                    + buyTicketInfoModel.getAllEncStr());
        }
        return passengerModel;
    }
}

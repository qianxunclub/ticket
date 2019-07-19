package com.qianxunclub.ticket.model;

import org.apache.http.impl.client.BasicCookieStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbin
 * @date 2019-06-08 11:10
 * @description: TODO
 */
public class UserTicketStore {
    public static List<BuyTicketInfoModel> buyTicketInfoModelList = new ArrayList<>();
    public static Map<String, BasicCookieStore> userBasicCookieStore = new HashMap<>();

    public static void add(BuyTicketInfoModel buyTicketInfoModel) {
        userBasicCookieStore.put(buyTicketInfoModel.getUsername(), new BasicCookieStore());
        buyTicketInfoModelList.add(buyTicketInfoModel);
    }

    public static void remove(BuyTicketInfoModel myTicketInfo) {
        Integer index = null;
        for (int i = 0; i < buyTicketInfoModelList.size(); i++) {
            BuyTicketInfoModel buyTicketInfoModel = buyTicketInfoModelList.get(i);
            if (buyTicketInfoModel.getPassengerIdTypeCode().equals(myTicketInfo.getPassengerIdTypeCode()) && buyTicketInfoModel.getRealName().equals(myTicketInfo.getRealName())) {
                index = i;
            }
        }
        if (index != null) {
            BuyTicketInfoModel buyTicketInfoModel = buyTicketInfoModelList.get(index);
            userBasicCookieStore.remove(buyTicketInfoModel.getUsername());
            buyTicketInfoModelList.remove(index);
        }
    }
}

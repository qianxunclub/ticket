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
    public static List<TicketInfoModel> ticketInfoModelList = new ArrayList<>();
    public static Map<String, BasicCookieStore> userBasicCookieStore = new HashMap<>();

    public static void add(TicketInfoModel ticketInfoModel) {
        userBasicCookieStore.put(ticketInfoModel.getUsername(), new BasicCookieStore());
        ticketInfoModelList.add(ticketInfoModel);
    }

    public static void remove(TicketInfoModel myTicketInfo) {
        Integer index = null;
        for (int i = 0; i < ticketInfoModelList.size(); i++) {
            TicketInfoModel ticketInfoModel = ticketInfoModelList.get(i);
            if (ticketInfoModel.getPassengerIdTypeCode().equals(myTicketInfo.getPassengerIdTypeCode()) && ticketInfoModel.getRealName().equals(myTicketInfo.getRealName())) {
                index = i;
            }
        }
        if (index != null) {
            TicketInfoModel ticketInfoModel = ticketInfoModelList.get(index);
            userBasicCookieStore.remove(ticketInfoModel.getUsername());
            ticketInfoModelList.remove(index);
        }
    }
}

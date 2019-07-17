package com.qianxunclub.ticket.thread;

import com.qianxunclub.ticket.model.MyTicketInfoModel;

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
public class UserInfo {
    public static List<MyTicketInfoModel> myTicketInfoModelList = new ArrayList<>();
    public static Map<String, BasicCookieStore> userBasicCookieStore = new HashMap<>();

    public static void add(MyTicketInfoModel myTicketInfoModel) {
        userBasicCookieStore.put(myTicketInfoModel.getUsername(), new BasicCookieStore());
        myTicketInfoModelList.add(myTicketInfoModel);
    }

    public static void remove(MyTicketInfoModel myTicketInfo) {
        Integer index = null;
        for (int i = 0; i < myTicketInfoModelList.size(); i++) {
            MyTicketInfoModel myTicketInfoModel = myTicketInfoModelList.get(i);
            if (myTicketInfoModel.getPassengerIdTypeCode().equals(myTicketInfo.getPassengerIdTypeCode()) && myTicketInfoModel.getRealName().equals(myTicketInfo.getRealName())) {
                index = i;
            }
        }
        if (index != null) {
            MyTicketInfoModel myTicketInfoModel = myTicketInfoModelList.get(index);
            userBasicCookieStore.remove(myTicketInfoModel.getUsername());
            myTicketInfoModelList.remove(index);
        }
    }
}

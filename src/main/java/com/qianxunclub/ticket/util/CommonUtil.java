package com.qianxunclub.ticket.util;

import com.qianxunclub.ticket.model.MyTicketInfoModel;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-06-08 11:44
 * @description: TODO
 */
@Slf4j
public class CommonUtil {

    public static String getThreadName(MyTicketInfoModel myTicketInfoModel) {
        String name = "" +
                myTicketInfoModel.getUsername() +
                "~" + myTicketInfoModel.getRealName() +
                "~" + myTicketInfoModel.getMobile();
        return name;
    }
}

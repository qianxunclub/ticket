package com.qianxunclub.ticket.thread;

import com.qianxunclub.ticket.handle.Handle;
import com.qianxunclub.ticket.model.MyTicketInfoModel;
import com.qianxunclub.ticket.model.TicketModel;
import com.qianxunclub.ticket.util.ApplicationContextHelper;
import com.qianxunclub.ticket.util.CommonUtil;


import org.springframework.util.CollectionUtils;

import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-06-08 11:08
 * @description: TODO
 */
@Slf4j
public class HandleThread implements Callable {

    private Handle handle;
    private MyTicketInfoModel myTicketInfoModel;

    public HandleThread(MyTicketInfoModel myTicketInfoModel) {
        this.myTicketInfoModel = myTicketInfoModel;
        handle = ApplicationContextHelper.getBean(Handle.class);
    }

    @Override
    public Boolean call() {
        Thread.currentThread().setName(CommonUtil.getThreadName(myTicketInfoModel));
        while (true) {
            myTicketInfoModel.setQueryNum(myTicketInfoModel.getQueryNum() + 1);
            try {
                TicketModel ticketModel = handle.queryicket(myTicketInfoModel);
                if (ticketModel == null || CollectionUtils.isEmpty(ticketModel.getSeat())) {
                    log.warn("没有查询到购买的票");
                    handle.sleep(null);
                    continue;
                }
                log.info("有票啦，开始抢！");
                return handle.buy(ticketModel, myTicketInfoModel);
            } catch (Exception e) {
                log.error("出现错误", e);
                handle.sleep(null);
                return null;
            }
        }
    }
}

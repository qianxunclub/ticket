package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.config.Config;
import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.model.TicketModel;
import com.qianxunclub.ticket.util.ApplicationContextHelper;
import com.qianxunclub.ticket.util.CommonUtils;


import org.springframework.util.CollectionUtils;

import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-06-08 11:08
 * @description: TODO
 */
@Slf4j
public class Task implements Callable {

    private Config config;
    private BuyTicket buyTicket;
    private QueryTicket queryTicket;
    private BuyTicketInfoModel buyTicketInfoModel;

    public Task(BuyTicketInfoModel buyTicketInfoModel) {
        this.buyTicketInfoModel = buyTicketInfoModel;
        this.buyTicket = ApplicationContextHelper.getBean(BuyTicket.class);
        this.queryTicket = ApplicationContextHelper.getBean(QueryTicket.class);
        this.config = ApplicationContextHelper.getBean(Config.class);
    }

    @Override
    public Boolean call() {
        Thread.currentThread().setName(CommonUtils.getThreadName(buyTicketInfoModel));
        while (true) {
            buyTicketInfoModel.setQueryNum(buyTicketInfoModel.getQueryNum() + 1);
            try {
                TicketModel ticketModel = queryTicket.getMyTicket(buyTicketInfoModel);
                if (ticketModel == null || CollectionUtils.isEmpty(ticketModel.getSeat())) {
                    log.debug("没有查询到购买的票");
                    Thread.sleep(config.getQueryTicketSellpTime() * 1000);
                    continue;
                }
                log.info("有票啦，开始抢！");

                return buyTicket.buy(buyTicketInfoModel, ticketModel);
            } catch (Exception e) {
                log.error("出现错误", e);
                try {
                    Thread.sleep(config.getQueryTicketSellpTime() * 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        }
    }
}

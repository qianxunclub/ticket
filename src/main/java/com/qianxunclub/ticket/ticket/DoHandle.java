package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.constant.StatusEnum;
import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.model.UserTicketStore;
import com.qianxunclub.ticket.repository.dao.TicketDao;
import com.qianxunclub.ticket.repository.entity.Ticket;
import com.qianxunclub.ticket.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangbin
 * @date 2019-06-08 11:54
 * @description: TODO
 */
@Slf4j
@Component
public class DoHandle {

    @Autowired
    private Login login;
    @Autowired
    private TicketDao ticketDao;

    private final static LocalTime startTime = LocalTime.parse("05:55");
    private final static LocalTime endTime = LocalTime.parse("23:35");

    private static ExecutorService handleCachedThreadPool = Executors.newFixedThreadPool(100);

    public void go() {
        UserTicketStore.buyTicketInfoModelList.forEach(buyTicketInfoModel -> {
            this.add(buyTicketInfoModel);
        });
    }

    public void add(BuyTicketInfoModel buyTicketInfoModel) {
        Thread.currentThread().setName(CommonUtil.getThreadName(buyTicketInfoModel));
        if (!login.login(buyTicketInfoModel)) {
            UserTicketStore.httpUtilStore.remove(buyTicketInfoModel.getUsername());
            return;
        }

        Main main = new Main(buyTicketInfoModel);
        Thread thread = new Thread(main);
        thread.start();
    }

    class Main implements Runnable {
        private BuyTicketInfoModel buyTicketInfoModel;

        public Main(BuyTicketInfoModel buyTicketInfoModel) {
            this.buyTicketInfoModel = buyTicketInfoModel;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(CommonUtil.getThreadName(buyTicketInfoModel));
            buyTicketInfoModel.setStatus(StatusEnum.ING);
            while (true) {
            try {
                if (!checkWorkTime()){
                    continue;
                }
                Task task = new Task(buyTicketInfoModel);
                Future<Boolean> booleanFuture = handleCachedThreadPool.submit(task);
                    Boolean flag = booleanFuture.get();
                    if (flag) {
                        log.info("完成!!!!");
                        buyTicketInfoModel.setStatus(StatusEnum.SUCCESS);
                        Ticket ticket = ticketDao.getTicketByUserName(buyTicketInfoModel.getUsername());
                        if(ticket != null){
                            ticketDao.deleteById(buyTicketInfoModel.getId());
                        }
                        return;
                    }
                } catch (Exception e) {
                    log.error("未知异常", e);
                } finally {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        log.error("time sleep error",ex);
                    }
                }
            }
        }
    }

    private boolean checkWorkTime() throws InterruptedException {
        if (LocalTime.now().isAfter(endTime) || LocalTime.now().isBefore(startTime)){
            log.info("不在抢票范围内，运营时间为:06:00-23:30");
            TimeUnit.MINUTES.sleep(1);
            return false;
        }
        return true;
    }
}

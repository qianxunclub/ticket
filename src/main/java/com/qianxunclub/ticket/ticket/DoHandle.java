package com.qianxunclub.ticket.ticket;

import com.qianxunclub.ticket.config.CookiesConfig;
import com.qianxunclub.ticket.constant.StatusEnum;
import com.qianxunclub.ticket.model.LogdeviceModel;
import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.service.ApiRequestService;
import com.qianxunclub.ticket.model.UserTicketStore;
import com.qianxunclub.ticket.util.CommonUtils;
import com.qianxunclub.ticket.util.CookieUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    private CookieUtil cookieUtil;
    @Autowired
    private CookiesConfig cookiesConfig;
    @Autowired
    private Login login;
    @Autowired
    private ApiRequestService apiRequestService;

    private static ExecutorService handleCachedThreadPool = Executors.newFixedThreadPool(100);

    public void go() {
        UserTicketStore.buyTicketInfoModelList.forEach(myTicketInfoModel -> {
            this.add(myTicketInfoModel);
        });
    }

    public void add(BuyTicketInfoModel buyTicketInfoModel) {

        LogdeviceModel logdeviceModel = null;
        // logdeviceModel = request.getDeviceId();
        logdeviceModel = new LogdeviceModel(cookiesConfig.getRailExpiration(), cookiesConfig.getRailDeviceid());
        buyTicketInfoModel.setLogdeviceModel(logdeviceModel);

        UserTicketStore.userBasicCookieStore.put(buyTicketInfoModel.getUsername(), cookieUtil.init(UserTicketStore.userBasicCookieStore.get(buyTicketInfoModel.getUsername()), buyTicketInfoModel.getLogdeviceModel()));
        if (!login.login(buyTicketInfoModel)) {
            UserTicketStore.remove(buyTicketInfoModel);
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
            Thread.currentThread().setName(CommonUtils.getThreadName(buyTicketInfoModel));
            buyTicketInfoModel.setStatus(StatusEnum.ING);
            while (true) {
                Task task = new Task(buyTicketInfoModel);
                Future<Boolean> booleanFuture = handleCachedThreadPool.submit(task);
                try {
                    Boolean flag = booleanFuture.get();
                    if (flag) {
                        log.info("完成!!!!");
                        buyTicketInfoModel.setStatus(StatusEnum.SUCCESS);
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception e) {
                    log.error("", e);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}

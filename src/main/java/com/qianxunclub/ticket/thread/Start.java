package com.qianxunclub.ticket.thread;

import com.qianxunclub.ticket.config.CookiesConfig;
import com.qianxunclub.ticket.config.StatusEnum;
import com.qianxunclub.ticket.handle.Login;
import com.qianxunclub.ticket.model.LogdeviceModel;
import com.qianxunclub.ticket.model.MyTicketInfoModel;
import com.qianxunclub.ticket.request.Request;
import com.qianxunclub.ticket.util.CookieUtil;
import com.qianxunclub.ticket.util.CommonUtil;

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
public class Start {

    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private CookiesConfig cookiesConfig;
    @Autowired
    private Login login;
    @Autowired
    private Request request;

    private static ExecutorService handleCachedThreadPool = Executors.newFixedThreadPool(100);

    public void go() {
        UserInfo.myTicketInfoModelList.forEach(myTicketInfoModel -> {
            this.add(myTicketInfoModel);
        });
    }

    public void add(MyTicketInfoModel myTicketInfoModel) {

        LogdeviceModel logdeviceModel = null;
        // logdeviceModel = request.getDeviceId();
        logdeviceModel = new LogdeviceModel(cookiesConfig.getRailExpiration(), cookiesConfig.getRailDeviceid());
        myTicketInfoModel.setLogdeviceModel(logdeviceModel);

        UserInfo.userBasicCookieStore.put(myTicketInfoModel.getUsername(), cookieUtil.init(UserInfo.userBasicCookieStore.get(myTicketInfoModel.getUsername()), myTicketInfoModel.getLogdeviceModel()));
        if (!login.login(myTicketInfoModel)) {
            return;
        }

        Main main = new Main(myTicketInfoModel);
        Thread thread = new Thread(main);
        thread.start();
    }

    class Main implements Runnable {
        private MyTicketInfoModel myTicketInfoModel;

        public Main(MyTicketInfoModel myTicketInfoModel) {
            this.myTicketInfoModel = myTicketInfoModel;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(CommonUtil.getThreadName(myTicketInfoModel));
            myTicketInfoModel.setStatus(StatusEnum.ING);
            while (true) {
                HandleThread handleThread = new HandleThread(myTicketInfoModel);
                Future<Boolean> booleanFuture = handleCachedThreadPool.submit(handleThread);
                try {
                    Boolean flag = booleanFuture.get();
                    if (flag) {
                        log.info("完成!!!!");
                        myTicketInfoModel.setStatus(StatusEnum.SUCCESS);
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

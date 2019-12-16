package com.qianxunclub.ticket.config;


import com.qianxunclub.ticket.util.StaticUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-05-30 16:06
 * @description: TODO
 */
@Data
@Component
@ConfigurationProperties(prefix = "api")
public class ApiConfig {

    @Autowired
    private Config config;

    private String station;

    private String leftTicketBaseUrl;

    private String leftTicket;

    private String loginConfig;

    private String captchaImage;

    private String uamtkStatic;

    private String login;

    private String passengers;

    private String captchaCheck;

    private String uamtk;

    private String uamauthclient;

    private String submitOrderRequest;

    private String checkUser;

    private String initDc;

    private String getPassengerDTOs;

    private String checkOrderInfo;

    private String checkRandCodeAnsyn;

    private String confirmSingleForQueue;

    private String getQueueCount;

    private String queryOrderWaitTime;

    private String notice;

    private String init;

    public String getHost() {
        String host = StaticUtil.ip();
        if (host == null) {
            host = config.getBaseUrl();
        }
        return host;
    }

    public String getStation() {
        return this.getHost().concat(station);
    }

    public String getLeftTicket() {
        return this.getHost().concat(leftTicket);
    }

    public String getLoginConfig() {
        return this.getHost().concat(loginConfig);
    }

    public String getCaptchaImage() {
        return this.getHost().concat(captchaImage);
    }

    public String getUamtkStatic() {
        return this.getHost().concat(uamtkStatic);
    }

    public String getLogin() {
        return this.getHost().concat(login);
    }

    public String getPassengers() {
        return this.getHost().concat(passengers);
    }

    public String getCaptchaCheck() {
        return this.getHost().concat(captchaCheck);
    }

    public String getUamtk() {
        return this.getHost().concat(uamtk);
    }

    public String getUamauthclient() {
        return this.getHost().concat(uamauthclient);
    }

    public String getSubmitOrderRequest() {
        return this.getHost().concat(submitOrderRequest);
    }

    public String getCheckUser() {
        return this.getHost().concat(checkUser);
    }

    public String getInitDc() {
        return this.getHost().concat(initDc);
    }

    public String getGetPassengerDTOs() {
        return this.getHost().concat(getPassengerDTOs);
    }

    public String getCheckOrderInfo() {
        return this.getHost().concat(checkOrderInfo);
    }

    public String getCheckRandCodeAnsyn() {
        return this.getHost().concat(checkRandCodeAnsyn);
    }

    public String getConfirmSingleForQueue() {
        return this.getHost().concat(confirmSingleForQueue);
    }

    public String getGetQueueCount() {
        return this.getHost().concat(getQueueCount);
    }

    public String getQueryOrderWaitTime() {
        return this.getHost().concat(queryOrderWaitTime);
    }

    public String getInit() {
        return this.getHost().concat(init);
    }
}

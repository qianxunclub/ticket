package com.qianxunclub.ticket.config;


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

    private String station;

    private String getJs;

    private String logdevice;

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

}

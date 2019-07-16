package com.qianxunclub.ticket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-05-30 15:57
 * @description: TODO
 */
@Data
@Component
@ConfigurationProperties(prefix = "config")
public class Config {

    private String host;

    private String baseUrl;

    private long queryTicketSellpTime;

    private String pythonPath;

}

package com.qianxunclub.ticket.config;

import java.util.Map;
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

    private Boolean enableProxy;

    private String proxyHost;

    private Integer proxyPort;

    private Map<String, Integer> queryTicketSleepTime;

    private String pythonPath;

    public Integer querySleep() {
        return (int) (queryTicketSleepTime.get("min") + Math.random() * (
                queryTicketSleepTime.get("max") - queryTicketSleepTime.get("min") + 1));
    }

}

package com.qianxunclub.ticket.config;

import com.qianxunclub.ticket.repository.entity.ProxyIp;
import com.qianxunclub.ticket.util.StaticUtil;
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

    private ProxyIp proxyIp;

    public Integer querySleep() {
        return (int) (queryTicketSleepTime.get("min") + Math.random() * (
                queryTicketSleepTime.get("max") - queryTicketSleepTime.get("min") + 1));
    }

    public ProxyIp getProxyIp() {
        proxyIp = StaticUtil.proxyIp();
        if (proxyIp == null) {
            proxyIp = new ProxyIp();
            proxyIp.setIp(proxyHost);
            proxyIp.setPort(proxyPort);
        }
        return proxyIp;
    }
}

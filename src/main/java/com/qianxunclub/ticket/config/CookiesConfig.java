package com.qianxunclub.ticket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-06-04 11:22
 * @description: TODO
 */
@Data
@Component
@ConfigurationProperties(prefix = "cookies")
public class CookiesConfig {
    String railExpiration;
    String railDeviceid;
}

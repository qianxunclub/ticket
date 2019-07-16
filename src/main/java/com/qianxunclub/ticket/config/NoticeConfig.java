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
@ConfigurationProperties(prefix = "notice")
public class NoticeConfig {

    private String accessKeyId;
    private String accessSecret;
    private String templateCode;
    private String signName;


}

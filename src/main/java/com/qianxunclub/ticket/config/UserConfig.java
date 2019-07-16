package com.qianxunclub.ticket.config;

import com.qianxunclub.ticket.model.MyTicketInfoModel;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-05 13:56
 * @description: TODO
 */
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserConfig extends MyTicketInfoModel {

}

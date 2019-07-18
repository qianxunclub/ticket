package com.qianxunclub.ticket.config;

import com.qianxunclub.ticket.model.TicketInfoModel;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author zhangbin
 * @date 2019-07-05 13:56
 * @description: TODO
 */
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserConfig {
    private List<TicketInfoModel> ticketInfo = new ArrayList<>();
}

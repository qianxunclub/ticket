package com.qianxunclub.ticket;

import com.qianxunclub.ticket.config.UserConfig;
import com.qianxunclub.ticket.model.MyTicketInfoModel;
import com.qianxunclub.ticket.thread.Start;
import com.qianxunclub.ticket.thread.UserInfo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;


/**
 * @author zhangbin
 */
@SpringBootApplication
public class TicketApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TicketApplication.class);
        ApplicationContext applicationContext = builder.run(args);

        MyTicketInfoModel myTicketInfoModel = applicationContext.getBean(UserConfig.class);
        if(myTicketInfoModel != null){
            UserInfo.add(myTicketInfoModel);
        }

        Start start = applicationContext.getBean(Start.class);
        start.go();

    }

}

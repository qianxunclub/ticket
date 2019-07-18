package com.qianxunclub.ticket;

import com.qianxunclub.ticket.config.UserConfig;
import com.qianxunclub.ticket.model.TicketInfoModel;
import com.qianxunclub.ticket.ticket.DoHandle;
import com.qianxunclub.ticket.model.UserTicketStore;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;


/**
 * @author zhangbin
 */
@SpringBootApplication
public class TicketApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TicketApplication.class);
        ApplicationContext applicationContext = builder.run(args);

        List<TicketInfoModel> ticketInfoModelList = applicationContext.getBean(UserConfig.class).getTicketInfo();
        ticketInfoModelList.forEach((ticketInfoModel)->{
            if(ticketInfoModel != null){
                UserTicketStore.add(ticketInfoModel);
            }
        });


        DoHandle doHandle = applicationContext.getBean(DoHandle.class);
        doHandle.go();

    }

}

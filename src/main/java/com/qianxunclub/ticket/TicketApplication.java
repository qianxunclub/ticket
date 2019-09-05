package com.qianxunclub.ticket;

import com.qianxunclub.ticket.config.Config;
import com.qianxunclub.ticket.config.UserConfig;
import com.qianxunclub.ticket.ip.AddIpThread;
import com.qianxunclub.ticket.ip.CheckIpThread;
import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.service.ApiRequestService;
import com.qianxunclub.ticket.service.IpsService;
import com.qianxunclub.ticket.service.TicketService;
import com.qianxunclub.ticket.ticket.DoHandle;
import com.qianxunclub.ticket.model.UserTicketStore;

import com.qianxunclub.ticket.ticket.Station;
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

        TicketApplication.init(applicationContext);

    }

    private static void init(ApplicationContext applicationContext){

        /**
         * 初始化 IP
         */
        IpsService ipsService = applicationContext.getBean(IpsService.class);
        ipsService.load();


        Station station = applicationContext.getBean(Station.class);
        ApiRequestService apiRequestService = applicationContext.getBean(ApiRequestService.class);
        station.load(apiRequestService);
        // 配置文件获取购票嘻嘻
        List<BuyTicketInfoModel> buyTicketInfoModelList = applicationContext.getBean(UserConfig.class).getTicketInfo();
        buyTicketInfoModelList.forEach((buyTicketInfoModel) -> {
            if (buyTicketInfoModel != null) {
                UserTicketStore.add(buyTicketInfoModel);
            }
        });

        // 数据库获取取购票嘻嘻
        TicketService ticketService = applicationContext.getBean(TicketService.class);
        ticketService.getBuyTicketInfoModel().forEach(buyTicketInfoModel -> {
            if (buyTicketInfoModel != null) {
                UserTicketStore.add(buyTicketInfoModel);
            }
        });

        DoHandle doHandle = applicationContext.getBean(DoHandle.class);
        doHandle.go();

        // 检查IP
        CheckIpThread checkIpThread = new CheckIpThread(ipsService);
        Thread checkIp = new Thread(checkIpThread);
        checkIp.start();

        // 获取新IP
        AddIpThread addIpThread = new AddIpThread(ipsService);
        Thread addIp = new Thread(addIpThread);
        addIp.start();

    }

}

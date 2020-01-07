package com.qianxunclub.ticket;

import com.qianxunclub.ticket.config.UserConfig;
import com.qianxunclub.ticket.ip.AddIpThread;
import com.qianxunclub.ticket.ip.CheckIpThread;
import com.qianxunclub.ticket.model.BuyTicketInfoModel;
import com.qianxunclub.ticket.service.ApiRequestService;
import com.qianxunclub.ticket.service.IpsService;
import com.qianxunclub.ticket.service.ProxyIpService;
import com.qianxunclub.ticket.service.TicketService;
import com.qianxunclub.ticket.ticket.DoHandle;
import com.qianxunclub.ticket.model.UserTicketStore;

import com.qianxunclub.ticket.ticket.Station;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;


/**
 * @author zhangbin
 */
@SpringBootApplication
@Slf4j
public class TicketApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TicketApplication.class);
        ApplicationContext applicationContext = builder.run(args);

        TicketApplication.init(applicationContext);

    }

    public static void init(ApplicationContext applicationContext) {

        // 初始化 IP
        IpsService ipsService = applicationContext.getBean(IpsService.class);
        ipsService.load();
        log.info("初始化Ip完成");
        // 初始化 代理
        ProxyIpService proxyIpservice = applicationContext.getBean(ProxyIpService.class);
        proxyIpservice.load();
        log.info("初始化代理Ip完成");
        // 初始化站点
        Station station = applicationContext.getBean(Station.class);
        ApiRequestService apiRequestService = applicationContext.getBean(ApiRequestService.class);
        station.load(apiRequestService);
        log.info("初始化站点信息完成");
        // 配置文件获取购票信息
        List<BuyTicketInfoModel> buyTicketInfoModelList = applicationContext
                .getBean(UserConfig.class).getTicketInfo();
        buyTicketInfoModelList.forEach((buyTicketInfoModel) -> {
            if (buyTicketInfoModel != null) {
                UserTicketStore.add(buyTicketInfoModel);
            }
        });
        log.info("初始化从配置文件初始化购票信息完成");
        // 数据库获取取购票信息
        TicketService ticketService = applicationContext.getBean(TicketService.class);
        ticketService.getBuyTicketInfoModel().forEach(buyTicketInfoModel -> {
            if (buyTicketInfoModel != null) {
                UserTicketStore.add(buyTicketInfoModel);
            }
        });
        log.info("初始化从数据库初始化购票信息完成");
        DoHandle doHandle = applicationContext.getBean(DoHandle.class);
        doHandle.go();
        log.info("开始执行抢票任务");
        // 检查IP
        CheckIpThread checkIpThread = new CheckIpThread(ipsService);
        Thread checkIp = new Thread(checkIpThread);
        checkIp.start();
        log.info("检查IP完成");
        // 获取新IP
        AddIpThread addIpThread = new AddIpThread(ipsService);
        Thread addIp = new Thread(addIpThread);
        addIp.start();
        log.info("获取IP完成");
    }

}

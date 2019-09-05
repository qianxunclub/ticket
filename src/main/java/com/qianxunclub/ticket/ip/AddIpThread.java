package com.qianxunclub.ticket.ip;

import com.qianxunclub.ticket.service.IpsService;
import com.qianxunclub.ticket.util.IpUtil;
import org.springframework.util.StringUtils;

public class AddIpThread implements Runnable{

    private IpsService ipsService;

    public AddIpThread(IpsService ipsService){
        this.ipsService = ipsService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 每十分钟获取更新一次 IP
                Thread.sleep(10 * 1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 获取 IP
            String ip = "";
            if(!StringUtils.isEmpty(ip)){
                ipsService.addIp(ip);
                IpUtil.addIp(ip);
            }
        }
    }
}

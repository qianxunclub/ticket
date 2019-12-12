package com.qianxunclub.ticket.ip;

import com.qianxunclub.ticket.service.IpsService;
import com.qianxunclub.ticket.util.StaticUtil;

public class PingIpThread implements Runnable {

    private IpsService ipsService;

    private String ip;

    public PingIpThread(IpsService ipsService,String ip) {
        this.ip = ip;
        this.ipsService = ipsService;
    }

    @Override
    public void run() {
        boolean s = true;
        // 这里校验 IP


        // 如果 IP 失效
        if(!s){
            ipsService.delIp(ip);
            StaticUtil.rmIp(ip);
        }
    }
}

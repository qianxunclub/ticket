package com.qianxunclub.ticket.ip;

import com.qianxunclub.ticket.service.IpsService;
import com.qianxunclub.ticket.util.StaticUtil;

public class CheckIpThread implements Runnable {

    private IpsService ipsService;


    public CheckIpThread(IpsService ipsService) {
        this.ipsService = ipsService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 每十分钟检查一次 IP
                Thread.sleep(10 * 1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            StaticUtil.ips.forEach(ip -> {
                Thread thread = new Thread(new PingIpThread(ipsService, ip));
                thread.start();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

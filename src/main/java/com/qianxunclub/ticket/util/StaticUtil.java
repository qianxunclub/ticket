package com.qianxunclub.ticket.util;

import com.qianxunclub.ticket.repository.entity.ProxyIp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StaticUtil {

    public static List<String> ips = new ArrayList<>();
    public static List<ProxyIp> proxyIp = new ArrayList<>();


    public static void addIp(String ip){
        ips.add(ip);
    }

    public static void rmIp(String ip){
        ips.remove(ip);
    }

    public static void addIps(List<String> ips){
        StaticUtil.ips = ips;
    }

    public static String ip(){
        if(ips.size() <= 0){
            return null;
        }
        Random r = new Random(0);
        int i = r.nextInt(ips.size() - 1);
        return ips.get(i);
    }

    public static void addProxyIp(List<ProxyIp> proxyIps){
        StaticUtil.proxyIp = proxyIps;
    }

    public static ProxyIp proxyIp(){
        if(proxyIp.size() <= 0){
            return null;
        }
        Random r = new Random(0);
        int i = r.nextInt(proxyIp.size() - 1);
        return proxyIp.get(i);
    }


}

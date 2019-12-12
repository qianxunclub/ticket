package com.qianxunclub.ticket.service;


import com.qianxunclub.ticket.repository.dao.ProxyIpDao;
import com.qianxunclub.ticket.util.StaticUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ProxyIpService {

    private ProxyIpDao proxyIpDao;

    public void load() {
        StaticUtil.addProxyIp(proxyIpDao.list());
    }

}

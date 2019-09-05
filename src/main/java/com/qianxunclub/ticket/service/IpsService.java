package com.qianxunclub.ticket.service;


import com.qianxunclub.ticket.repository.dao.IpsDao;
import com.qianxunclub.ticket.repository.entity.Ips;
import com.qianxunclub.ticket.util.IpUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class IpsService {

    private IpsDao ipsDao;

    public List<Ips> getIps() {
        return ipsDao.list();
    }

    public void addIp(String ip) {
        if (ipsDao.getIp(ip) == null) {
            Ips ips = new Ips();
            ips.setIp(ip);
            ipsDao.add(ips);
        }
    }

    public void delIp(String ip) {
        Ips ips = ipsDao.getIp(ip);
        if (ips != null) {
            ipsDao.deleteById(ips.getId());
        }
    }

    public void load() {
        ipsDao.list().forEach(ips -> {
            IpUtil.addIp(ips.getIp());
        });
    }

}

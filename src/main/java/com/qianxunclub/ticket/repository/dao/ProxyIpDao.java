package com.qianxunclub.ticket.repository.dao;

import com.qianxunclub.ticket.repository.entity.ProxyIp;
import com.qianxunclub.ticket.repository.mapper.ProxyIpMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author zhangbin
 * @date 2019-07-19 10:19
 * @description: TODO
 */
@Repository
@AllArgsConstructor
public class ProxyIpDao {

    private ProxyIpMapper proxyIpMapper;

    public List<ProxyIp> list() {
        return proxyIpMapper.selectList(null);
    }
}

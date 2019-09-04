package com.qianxunclub.ticket.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qianxunclub.ticket.repository.entity.Ips;
import com.qianxunclub.ticket.repository.mapper.IpsMapper;
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
public class IpsDao {

    private IpsMapper ipsMapper;

    public List<Ips> list() {
        return ipsMapper.selectList(null);
    }

    public Ips getIp(String ip) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("ip", ip);
        return ipsMapper.selectOne(queryWrapper);
    }

    public int add(Ips ips) {
        return ipsMapper.insert(ips);
    }

    public int deleteById(Integer id) {
        return ipsMapper.deleteById(id);
    }
}

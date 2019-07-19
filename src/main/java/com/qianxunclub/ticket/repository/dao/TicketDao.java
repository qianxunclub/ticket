package com.qianxunclub.ticket.repository.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qianxunclub.ticket.repository.entity.Ticket;
import com.qianxunclub.ticket.repository.mapper.TicketMapper;

import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * @author zhangbin
 * @date 2019-07-19 10:19
 * @description: TODO
 */
@Repository
@AllArgsConstructor
public class TicketDao {
    private TicketMapper ticketMapper;

    public List<Ticket> list() {
        return ticketMapper.selectList(null);
    }

    public Ticket getTicketByUserName(String userName) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", userName);
        return ticketMapper.selectOne(queryWrapper);
    }

    public void add(Ticket ticket) {
        ticketMapper.insert(ticket);
    }
}

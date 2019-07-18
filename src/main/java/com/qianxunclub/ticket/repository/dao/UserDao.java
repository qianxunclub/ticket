package com.qianxunclub.ticket.repository.dao;

import com.qianxunclub.ticket.repository.entity.User;
import com.qianxunclub.ticket.repository.mapper.UserMapper;

import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.AllArgsConstructor;

/**
 * @author zhangbin
 * @date 2019-07-18 17:24
 * @description: TODO
 */
@Repository
@AllArgsConstructor
public class UserDao {
    private UserMapper userMapper;

    public List<User> list(){
        return userMapper.selectList(null);
    }
}

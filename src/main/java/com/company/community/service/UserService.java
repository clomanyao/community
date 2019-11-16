package com.company.community.service;

import com.company.community.mapper.UserMapper;
import com.company.community.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional //添加事务，保持原子性
    public void insertUser(User user){
        if(user!=null){
        userMapper.insertUser(user);
    }
}
}

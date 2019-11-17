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

    @Transactional //添加事务
    public void insertOrUpdateUser(User user){
        User dbuser = userMapper.selectUserByaccountId(user.getAccountId());
        if(dbuser!=null){
            //更新
            dbuser.setToken(user.getToken())
                  .setName(user.getName())
                  .setGmtModified(System.currentTimeMillis())
                  .setBio(user.getBio())
                  .setAvatarUrl(user.getAvatarUrl());
            userMapper.updateUser(dbuser);
        }else {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
        }

}
}

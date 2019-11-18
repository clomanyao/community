package com.company.community.service;

import com.company.community.mapper.UserMapper;
import com.company.community.mapper.UserMapperCustom;
import com.company.community.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperCustom userMapperCustom;


    public void insertOrUpdateUser(User user){
        User dbuser = userMapperCustom.selectUserByaccountId(user.getAccountId());
        if(dbuser!=null){
            //更新
            dbuser.setToken(user.getToken());
            dbuser.setName(user.getName());
            dbuser.setGmtModified(System.currentTimeMillis());
            dbuser.setBio(user.getBio());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            userMapperCustom.updateUser(dbuser);
        }else {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertSelective(user);
        }

}
}

package com.company.community.mapper;

import com.company.community.models.User;

public interface UserMapperCustom {

    User findUserByToken(String token);

    User selectByCreattorId(Integer id);

    User selectUserByaccountId(String accountId);

    void updateUser(User user);

}

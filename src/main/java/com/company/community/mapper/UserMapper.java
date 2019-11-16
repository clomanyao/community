package com.company.community.mapper;


import com.company.community.models.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insertUser(User user);

    @Select("select * from user u where u.token=#{token}")
    User findUserByToken(String token);

    @Select("select * from user u where u.id=#{id}")
    User selectByCreattorId(Integer id);
}

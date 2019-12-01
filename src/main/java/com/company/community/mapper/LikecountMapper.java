package com.company.community.mapper;

import com.company.community.models.Likecount;
import com.company.community.models.LikecountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikecountMapper {
    long countByExample(LikecountExample example);

    int deleteByExample(LikecountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Likecount record);

    int insertSelective(Likecount record);

    List<Likecount> selectByExample(LikecountExample example);

    Likecount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Likecount record, @Param("example") LikecountExample example);

    int updateByExample(@Param("record") Likecount record, @Param("example") LikecountExample example);

    int updateByPrimaryKeySelective(Likecount record);

    int updateByPrimaryKey(Likecount record);
}
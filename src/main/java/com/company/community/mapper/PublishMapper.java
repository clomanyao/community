package com.company.community.mapper;

import com.company.community.models.Publish;
import com.company.community.models.PublishExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PublishMapper {
    long countByExample(PublishExample example);

    int deleteByExample(PublishExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Publish record);

    int insertSelective(Publish record);

    List<Publish> selectByExampleWithBLOBs(PublishExample example);

    List<Publish> selectByExample(PublishExample example);

    Publish selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Publish record, @Param("example") PublishExample example);

    int updateByExampleWithBLOBs(@Param("record") Publish record, @Param("example") PublishExample example);

    int updateByExample(@Param("record") Publish record, @Param("example") PublishExample example);

    int updateByPrimaryKeySelective(Publish record);

    int updateByPrimaryKeyWithBLOBs(Publish record);

    int updateByPrimaryKey(Publish record);
}
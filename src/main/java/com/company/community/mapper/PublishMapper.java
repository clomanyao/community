package com.company.community.mapper;

import com.company.community.models.Publish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PublishMapper {

    @Insert("insert into publish(title,description,tag,creator,gmt_create,gmt_modified) values(#{title},#{description},#{tag},#{creator},#{gmtCreate},#{gmtModified})")
    void insertProblem(Publish publish);

    @Select("select * from publish")
    List<Publish> selectPublishList();

}

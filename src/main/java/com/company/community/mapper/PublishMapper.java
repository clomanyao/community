package com.company.community.mapper;

import com.company.community.models.Publish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PublishMapper {

    @Insert("insert into publish(title,description,tag,creator,gmt_create,gmt_modified) values(#{title},#{description},#{tag},#{creator},#{gmtCreate},#{gmtModified})")
    void insertProblem(Publish publish);

    @Select("select * from publish")
    List<Publish> selectPublishList();

    @Select("select * from publish p where p.creator=#{createId}")
    List<Publish> selectPublistByCreatorId(Integer createId);

    @Select("select * from publish p where p.id=#{id}")
    Publish selectPublishById(Integer id);

    @Update("update publish p set p.tag=#{tag},p.description=#{description},p.title=#{title},p.gmt_modified=#{gmtModified} where p.id =#{id}")
    void updateQuestion(Publish publish);

}

package com.company.community.service;

import com.company.community.dto.PageDTO;
import com.company.community.dto.PublishDTO;
import com.company.community.mapper.PublishMapper;
import com.company.community.mapper.UserMapper;
import com.company.community.models.Publish;
import com.company.community.models.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublishService {

    private final static  Integer navigatePages=3;

    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PageDTO pageDTO;

    public List<PublishDTO> selectPublishList(Integer pageNum,Integer pageSize){
        List<PublishDTO> dtoArrrayList = new ArrayList<PublishDTO>();
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Publish> publishList = publishMapper.selectPublishList();
        for(Publish publish:publishList){
            PublishDTO publishDTO = new PublishDTO();
            //将publishList中的每个属性copy到publishDTO中
            BeanUtils.copyProperties(publish,publishDTO);
            //根据creator查找到对应的user并绑定
            User user = userMapper.selectByCreattorId(publish.getCreator());
            publishDTO.setUser(user);
            dtoArrrayList.add(publishDTO);
        }
        //每次连续查询n页
        PageInfo<Publish> pageInfo = new PageInfo<>(publishList, navigatePages);
        pageDTO.setPageInfo(pageInfo);
        return dtoArrrayList;
    }


    public List<Publish> selectPublistByCreatorId(Integer createId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Publish> publishList = publishMapper.selectPublistByCreatorId(createId);
        //连续展示的页面
        PageInfo<Publish> profilePageInfo = new PageInfo<>(publishList,navigatePages);
        pageDTO.setProfilePageInfo(profilePageInfo);
        return  publishList;
    }
}

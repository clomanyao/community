package com.company.community.service;

import com.company.community.dto.PublishDTO;
import com.company.community.mapper.PublishMapper;
import com.company.community.mapper.UserMapper;
import com.company.community.models.Publish;
import com.company.community.models.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublishService {
    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private UserMapper userMapper;

    public List<PublishDTO> selectPublishList(){
        List<PublishDTO> dtoArrrayList = new ArrayList<PublishDTO>();
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

        return dtoArrrayList;

    }
}

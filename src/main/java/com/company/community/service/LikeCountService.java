package com.company.community.service;

import com.company.community.enums.LikeCountEnum;
import com.company.community.mapper.CommentMapperCustom;
import com.company.community.mapper.LikecountMapper;
import com.company.community.models.Likecount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeCountService {

    @Autowired
    private LikecountMapper likecountMapper;

    @Autowired
    private CommentMapperCustom commentMapperCustom;


    @Transactional
    public void insertAndupdateCommentLikeCount(Integer commmetId,Integer userId){
        Likecount likecount = new Likecount();
        likecount.setLikeUser(userId);
        likecount.setCommentId(commmetId);
        likecount.setGmtCreate(System.currentTimeMillis());
        likecount.setLikeCount(LikeCountEnum.LIKECOUNT.getLikeProperty());
        likecount.setStatus(LikeCountEnum.LIKESTATUS.getLikeProperty());
        likecountMapper.insert(likecount);
        commentMapperCustom.updatelikeCount(commmetId);
    }
}

package com.company.community.service;

import com.company.community.dto.CommentDTO;
import com.company.community.enums.NotificationStatusEnum;
import com.company.community.enums.NotificationTypeEnum;
import com.company.community.exception.ExceptionEnum;
import com.company.community.exception.MyException;
import com.company.community.mapper.*;
import com.company.community.models.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private PublishMapperCustom publishMapperCustom;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentMapperCustom commentMapperCustom;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insertComment(Comment comment) {
        if (comment.getType() == 1) {
            Publish publish = publishMapper.selectByPrimaryKey(comment.getParentId());
            if (publish == null) {
                throw new MyException(ExceptionEnum.COMQUESTION);
            }
            //回复问题
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());
            //需要开启事务
            commentMapperCustom.insert(comment);
            publishMapperCustom.inComment(comment.getParentId());
            //问题通知
            createNotice(comment,NotificationTypeEnum.QUESTIONNOTICE.getType(),publish.getCreator());

        } else {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment == null) {
                throw new MyException(ExceptionEnum.COMQUESTION);
            } else {
                comment.setGmtCreate(System.currentTimeMillis());
                comment.setGmtModified(comment.getGmtCreate());
                commentMapper.insert(comment);
                commentMapperCustom.updateCommentCount(comment.getParentId());
                //评论通知
                createNotice(comment,NotificationTypeEnum.COMMENTNOTICE.getType(),dbcomment.getCommentator());
            }
        }
    }
   //创建通知回复
    public void createNotice(Comment comment,int type,int receiver){
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        //设置是问题的id，还是评论的id
        notification.setOuterId(comment.getParentId());
        //设置通知人
        notification.setNotifier(comment.getCommentator());
        //设置接收人
        notification.setReceiver(receiver);
        notification.setType(type);
        notification.setStatus(NotificationStatusEnum.NUREAD.getStatus());
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> selectByparentIdAndType(Integer id,Integer type) {
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("gmt_create desc");  //评论信息倒序排列
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type);
        //selectByExampleWithBLOBs方法会对数据库text类型进行查询
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
        if(comments==null&&comments.size()==0){
            return null;
        }
        //本处代码逻辑太罗嗦，以后会用java 8的lamba和steam知识进行代码优化
        List<CommentDTO> commentDTOArrayList = new ArrayList<CommentDTO>();
        for (Comment comment : comments) {
            User user = userMapper.selectByPrimaryKey(comment.getCommentator());
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(user);
            commentDTOArrayList.add(commentDTO);
        }
        return commentDTOArrayList;
    }
}

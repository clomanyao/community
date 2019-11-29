package com.company.community.service;

import com.company.community.dto.NotificationDTO;
import com.company.community.dto.PageDTO;
import com.company.community.enums.NotificationStatusEnum;
import com.company.community.enums.NotificationTypeEnum;
import com.company.community.exception.ExceptionEnum;
import com.company.community.exception.MyException;
import com.company.community.mapper.CommentMapper;
import com.company.community.mapper.NotificationMapper;
import com.company.community.mapper.PublishMapper;
import com.company.community.mapper.UserMapper;
import com.company.community.models.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private final static Integer navigatePages = 3;

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private PageDTO pageDTO;


    public List<NotificationDTO> selectNotificationByuserId(Integer id, Integer pageNum, Integer pageSize){
        //查询问题的通知和评论通知
        List<NotificationDTO> notificationDTOList= new ArrayList<NotificationDTO>();
        NotificationExample notificationexample = new NotificationExample();
        notificationexample.setOrderByClause("gmt_modified asc");
        notificationexample.createCriteria().andReceiverEqualTo(id);
                //.andStatusEqualTo(NotificationStatusEnum.NUREAD.getStatus());
        PageHelper.startPage(pageNum,pageSize);
        List<Notification> notifications = notificationMapper.selectByExample(notificationexample);
        if(notifications==null){
            return null;
        }
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            if(notification.getType()==1){
                //问题通知
                Publish publish = publishMapper.selectByPrimaryKey(notification.getOuterId());
                notificationDTO.setPublish(publish);
                notificationDTO.setName(NotificationTypeEnum.QUESTIONNOTICE.getName());
            }
            if(notification.getType()==2){
                //评论通知
                Comment comment = commentMapper.selectByPrimaryKey(notification.getOuterId());
                notificationDTO.setComment(comment);
                notificationDTO.setName(NotificationTypeEnum.COMMENTNOTICE.getName());
            }
            User user = userMapper.selectByPrimaryKey(notification.getNotifier());
            notificationDTO.setUser(user);
            notificationDTOList.add(notificationDTO);
        }
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notifications,navigatePages);
        pageDTO.setNotificationPageInfo(notificationPageInfo);
        return notificationDTOList;
    }


    public NotificationDTO notificationRead(User user,Integer id){
        //判断用书是不是读取属于自己的通知信息
        Notification dbnotification = notificationMapper.selectByPrimaryKey(id);
        if(dbnotification==null){
            throw new MyException(ExceptionEnum.NOTIFICATIONNOTFOUND);
        }
        if(dbnotification.getReceiver()!=user.getId()){
            throw new MyException(ExceptionEnum.NOTIFICATIONCANOTREAD);
        }
        //判断通知信息是否还在数据库中（有无被删除）
        NotificationExample notificationexample = new NotificationExample();
        notificationexample.createCriteria().andReceiverEqualTo(user.getId()).andIdEqualTo(id);
        List<Notification> notifications = notificationMapper.selectByExample(notificationexample);
        if(notifications==null){
            throw new MyException(ExceptionEnum.NOTIFICATIONNOTFOUND);
        }
        //根据通知信息id来更细通知信息的状态status
        Notification notification = new Notification();
        notification.setGmtModified(System.currentTimeMillis());
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        NotificationExample example = new NotificationExample();
        example.createCriteria().andIdEqualTo(id);
        int i = notificationMapper.updateByExampleSelective(notification, example);

        NotificationDTO notificationDTO = new NotificationDTO();
        //如果更新成功
        if(i==1){
            if(dbnotification.getType()==NotificationTypeEnum.QUESTIONNOTICE.getType()){
                //如果是更新的问题的信息通知，就把问题的id的返回
                notificationDTO.setOuterId(dbnotification.getOuterId());
                return notificationDTO;
            }else {
                //如果是更新的评论的信息通知，就把评论对应父类的问题的id的返回
                Comment comment = commentMapper.selectByPrimaryKey(dbnotification.getOuterId());
                notificationDTO.setOuterId(comment.getParentId());
                return notificationDTO;
            }
        }
        return null;
    }
}

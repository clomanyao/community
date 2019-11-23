package com.company.community.controller;

import com.company.community.enums.CommentEnumType;
import com.company.community.exception.ExceptionEnum;
import com.company.community.exception.MyException;
import com.company.community.mapper.CommentMapper;
import com.company.community.models.Comment;
import com.company.community.models.User;
import com.company.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public @ResponseBody
    Object postcomment(@RequestBody Comment comment, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            throw new MyException(ExceptionEnum.USERISNULL);
        }
        //comment.setLikeCount(1);
        comment.setCommentator(user.getId());
        commentService.insertComment(comment);
        //Comment dbcommnet = commentMapper.selectByPrimaryKey(comment.getId());
        return CommentEnumType.COMMENTOK.getType();
    }
}

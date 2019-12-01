package com.company.community.controller;

import com.company.community.dto.CommentDTO;
import com.company.community.enums.CommentEnumType;
import com.company.community.exception.ExceptionEnum;
import com.company.community.exception.MyException;
import com.company.community.models.Comment;
import com.company.community.models.User;
import com.company.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public @ResponseBody
    Object postcomment(@RequestBody Comment comment, Model model,
                       HttpServletRequest request) {
        if (StringUtils.isEmpty(comment.getContext())) {
            model.addAttribute("error", "评论不能为空！");
            return "question";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new MyException(ExceptionEnum.USERISNULL);
        }
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);
        commentService.insertComment(comment,user.getId());
        return CommentEnumType.COMMENTOK.getType();

    }

    @GetMapping("/comment/{id}")
    public @ResponseBody
    List<CommentDTO> getcomment(@PathVariable("id") Integer id,HttpServletRequest request) {
        User user =(User)request.getSession().getAttribute("user");
        List<CommentDTO> commentDTOS = commentService.selectByparentIdAndType(id, CommentEnumType.COMMENT.getType(),user.getId());
        return commentDTOS;
    }


}

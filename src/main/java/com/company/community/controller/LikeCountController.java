package com.company.community.controller;

import com.company.community.mapper.CommentMapper;
import com.company.community.mapper.LikecountMapper;
import com.company.community.models.Comment;
import com.company.community.models.Likecount;
import com.company.community.models.LikecountExample;
import com.company.community.models.User;
import com.company.community.service.LikeCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Slf4j
@Controller
public class LikeCountController {

    @Autowired
    private LikeCountService likeCountService;
    @Autowired
    private LikecountMapper likecountMapper;
    @Autowired
    private CommentMapper commentMapper;



    @PostMapping("/like")
    public ModelAndView likeCount(@RequestBody Likecount likecount,
                                  HttpServletRequest request, Model model){
        System.out.println("..................."+likecount.getId());
        Integer id = likecount.getId();
        System.out.println("..................................."+id);
        log.info("进入点赞模块");
        User user =(User)request.getSession().getAttribute("user");
        if(user==null){
            return new ModelAndView("redirect:/");
        }
        LikecountExample likecountexample = new LikecountExample();
        likecountexample.createCriteria().andCommentIdEqualTo(id).andLikeUserEqualTo(user.getId());
        List<Likecount> likecounts = likecountMapper.selectByExample(likecountexample);
        if(likecounts.size()>=1&&likecounts!=null){
            model.addAttribute("haspraise","已经点过赞了");
            log.info("已经点过赞了");
        }else{
            likeCountService.insertAndupdateCommentLikeCount(id, user.getId());
            log.info("点赞成功");
        }
        Comment comment = commentMapper.selectByPrimaryKey(id);
        Integer questionId=comment.getParentId();
        //return "redirect:question/"+comment.getParentId();
        return new ModelAndView("redirect:/question/"+questionId);
    }

}

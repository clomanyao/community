package com.company.community.controller;

import com.company.community.enums.LikeCountEnum;
import com.company.community.mapper.CommentMapper;
import com.company.community.mapper.LikecountMapper;
import com.company.community.models.Likecount;
import com.company.community.models.LikecountExample;
import com.company.community.models.User;
import com.company.community.service.LikeCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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



    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public @ResponseBody
    Object likeCount(@RequestBody Likecount likecount,
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
        return LikeCountEnum.LIKECOUNT.getCode();
        //return new ModelAndView("redirect:/question/"+questionId);
    }

}

package com.company.community.controller;

import com.company.community.dto.CommentDTO;
import com.company.community.dto.PublishDTO;
import com.company.community.enums.CommentEnumType;
import com.company.community.models.Publish;
import com.company.community.models.User;
import com.company.community.service.CommentService;
import com.company.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private PublishService publishService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model,
                           HttpServletRequest request) {
        Integer userId = null;
        User user =(User)request.getSession().getAttribute("user");
        if(user!=null){
            userId=user.getId();
        }
        if (id != null) {
            publishService.incView(id);
            //查找问题
            PublishDTO publishDTO = publishService.selectPublishById(id);
            model.addAttribute("publishDTO", publishDTO);
            //查找评论
            List<CommentDTO> commentDTOS = commentService.selectByparentIdAndType(id, CommentEnumType.QUESTION.getType(),userId!=null?userId:null);
            if (commentDTOS != null) {
                model.addAttribute("commentDTOS", commentDTOS);
            }
            //通过标签查找相关问题
            List<Publish> publishList = publishService.selectPublishByTags(publishDTO);
            if(publishList!=null){
                model.addAttribute("publishList", publishList);
            }
        }
        return "question";
    }
}

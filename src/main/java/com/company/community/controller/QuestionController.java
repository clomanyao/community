package com.company.community.controller;

import com.company.community.dto.CommentDTO;
import com.company.community.dto.PublishDTO;
import com.company.community.enums.CommentEnumType;
import com.company.community.service.CommentService;
import com.company.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private PublishService publishService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            publishService.incView(id);
            PublishDTO publishDTO = publishService.selectPublishById(id);
            model.addAttribute("publishDTO", publishDTO);
            List<CommentDTO> commentDTOS = commentService.selectByparentIdAndType(id, CommentEnumType.QUESTION.getType());
            if(commentDTOS!=null){
                model.addAttribute("commentDTOS",commentDTOS);
            }
        }
        return "question";
    }
}

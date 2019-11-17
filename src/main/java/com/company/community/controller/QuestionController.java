package com.company.community.controller;

import com.company.community.dto.PublishDTO;
import com.company.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private PublishService publishService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            PublishDTO publishDTO = publishService.selectPublishById(id);
            model.addAttribute("publishDTO", publishDTO);
        }
        return "question";
    }
}

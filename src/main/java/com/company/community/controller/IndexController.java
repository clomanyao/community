package com.company.community.controller;

import com.company.community.dto.PageDTO;
import com.company.community.dto.PublishDTO;
import com.company.community.models.Publish;
import com.company.community.service.PublishService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {


    @Autowired
    private PublishService publishService;

    @Autowired
    private PageDTO pageDTO;


    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "pageNum",defaultValue ="1") Integer pageNum,
                        @RequestParam(value = "pageSize",defaultValue ="5") Integer pageSize,
                        @RequestParam(value = "search",required = false) String search
                        ){

        List<PublishDTO> publishDTOS = publishService.selectPublishList(pageNum,pageSize,search);
        PageInfo<Publish> pageInfo = pageDTO.getPageInfo();
        model.addAttribute("pageInfo",pageInfo);
        int[] nums = pageInfo.getNavigatepageNums();
        model.addAttribute("nums",nums);
        model.addAttribute("publishDTOS",publishDTOS);
        model.addAttribute("search",search);
        return "index";
    }
}

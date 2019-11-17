package com.company.community.controller;

import com.company.community.dto.PageDTO;
import com.company.community.models.Publish;
import com.company.community.models.User;
import com.company.community.service.PublishService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private PageDTO pageDTO;

    @Autowired
    private PublishService publishService;

    @GetMapping("profile/{action}")
    public String question(@PathVariable("action") String action, Model model,
                           @RequestParam(value = "pageNum",defaultValue ="1") Integer pageNum,
                           @RequestParam(value = "pageSize",defaultValue ="5") Integer pageSize,
                           HttpServletRequest request
                           ) {
        User user =(User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        if ("question".equals(action)) {
            model.addAttribute("question", "question");
            model.addAttribute("selectName", "我的问题");
        }
        if("replies".equals(action)){
            model.addAttribute("question", "replies");
            model.addAttribute("selectName", "通知消息");
        }

        List<Publish> publishList = publishService.selectPublistByCreatorId(user.getId(), pageNum, pageSize);
        model.addAttribute("publishList",publishList);
        PageInfo<Publish> profilePageInfo = pageDTO.getProfilePageInfo();
        int[] nums = profilePageInfo.getNavigatepageNums();
        model.addAttribute("user",user);
        model.addAttribute("nums",nums);
        model.addAttribute("profilePageInfo",profilePageInfo);
        return "profile";
    }

}

package com.company.community.controller;

import com.company.community.dto.PublishDTO;
import com.company.community.mapper.UserMapper;
import com.company.community.models.User;
import com.company.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PublishService publishService;

    @GetMapping("/")
    public String index(HttpServletRequest request,Model model){

        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                User user = userMapper.findUserByToken(cookie.getValue());
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
          }
        }
        List<PublishDTO> publishDTOS = publishService.selectPublishList();
        model.addAttribute("publishDTOS",publishDTOS);
        return "index";
    }
}

package com.company.community.controller;

import com.company.community.dto.NotificationDTO;
import com.company.community.models.User;
import com.company.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;


@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notificationRead(@PathVariable("id") Integer id,
                                   HttpServletRequest request, Model model){
         User user = (User)request.getSession().getAttribute("user");
        if(user ==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.notificationRead(user, id);
        if(notificationDTO!=null){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }
        return "redirect:/";
    }
}

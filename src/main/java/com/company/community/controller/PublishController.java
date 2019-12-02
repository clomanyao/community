package com.company.community.controller;

import com.company.community.exception.ExceptionEnum;
import com.company.community.exception.MyException;
import com.company.community.mapper.PublishMapper;
import com.company.community.mapper.UserMapperCustom;
import com.company.community.models.Publish;
import com.company.community.models.User;
import com.company.community.other.PublishMessage;
import com.company.community.privoder.TagPrivoder;
import com.company.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private PublishService publishService;

    @Autowired
    private UserMapperCustom userMapperCustom;

    @Autowired
    private PublishMessage publishMessage;


    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        //修复前端页面用户通过进入自己的编辑模式后，然后通过不正确的访问方式进入别的帖子进行修改
        User user =(User) request.getSession().getAttribute("user");
        if(user==null){
            throw new MyException(ExceptionEnum.USERISNULL);
        }
        User dbuser = userMapperCustom.selectUserByaccountId(user.getAccountId());
        Publish publish = publishMapper.selectByPrimaryKey(id);
        if(publish==null){
            throw new MyException(ExceptionEnum.QEUSTION);
        }
        if(dbuser.getId().equals(publish.getCreator())){
            publishMessage.setPublish(publish);
            model.addAttribute("managerDTOS",TagPrivoder.getTag());
            model.addAttribute("publish",publish);
            return "publish";
        }
        model.addAttribute("publish",publishMessage.getPublish());
        model.addAttribute("error","无权访问");
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(HttpServletRequest request,Model model) {
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        model.addAttribute("managerDTOS",TagPrivoder.getTag());
        return "publish";
    }

    @PostMapping("/publish")
    public String postPublish(Publish publish, Model model,
                              HttpServletRequest request,
                              @RequestParam(value = "id",required = false) Integer id
                              ){
        //int a=id; 当url访问错误，不会因为浏览器上的url的的改变而传参改变
        model.addAttribute("publish",publish);
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        model.addAttribute("managerDTOS",TagPrivoder.getTag());
        if(publish.getTitle().trim().equals("")){
            model.addAttribute("error","问题标题不能为为空！");
            return "publish";
        }
        if(publish.getDescription().trim().equals("")){
            model.addAttribute("error","问题描述不能为空 ！");
            return "publish";
        }
        if(publish.getTag().trim().equals("")){
            model.addAttribute("error","标签不能为空！");
            return "publish";
        }
        if(publish.getTitle().trim().length()>25){
            model.addAttribute("error","问题标题不能超过25个字哦！");
            return "publish";
        }
        String tags = TagPrivoder.conditionalTags(publish.getTag().trim());
        if(tags!=null){
            model.addAttribute("error","非法标签"+tags);
            return "publish";
        }
        publish.setCreator(user.getId());
        //更新或者插入问题
        publishService.updateOrinsertQuestion(publish,id);
        return "redirect:/";
    }

}

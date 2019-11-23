package com.company.community.controller;

import com.company.community.dto.AccessTokenDTO;
import com.company.community.dto.GitHubUser;
import com.company.community.models.User;
import com.company.community.privoder.GitHubPrivoder;
import com.company.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {
    @Autowired
    private GitHubPrivoder gitHubPrivoder;

    @Value("${github.accesstoken.client_id}")
    private String client_id;
    @Value("${github.accesstoken.client_secret}")
    private String client_secret;
    @Value("${github.accesstoken.redirect_uri}")
    private String redirect_uri;

    @Autowired
    private UserService userService;

    //当用户点击登陆按钮时，会去github得到授权,然后返回回到redirect_uri地址，并且携带code和state
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        String accessToken = gitHubPrivoder.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubPrivoder.getUser(accessToken);
        if (gitHubUser != null) {
            User user = new User();
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));  //强制转换为String类型
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setBio(gitHubUser.getBio());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            //添加或者更新user
            userService.insertOrUpdateUser(user);
            //添加token到cookie中,通过response响应到浏览器
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60*60*4);
            response.addCookie(cookie);
            //注意:和/不能有间隔
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //移除session
        request.getSession().removeAttribute("user");
        //移除cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}

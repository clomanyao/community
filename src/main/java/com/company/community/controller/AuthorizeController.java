package com.company.community.controller;

import com.company.community.dto.AccessTokenDTO;
import com.company.community.dto.GitHubUser;
import com.company.community.privoder.GitHubPrivoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

    //当用户点击登陆按钮时，会去github得到授权,然后返回回到redirect_uri地址，并且携带code和state
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        String accessToken = gitHubPrivoder.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubPrivoder.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }

}

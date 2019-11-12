package com.company.community.privoder;

import com.alibaba.fastjson.JSON;
import com.company.community.dto.AccessTokenDTO;
import com.company.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component //加入容器中
public class GitHubPrivoder {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //将accessTokenDTO对象转化为json串
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        //System.out.println("body:........"+body);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //根据请求体返回结果access_token
            String access_token = response.body().string();
            //System.out.println("access_token......"+access_token);
            //取出access_token=b4dd57afde01ed95cf1201e1912bf3c0dc257b0a&scope=user&token_type=bearer
            String accesstoken = access_token.split("&")[0].split("=")[1];
            return accesstoken;
        } catch (IOException e) {
            e.printStackTrace();
        }
            return null;
    }
    public GitHubUser getUser(String accesstoken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accesstoken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            //将json类型的字符串s转化为GithubUser的java对象
            //System.out.println("s........."+s);
            GitHubUser gitHubUser = JSON.parseObject(s, GitHubUser.class);
            return gitHubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
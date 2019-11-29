package com.company.community.config.interceptors;

import com.company.community.enums.NotificationStatusEnum;
import com.company.community.mapper.NotificationMapper;
import com.company.community.mapper.UserMapperCustom;
import com.company.community.models.NotificationExample;
import com.company.community.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapperCustom userMapperCustom;

    @Autowired
    private NotificationMapper notificationMapper;

    private Long unreadCount;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("token")){
                    User user = userMapperCustom.findUserByToken(cookie.getValue());
                    if(user!=null){
                        request.getSession().setAttribute("user",user);
                        //查询通知数
                        NotificationExample example = new NotificationExample();
                        example.createCriteria()
                                .andReceiverEqualTo(user.getId())
                                .andStatusEqualTo(NotificationStatusEnum.NUREAD.getStatus());
                        unreadCount = notificationMapper.countByExample(example);
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

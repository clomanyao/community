package com.company.community.config;

import com.company.community.config.interceptors.LoginInterceptor;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Properties;

@Configuration
public class BeanConfig extends WebMvcConfigurerAdapter {

      @Autowired
      private LoginInterceptor loginInterceptor;

     //将分页插件加入到容器中
     @Bean
     public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        //rowBoundsWithCount为true，会使用count查询
        properties.setProperty("rowBoundsWithCount","true");
        //reasonable为true，pageNum<0插叙第一页，当pageNum>总页数，查询最后一页
        properties.setProperty("reasonable","true");
        pageHelper.setProperties(properties);
        return pageHelper;
     }

     //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }


}

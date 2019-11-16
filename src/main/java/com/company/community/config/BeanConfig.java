package com.company.community.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class BeanConfig {

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
}

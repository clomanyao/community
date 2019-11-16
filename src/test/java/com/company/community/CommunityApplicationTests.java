package com.company.community;


import com.company.community.dto.PageDTO;
import com.company.community.mapper.PublishMapper;
import com.company.community.models.Publish;
import com.company.community.service.PublishService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    @Autowired
    private PageDTO pageDTO;
    @Autowired
    private PublishService publishService;

    @Autowired
    private PublishMapper publishMapper;
    @Test
    public void contextLoads() {

    }

    @Test
    public void test(){
        Page<Object> page = PageHelper.startPage(4, 5);
        List<Publish> publishes = publishMapper.selectPublishList();
        System.out.println("多少页："+page.getPages());
       //代表每次显示多少页
        PageInfo<Publish> pageInfo = new PageInfo<>(publishes,3);

        System.out.println("多少页："+pageInfo.getPages());
        System.out.println("当前所在也面"+pageInfo.getPageNum());
//        System.out.println("连续显示的页面：");
//        int[] nums = pageInfo.getNavigatepageNums();
//        for (int num : nums) {
//            System.out.println(num);
//        }

    }
}


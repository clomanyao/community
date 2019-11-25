package com.company.community;


import com.company.community.mapper.PublishMapper;
import com.company.community.mapper.PublishMapperCustom;
import com.company.community.models.Publish;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

    @Autowired
    private PublishMapper publishMapper;
    @Autowired
    private PublishMapperCustom publishMapperCustom;

    @Test
    public void contextLoads() {
        Publish publish = publishMapper.selectByPrimaryKey(23);
        String[] split = publish.getTag().split(",");
        String collect = Arrays.stream(split).collect(Collectors.joining("|"));
        Publish publish1 = new Publish();
        publish1.setId(23);
    }

}


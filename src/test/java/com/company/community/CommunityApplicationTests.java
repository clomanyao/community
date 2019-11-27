package com.company.community;


import com.company.community.privoder.TagPrivoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {



    @Test
    public void contextLoads() {
        String s = TagPrivoder.conditionalTags("java,springCloud,spring,hibernate,mybatis,asd");
        System.out.println(s);
    }

}


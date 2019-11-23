package com.company.community;


import com.company.community.enums.CommentEnumType;
import com.company.community.mapper.CommentMapper;
import com.company.community.models.Comment;
import com.company.community.models.CommentExample;
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
    private CommentMapper commentMapper;

    @Test
    public void contextLoads() {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(22).andTypeEqualTo(CommentEnumType.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }

}


package com.company.community.dto;

import com.company.community.models.Comment;
import com.company.community.models.User;
import lombok.Data;

@Data
public class CommentDTO extends Comment {
    private User user;
    private Integer status; //用来用户是否点赞的状态
}

package com.company.community.dto;

import com.company.community.models.Comment;
import com.company.community.models.User;
import lombok.Data;

@Data
public class CommentDTO extends Comment {
    private User user;

}

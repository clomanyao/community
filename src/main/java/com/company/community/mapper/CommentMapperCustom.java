package com.company.community.mapper;

import com.company.community.models.Comment;

public interface CommentMapperCustom {
    void insert(Comment comment);
    void updateCommentCount(Integer commentId);
}

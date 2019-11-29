package com.company.community.dto;

import com.company.community.models.Comment;
import com.company.community.models.Notification;
import com.company.community.models.Publish;
import com.company.community.models.User;
import lombok.Data;

@Data
public class NotificationDTO extends Notification {
    private User user;
    private Publish publish;
    private Comment comment;
    private String name;
}

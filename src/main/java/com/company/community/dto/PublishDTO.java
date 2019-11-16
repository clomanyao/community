package com.company.community.dto;

import com.company.community.models.Publish;
import com.company.community.models.User;
import lombok.Data;

@Data
public class PublishDTO extends Publish {
    private User user;
}

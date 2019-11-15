package com.company.community.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class GitHubUser {
    private String name;
    private Long id;
    private String Bio;
    private String avatarUrl;
}

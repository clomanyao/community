package com.company.community.dto;


import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

//@Component
//@ConfigurationProperties(prefix = "github.accesstoken")
@Data
@ToString
@Accessors(chain = true)
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}

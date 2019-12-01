package com.company.community.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileDTO {
    private int success;
    private String message;
    private String url;
}

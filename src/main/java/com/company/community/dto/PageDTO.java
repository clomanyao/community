package com.company.community.dto;

import com.company.community.models.Publish;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PageDTO {
    private PageInfo<Publish> pageInfo;

    private PageInfo<Publish> profilePageInfo;
}

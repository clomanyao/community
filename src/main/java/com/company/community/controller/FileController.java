package com.company.community.controller;

import com.company.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
    @GetMapping("/file/upload")
    @ResponseBody
    public FileDTO fileupload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setMessage("上传成功").setSuccess(1).setUrl("http://hhhhh");
        return fileDTO;
    }
}

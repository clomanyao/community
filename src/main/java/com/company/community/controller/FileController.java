package com.company.community.controller;

import com.company.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
    @GetMapping("/file/upload")
    @ResponseBody
    public FileDTO fileupload(@RequestParam("editormd-image-file") MultipartFile multipartFile){
        System.out.println(multipartFile);
        FileDTO fileDTO = new FileDTO();
        fileDTO.setMessage("上传成功").setSuccess(1).setUrl("http://hhhhh");
        return fileDTO;
    }
}

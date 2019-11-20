package com.company.community.controller.error;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//@Controller
public class MyCustomizeErrorController extends AbstractErrorController {

    public MyCustomizeErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(value = "/error",produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = this.getStatus(request);
        if(status.is4xxClientError()){
            model.addAttribute("message","访问路径不存在，换一个是试试");
        }
        if(status.is5xxServerError()){
            model.addAttribute("message","操作错误!");
        }
        return new ModelAndView("error");
    }
}
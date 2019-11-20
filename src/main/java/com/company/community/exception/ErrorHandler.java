package com.company.community.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionhandler(Throwable e, Model model){
        if(e instanceof MyException){
            model.addAttribute("message",e.getMessage());
        }
        else{
            model.addAttribute("message","服务找不到！");
        }
        ModelAndView modelAndView = new ModelAndView("error");
        return modelAndView;
    }
}

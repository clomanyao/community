package com.company.community.exception;

public enum ExceptionEnum {
    USERISNULL("没有登陆,请登陆"),

    QEUSTION("查找的问题不存在，换一个吧！");

    public String getMessgae() {
        return messgae;
    }

    private String messgae;

    private ExceptionEnum(String messgae){
      this.messgae=messgae;
    }

}

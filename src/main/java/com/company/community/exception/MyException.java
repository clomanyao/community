package com.company.community.exception;

public class MyException extends RuntimeException {

    public MyException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessgae());
    }

}

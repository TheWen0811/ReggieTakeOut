package com.jlw.utils;

/**
 * @program: reggie_take_out
 * @description:自定义业务异常类
 * @author: jlw
 * @create: 2024-07-26 12:11
 **/

public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}

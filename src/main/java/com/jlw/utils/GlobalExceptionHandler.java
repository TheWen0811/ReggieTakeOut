package com.jlw.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @program: reggie_take_out
 * @description: 全局异常处理
 * @author: jlw
 * @create: 2024-07-25 13:52
 **/

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    //异常处理方法
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHanler(SQLIntegrityConstraintViolationException ex) {
        log.info(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] split = ex.getMessage().split("");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("失败了");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHanler(CustomException ex) {
        log.info(ex.getMessage());

        return R.error(ex.getMessage());
    }
}

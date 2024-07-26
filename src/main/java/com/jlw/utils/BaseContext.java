package com.jlw.utils;

/**
 * @program: reggie_take_out
 * @description: 基于ThreadLocak封装工具类。用于保存和获取当前登录用户Id
 * @author: jlw
 * @create: 2024-07-26 10:29
 **/

public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}

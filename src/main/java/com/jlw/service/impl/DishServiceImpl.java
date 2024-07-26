package com.jlw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlw.mapper.DishMapper;
import com.jlw.pojo.Dish;
import com.jlw.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @program: reggie_take_out
 * @description:
 * @author: jlw
 * @create: 2024-07-26 11:55
 **/

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}

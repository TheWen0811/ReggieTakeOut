package com.jlw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlw.mapper.CategoryMapper;
import com.jlw.pojo.Category;
import com.jlw.pojo.Dish;
import com.jlw.pojo.Setmeal;
import com.jlw.service.CategoryService;
import com.jlw.service.DishService;
import com.jlw.service.SetmealService;
import com.jlw.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @program: reggie_take_out
 * @description:
 * @author: jlw
 * @create: 2024-07-26 11:05
 **/

@Service
public class CategoryServiceImpl extends ServiceImpl <CategoryMapper,Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    private SetmealService setmealService;
    /**
     * @Description: 根据id删除分类。删除之前需要进行判断
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @Override
    public void remove(Long id){
        LambdaQueryWrapper<Dish> dishQueryWrapper=new LambdaQueryWrapper<>();
        //t=添加查询条件，根据分类id查询
         dishQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishQueryWrapper);
        //查询当前分类是否关联菜品，如果关联抛出异常
        if(count>0){
            //已经关联菜品
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //查询当前分类是否关联套餐，如果关联抛出异常
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper=new LambdaQueryWrapper<>();
        //t=添加查询条件，根据分类id查询
        setmealQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = dishService.count(dishQueryWrapper);
        if(count2>0){
            //已经关联套餐
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除分类
        super.removeById(id);
    };
}

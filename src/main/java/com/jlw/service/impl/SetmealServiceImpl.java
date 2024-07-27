package com.jlw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlw.dto.SetmealDto;
import com.jlw.mapper.SetmealMapper;
import com.jlw.pojo.Setmeal;
import com.jlw.pojo.SetmealDish;
import com.jlw.service.SetmealDishService;
import com.jlw.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: reggie_take_out
 * @description:
 * @author: jlw
 * @create: 2024-07-26 11:56
 **/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements com.jlw.service.SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * @Description: 新增套餐，同时需要保存套餐和菜品的关联关系
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
     @Override
     @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
         //保存套餐基本信息，操作setmeal，执行insert操作
         this.save(setmealDto);

         List<SetmealDish> setmealDishes =setmealDto.getSetmealDishes();
         setmealDishes.stream().map((item)->{
             item.setSetmealId(setmealDto.getId());
             return item;
         }).collect(Collectors.toList());
         //保存套餐和菜品的关联关系，操作setmeal_dish执行insert操作
         setmealDishService.saveBatch(setmealDishes);


    }

    @Override
    public void removeWithDish(List<Long> ids) {

        //查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);

        //如果不能删除，抛出一个业务异常
        if(count>0){
            throw new CustomException("套餐正在售卖，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据
        this.removeByIds(ids);
        //删除关系表中的数据
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);

        setmealDishService.remove(lambdaQueryWrapper);
    }
}

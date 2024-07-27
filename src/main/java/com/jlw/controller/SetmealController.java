package com.jlw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlw.dto.SetmealDto;
import com.jlw.pojo.Category;
import com.jlw.pojo.Employee;
import com.jlw.pojo.Setmeal;
import com.jlw.service.CategoryService;
import com.jlw.service.SetmealDishService;
import com.jlw.service.SetmealService;
import com.jlw.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: reggie_take_out
 * @description:套餐管理
 * @author: jlw
 * @create: 2024-07-27 10:09
 **/
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;
/** 
* @Description: 新增套餐
* @Param: 
* @return: 
* @Author: jlw
* @Date: 
*/
    @PostMapping
    public R<String> save(@RequestBody  SetmealDto setmealDto){
        log.info("套餐信息：{}",setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }
    /**
     * @Description: 分页查询
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //构造分页构造器
        Page<Setmeal> pageInfo=new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage=new Page<>();
        //构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name!=null,Setmeal::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //执行查询
        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records=pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }
    /**
     * @Description: 删除套餐
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }


}
